## 事件分发机制的学习-View篇
** 参照鸿神博客学习 **
####  一 、继承Button 重写事件处理相关的方法 

   * dispatchTouchEvent
   * onTouchEvent方法
   * 为View添加onTouch事件

按下Button 稍作滑动，触发ACTION_DOWN、ATION_MOVE、ACTION_UP三个事件

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
 
 **日志结论：三个方法的调用顺序为**
     1. dipatchTouchEvent 
            ↓
     2. onTouch 
            ↓
     3. onTouchEvent
     
     
***
     
#### 二、 按顺序进源码分析

   * dipatchTouchEvent 方法
 
 ```java
 
 //先看方法注解。任何第一次见到的方法都应该先看注解
  /**
      * Pass the touch screen motion event down to the target view, or this
      * view if it is the target.
      //通过触摸屏幕移动到目标View,或者当前View就是目标View
      //目标View：屏幕上显示的View都算
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
             //有焦点并且获取了此事件，则使用默认事件调度。呃-就是有焦点并且接收到了事件-就往下处理
             event.setTargetAccessibilityFocus(false);
         }
         //先设置一个返回，默认= false 不消费。
         boolean result = false;
        
        //判断是否是键盘输入事件，如果先传递给输入事件的onTouchEvent,并且还会继续执行后面的代码
         if (mInputEventConsistencyVerifier != null) {
             mInputEventConsistencyVerifier.onTouchEvent(event, 0);
         }
        
        //获取事件膜-这里不是太明白
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
             //1. 是否添加了onTouchlistener,2.是否为enabled状态，onTouch中返回true消费了此次事件
             if (li != null && li.mOnTouchListener != null
                     && (mViewFlags & ENABLED_MASK) == ENABLED
                     && li.mOnTouchListener.onTouch(this, event)) {
                 result = true;
             }
            //上面没有没有消费，在onTouchEvent中返回为true进行消费。
             if (!result && onTouchEvent(event)) {
                 result = true;
             }
         }
        
         if (!result && mInputEventConsistencyVerifier != null) {
             mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
         }
        
        //判断手势结束滚动
         // Clean up after nested scrolls if this is the end of 
         // also cancel it if we tried an ACTION_DOWN but wea gesture; didn't want the rest
         // of the gesture.
         if (actionMasked == MotionEvent.ACTION_UP ||
                 actionMasked == MotionEvent.ACTION_CANCEL ||
                 (actionMasked == MotionEvent.ACTION_DOWN && !result)) {
             stopNestedScroll();
         }
 
         return result;
     }
 
 ```
 
 **dispatchTouchEvent方法处理概括**
    1 . 首要条件：目标View是否有焦点，无焦点返回false
    2 . 是否添加了onTouchListener，是否为可用状态，是否在onTouch中处理。都满足 返回true 表示已消费
    3 . 在onTouch没有消费（result= false），且选择在onTouchEvent中进行处理，则返回true 表示已消费
 
**这就解释了日志中的处理顺序:dispatchTouchEvent → onTouch → onTouchEvent。
  所以对View设置了onTouchListener那么View自己的OnTouchEvent就不会执行了**
 
   * onTouchEvent 方法

