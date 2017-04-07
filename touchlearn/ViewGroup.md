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
    
   * 1.`ViewGroup [dispatchTouchEvent]`
    
```java
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
                    //···省略
                    
                        boolean handled = false;
                        //onFilterTouchEventForSecurity 判断窗口是否被遮挡，如果被遮挡则不向下执行事件分发。
                        if (onFilterTouchEventForSecurity(ev)) {
                             final int action = ev.getAction();
                             //获取具体事件类型
                             final int actionMasked = action & MotionEvent.ACTION_MASK;
                              // Handle an initial down.
                               if (actionMasked == MotionEvent.ACTION_DOWN) {
                                // Throw away all previous state when starting a new touch gesture.
                                 // The framework may have dropped the up or cancel event for the previous gesture
                                  // due to an app switch, ANR, or some other state change.
                                  //取消和清除处理触摸事件的目标View,及其包含的子View
                                   cancelAndClearTouchTargets(ev);
                                   //重新设置触摸状态
                                   resetTouchState();
                                   }
                             
                        }
        
        
        
        
         }
  ```  