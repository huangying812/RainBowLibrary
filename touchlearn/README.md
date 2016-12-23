## 事件分发机制的学习
** 参照鸿神博客学习 **
1. 继承Button 重写事件处理相关的方法 
    dispatchTouchEvent
    onTouchEvent方法
    在Activity中添加onTouch事件

 日志：
 DOWN 事件
 D/TButton: dispatchTouchEvent = ACTON_DOWN
 D/MainActivity: onTouch = ACTON_DOWN
 D/TButton: onTouchEvent = ACTON_DOWN
 
 MOVE 事件
 D/TButton: dispatchTouchEvent = ACTION_MOVE
 D/MainActivity: onTouch = ACTION_MOVE
 D/TButton: onTouchEvent = ACTION_MOVE
 
 UP 事件
 D/TButton: dispatchTouchEvent = ACTION_UP
 D/MainActivity: onTouch = ACTION_UP
 D/TButton: onTouchEvent = ACTION_UP
 
 ** 日志结论：三个方法的调用顺序为  
     1. dipatchTouchEvent 
             ↓
     2. onTouch 
            ↓
     3. onTouchEvent
 按顺序进源码分析
 
 ```java
 
 //先看方法注解。任何第一次见到的方法都应该先看注解
  /**
      * Pass the touch screen motion event down to the target view, or this
      * view if it is the target.
      //通过触摸屏幕移动到目标View,或者当前View就是目标View
      //目标View：就是用来负责分发触摸事件的View，所有你摸到的View都算（布局重叠也算）
      * @param event The motion event to be dispatched.
      * @return True if the event was handled by the view, false otherwise.
        //如果当前View处理了此事件返回true,否则返回false
      */
     public boolean dispatchTouchEvent(MotionEvent event) {
         // If the event should be handled by accessibility focus first.
         //如果此event作为第一个可访问的焦点被处理
         if (event.isTargetAccessibilityFocus()) {
             // We don't have focus or no virtual descendant has it, do not handle the event.
            //我们没有焦点或者没有虚拟子类持有焦点，则不处理此事件
             if (!isAccessibilityFocusedViewOrHost()) {
                 return false;
                 //这里直接返回false,不处理此事件。这里一直出现一个词 focus.
                 //也就是说，target View 获取不到焦点（我们将focusable = false） 将直接跳过此次事件处理，他还是能获取到触摸事件，只是跳过处理
             }
             // We have focus and got the event, then use normal event dispatch.
             //我们由焦点并且获取了此事件，则使用默认事件调度。呃-就是有焦点并且接收到了事件-就开始处理呗
             event.setTargetAccessibilityFocus(false);
         }
         //先设置一个返回，默认= false 不消费。
         boolean result = false;
        
        //判断是否是键盘输入事件，如果先传递给输入事件的onTouchEvent,并且还会继续执行后面的代码
         if (mInputEventConsistencyVerifier != null) {
             mInputEventConsistencyVerifier.onTouchEvent(event, 0);
         }
        
        //获取事件膜
         final int actionMasked = event.getActionMasked();
         if (actionMasked == MotionEvent.ACTION_DOWN) {
             // Defensive cleanup for new gesture
             //防止新手势被清楚，停止嵌套滚动
             stopNestedScroll();
         }
        
        //这里过滤判断里面 是判断窗口是否被遮挡，如果被遮挡则终止处理返回false
         if (onFilterTouchEventForSecurity(event)) {
             //noinspection SimplifiableIfStatement
             ListenerInfo li = mListenerInfo;
             //下面的逻辑就是判断是否消费此事件
             //1. 是否添加了onTouchlistener,是否为enabled状态
             if (li != null && li.mOnTouchListener != null
                     && (mViewFlags & ENABLED_MASK) == ENABLED
                     && li.mOnTouchListener.onTouch(this, event)) {
                 result = true;
             }
            //上面没有没有消费并且选择覆盖了onTouchEvent方法来处理事件。
             if (!result && onTouchEvent(event)) {
                 result = true;
             }
         }
        
         if (!result && mInputEventConsistencyVerifier != null) {
             mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
         }
        
        //判断手势结束滚动
         // Clean up after nested scrolls if this is the end of a gesture;
         // also cancel it if we tried an ACTION_DOWN but we didn't want the rest
         // of the gesture.
         if (actionMasked == MotionEvent.ACTION_UP ||
                 actionMasked == MotionEvent.ACTION_CANCEL ||
                 (actionMasked == MotionEvent.ACTION_DOWN && !result)) {
             stopNestedScroll();
         }
 
         return result;
     }
 
 ```
 
 
 
 


       
        
        
        
        