```java
 /**
     * Implement this method to handle touch screen motion events.
        实现此方法来处理屏幕触摸事件
     * <p>
     * If this method is used to detect click actions, it is recommended that
      the actions be performed by implementing and calling
      如果这个方法是用来监测点击动作，建议通过调用实现此方法来处理action
     * {@link #performClick()}. This will ensure consistent system behavior,
     * including:
     * <ul>
     * <li>obeying click sound preferences
     * <li>dispatching OnClickListener calls
     * <li>handling {@link AccessibilityNodeInfo#ACTION_CLICK ACTION_CLICK} when
     * accessibility features are enabled
     * </ul>
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     //事件被处理返回 true 否则返回false
     */
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        //获取事件触发点的 横轴坐标
        final float y = event.getY();
        //获取事件触发点的 纵坐标
        final int viewFlags = mViewFlags;
        //view状态标记
        final int action = event.getAction();
        //获取 事件类型
        
        if ((viewFlags & ENABLED_MASK) == DISABLED) {
            if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
                setPressed(false);
            }
            // A disabled view that is clickable still consumes the touch
             events, it just doesn't respond to them.
            return (((viewFlags & CLICKABLE) == CLICKABLE
                    || (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)
                    || (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE);
        }
        //上面判断viewFlags 是否被已禁用，如果被禁用并且有可点击状态则消费此次事件，但不做处理

        if (mTouchDelegate != null) {
            if (mTouchDelegate.onTouchEvent(event)) {
                return true;
            }
        }
        //是否由代理执行

        if (((viewFlags & CLICKABLE) == CLICKABLE ||
                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) ||
                (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE) {
                //判断是否可点，是否可长按，上下文是否可点
                //进入到这个if判断里面，最终都会返回true 表示已消费此次传递的事件
                
                
                //源代码中顺序不方便阅读，下面我们给ACTION换一下位置，从上到下按照触发顺序来分析
                //ACTION_DOWN → ACTION_MOVE → ACTION_UP → ACTION_CANCEL
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mHasPerformedLongPress = false;

                    if (performButtonActionOnTouchDown(event)) {
                        break;
                    }

                    // Walk up the hierarchy to determine if we're inside a scrolling container.
                    boolean isInScrollingContainer = isInScrollingContainer();

                    // For views inside a scrolling container, delay the pressed feedback for
                    // a short period in case this is a scroll.
                    if (isInScrollingContainer) {
                        mPrivateFlags |= PFLAG_PREPRESSED;
                        if (mPendingCheckForTap == null) {
                            mPendingCheckForTap = new CheckForTap();
                        }
                        mPendingCheckForTap.x = event.getX();
                        mPendingCheckForTap.y = event.getY();
                        postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else {
                        // Not inside a scrolling container, so show the feedback right away
                        setPressed(true, x, y);
                        checkForLongClick(0);
                    }
                    break;
                 case MotionEvent.ACTION_MOVE:
                     drawableHotspotChanged(x, y);
 
                     // Be lenient about moving outside of buttons
                     if (!pointInView(x, y, mTouchSlop)) {
                         // Outside button
                         removeTapCallback();
                         if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
                             // Remove any future long press/tap checks
                             removeLongPressCallback();
 
                             setPressed(false);
                         }
                     }
                     break;          
                case MotionEvent.ACTION_UP:
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                    if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {
                        // take focus if we don't have it already and we should in
                        // touch mode.
                        boolean focusTaken = false;
                        if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                            focusTaken = requestFocus();
                        }

                        if (prepressed) {
                            // The button is being released before we actually
                            // showed it as pressed.  Make it show the pressed
                            // state now (before scheduling the click) to ensure
                            // the user sees it.
                            setPressed(true, x, y);
                       }

                        if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
                            // This is a tap, so remove the longpress check
                            removeLongPressCallback();

                            // Only perform take click actions if we were in the pressed state
                            if (!focusTaken) {
                                // Use a Runnable and post this rather than calling
                                // performClick directly. This lets other visual state
                                // of the view update before click actions start.
                                if (mPerformClick == null) {
                                    mPerformClick = new PerformClick();
                                }
                                if (!post(mPerformClick)) {
                                    performClick();
                                }
                            }
                        }

                        if (mUnsetPressedState == null) {
                            mUnsetPressedState = new UnsetPressedState();
                        }

                        if (prepressed) {
                            postDelayed(mUnsetPressedState,
                                    ViewConfiguration.getPressedStateDuration());
                        } else if (!post(mUnsetPressedState)) {
                            // If the post failed, unpress right now
                            mUnsetPressedState.run();
                        }

                        removeTapCallback();
                    }
                    mIgnoreNextUpEvent = false;
                    break;

                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    removeTapCallback();
                    removeLongPressCallback();
                    mInContextButtonPress = false;
                    mHasPerformedLongPress = false;
                    mIgnoreNextUpEvent = false;
                    break;
            }

            return true;
        }

        return false;
    }

```
   


        
        