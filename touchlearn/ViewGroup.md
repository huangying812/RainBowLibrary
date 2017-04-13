## 触摸事件分发机制学习 -ViewGroup篇

在上一篇[事件分发机制的学习-View篇](http://www.jianshu.com/p/383bae6b6487)基本算是逐行解析了单独view的事件传递和处理。
本篇在此基础上来分析ViewGroup对事件的分发和拦截。
   
有必要先简单回顾下单独View的事件处理机制：
     1. dipatchTouchEvent 
            ↓
     2. onTouch 
            ↓false
     3. onTouchEvent

***
 
### 选取一个ViewGroup来做小白鼠-LinearLayout
 
 *   **自定义View继承LinearLayout,重写事件处理的三个方法。**
   三个方法分别是，触摸事件调度、触摸事件、拦截触摸事件
```java
    @Override
       public boolean dispatchTouchEvent(MotionEvent ev) {
           switch (ev.getAction()){
               case MotionEvent.ACTION_DOWN:
                   L.println("TLineraLayout -dispatchTouchEvent-  MotionEvent.ACTION_DOWN");
                   break;
   
               case MotionEvent.ACTION_MOVE:
                   L.println("TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_MOVE");
                   break;
   
               case MotionEvent.ACTION_UP:
                   L.println("TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_UP");
                   break;
               default:
   
                   break;
           }
   
           return super.dispatchTouchEvent(ev);
       }
   
       @Override
       public boolean onTouchEvent(MotionEvent event) {
           switch (event.getAction()){
               case MotionEvent.ACTION_DOWN:
                   L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_DOWN");
                   break;
   
               case MotionEvent.ACTION_MOVE:
                   L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_MOVE");
                   break;
   
               case MotionEvent.ACTION_UP:
                   L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_UP");
                   break;
               default:
   
                   break;
           }
           return super.onTouchEvent(event);
       }
   
       @Override
       public boolean onInterceptTouchEvent(MotionEvent ev) {
           switch (ev.getAction()){
               case MotionEvent.ACTION_DOWN:
                   L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_DOWN");
                   return true;
   
               case MotionEvent.ACTION_MOVE:
                   L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_MOVE");
                   break;
   
               case MotionEvent.ACTION_UP:
                   L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_UP");
                   break;
               default:
   
                   break;
           }
           return super.onInterceptTouchEvent(ev);
       }
   
   ```
   看看日志打印：
```html
    D/L-: TLineraLayout -dispatchTouchEvent-  MotionEvent.ACTION_DOWN
    D/L-: TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_DOWN
    D/L-: TButton - dispatchTouchEvent = ACTON_DOWN
    D/L-: ACtivity - onTouch = ACTON_DOWN 
    D/L-: TButton - onTouchEvent = ACTON_DOWN

    D/L-: TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_MOVE
    D/L-: TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_MOVE
    D/L-: TButton - dispatchTouchEvent = ACTION_MOVE
    D/L-: ACtivity - onTouch = ACTION_MOVE
    D/L-: TButton - onTouchEvent = ACTION_MOVE

    D/L-: TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_UP
    D/L-: TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_UP
    D/L-: TButton - dispatchTouchEvent = ACTION_UP
    D/L-: ACtivity - onTouch = ACTION_UP
    D/L-: TButton - onTouchEvent = ACTION_UP
```
   上面日志说明从ViewGroup → View的事件调用顺序依次是
    `ViewGroup[dispatchTouchEvent → onInterceptTouchEvent]`
    ↓
    `View[dispatchTouchEvent → onTouch → onTouchEvent]`
    暂时直观理解：触摸事件是从ViewGroup从上到下传递到子View的。
    这里有一个疑问了，`onTouchEvent`事件怎么没有触发呢？
    
   下面就杀进源码看个究竟。
    
   >这里我们只需要着重关心`dispatchTouchEvent`即可，因为所有暗箱操作都在他这里
#### dispatchTouchEvent方法`（源码版本:sdk\sources\android23）
    >这里代码比较长，只截取重要代码片断，分段来解析
    >按照我自己的理解，重点围绕ViewGroup如何拦截的事件，如何分发事件这两点即可。
    
 
   * 1 .拦截判断 
   为`intercepted`初始化,只有当事件为`ACTION_DOWN`,
   或者 `mFirstTouchTarget != null`。
   `mFirstTouchTarget`总是在Down事件开始被清空，再处理事件后被赋值为next(接受后续事件的TouchTarget)
   `mFirstTouchTarget != null`：不是ACTION_DOWN事件，可能是MOVE，UP等事件传递进来（也就是二次接收事件了）。
   `mFirstTouchTarget == null`：ViewGroup压根没向下传递事件给子View,直接交给了起父类View类来处理了。
   (利用以上两点结论，有助于更快理解后面用`mFirstTouchTarget`做的一些if判断。)
   

```java
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ...省略

        final boolean intercepted;
        if (actionMasked == MotionEvent.ACTION_DOWN
                || mFirstTouchTarget != null) {
            // ==0 拦截false  !=0 不拦截true
            final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
            //允许拦截
            if (!disallowIntercept) {
                intercepted = onInterceptTouchEvent(ev);
                ev.setAction(action);
            } else {
                intercepted = false;
            }
        } else {
            intercepted = true;
        }
    }

}
                 
```  
   **小结**                     
   `onInterceptTouchEvent`方法在父类默认返回false 子类可以覆盖重写。
      上面有一个flag非常关键 `FLAG_DISALLOW_INTERCEPT` “不拦截”标记。
      `disallowIntercept = false` 时调用`onInterceptTouchEvent`方法，返回父类是否拦截。
   **`mGroupFlags`** 添加 不拦截的tag是在 `requestDisallowInterceptTouchEvent`方法中,传入true则添加不许拦截的tag。                               
   
   * 2.派发事件
    
```java
            ···省略
    final boolean canceled = resetCancelNextUpFlag(this)
            || actionMasked == MotionEvent.ACTION_CANCEL;   
          if(!canceled &&!intercepted)

    {
        //按下、多指、触点保持
        if (actionMasked == MotionEvent.ACTION_DOWN
                || (split && actionMasked == MotionEvent.ACTION_POINTER_DOWN)
                || actionMasked == MotionEvent.ACTION_HOVER_MOVE) {

            final int childrenCount = mChildrenCount;
            if (newTouchTarget == null && childrenCount != 0) {
                //记录事件触发坐标
                final float x = ev.getX(actionIndex);
                final float y = ev.getY(actionIndex);

                //确认子View显示或者包涵动画||触发坐标没有移除view的范围
                //否则跳过此次事件处理
                if (!canViewReceivePointerEvents(child)
                        || !isTransformedTouchPointInView(x, y, child, null)) {
                    ev.setTargetAccessibilityFocus(false);
                    continue;
                }

                //子View消费
                //分发事件给子类，进入dispatchTransformedTouchEvent得知 child!=null 时交给子View处理
                //如果子类选择消费此次事件，则结束此次事件传递，不再询问其他子View。
                if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
                    //为mFirstTarget重新赋值
                    newTouchTarget = addTouchTarget(child, idBitsToAssign);
                    alreadyDispatchedToNewTouchTarget = true;
                    break;
                }
            }
        }

        //子View未消费，交给父类自己处理。ViewGroup的父类本身也是一个View
        if (mFirstTouchTarget == null) {
            // No touch targets so treat this as an ordinary view.
            handled = dispatchTransformedTouchEvent(ev, canceled, null,
                    TouchTarget.ALL_POINTER_IDS);
        } else {//下面为了保险，如果已分发的事件traget!=newTouchTraget则再分发一次
            TouchTarget target = mFirstTouchTarget;
            while (target != null) {
                final TouchTarget next = target.next;
                if (alreadyDispatchedToNewTouchTarget && target == newTouchTarget) {
                    handled = true;
                } else {
                    final boolean cancelChild = resetCancelNextUpFlag(target.child)
                            || intercepted;
                    if (dispatchTransformedTouchEvent(ev, cancelChild,
                            target.child, target.pointerIdBits)) {
                        handled = true;
                    }
                }
            }

            return handled;
        }
            
        ···省略
```
   
   源码分析结束。
   ---
   
   
   
### 总结
   * 1.ViewGroup如何拦事件自己处理
       重写`onInterceptTouchEvent(ev)方法`
   * 2.ViewGroup如何拦截子View事件,不向下分发
        重写`onInterceptTouchEvent(ev)方法`并返回true
        或者直接调用`requestDisallowInterceptTouchEvent(false)`
   
   * 3.ViewGroup如何控制事件分发
       在不拦截时，如果子View不为Null,则交给子view去处理
       如果子View没有选择消费，或者子View为空则向上交给父类View来处理。也就是把自己当成一个View来处理了。
        
   * 3.子View事件被父类拦截后如何保证依然能获取到事件
        调用`getParent().requestDisallowInterceptTouchEvent(true)`,保证自己一定能获取到事件
        如果还拿不到那就两个`getParent().getParent()`
        
   实用场景（来自鸿神 -实力Carry）：
        比如你需要写一个类似slidingmenu的左侧隐藏menu，主Activity上有个Button、ListView或者任何可以响应点击的View。
        你在当前View上死命的滑动，菜单栏也出不来；因为MOVE事件被子View处理了~ 
        你需要这么做：在ViewGroup的dispatchTouchEvent中判断用户是不是想显示菜单。
        如果是，则在onInterceptTouchEvent(ev)拦截子View的事件；自己进行处理。
        这样自己的onTouchEvent就可以顺利展现出菜单栏了~~
        
        简单来说就是在你需要的时候，拦截事件并利用ViewGroup的逻辑：child= null时自行处理的套路，来保证整个事件的随意使用。
    
           
   有关事件分发更深入的认识可以参考博客[事件分发流程-张兴业](http://www.cnblogs.com/xyzlmn/p/3641704.html)