## 触摸事件分发机制学习 -ViewGroup篇

在上一篇[事件分发机制的学习-View篇]()解析了单独view的事件传递和处理。
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
    这里有一个疑问了，`onTouchEvent`事件怎么没有触发呢。我猜想这一定和`onInterceptTouchEvent`脱不了干系。
    
   下面就杀进源码看个究竟。
    
#### 1.`ViewGroup - [dispatchTouchEvent]方法`(这里代码比较长，分段来看)
 
   * 拦截判断 为`intercepted`初始化

```java
    public boolean  dispatchTouchEvent(MotionEvent ev) {
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
                        
   `onInterceptTouchEvent`方法在父类默认返回false 子类可以覆盖重写              
   上面有一个flag非常关键 `FLAG_DISALLOW_INTERCEPT` “不拦截”标记                       
   `disallowIntercept = false` 时调用`onInterceptTouchEvent`方法
   **`mGroupFlags`** 添加 不拦截的tag是在 `requestDisallowInterceptTouchEvent`方法中
   调用`requestDisallowInterceptTouchEvent(true)`则添加不许拦截的tag 从而改变上面得判断                                
   
   * 派发事件
   
    上面我们看到为`intercepted`赋值了，下面就用到了
```java
            //事件是否被取消
           final boolean canceled = resetCancelNextUpFlag(this)
                             || actionMasked == MotionEvent.ACTION_CANCEL;   
            
          if (!canceled && !intercepted) {
                
             If the event is targeting accessiiblity focus we give it to the
              view that has accessibility focus and if it does not handle it
               we clear the flag and dispatch the event to all children as usual.
               We are looking up the accessibility focused host to avoid keeping
               state since these events are very rare.
            
          
          }



```
   
                       