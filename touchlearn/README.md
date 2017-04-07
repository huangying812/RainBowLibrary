## 事件分发机制的学习-View篇

>站在巨人的肩膀上，攀上更高处，看向更远处。

####  一 、继承Button 重写事件处理相关的方法 

   * dispatchTouchEvent
   * onTouchEvent方法
   * 为View添加onTouch事件

按下Button 稍做滑动，触发ACTION_DOWN、ATION_MOVE、ACTION_UP三个事件

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
 
 ` dispatchTouchEvent方法处理概括
    1 . 首要条件：目标View是否有焦点，无焦点返回false
    2 . 是否添加了onTouchListener，是否为可用状态，是否在onTouch中处理。都满足 返回true 表示已消费
    3 . 在onTouch没有消费（result= false），且选择在onTouchEvent中进行处理，则返回true 表示已消费 `
 
**这就解释了日志中的处理顺序:dispatchTouchEvent → onTouch → onTouchEvent。
  所以对View设置了onTouchListener那么View自己的OnTouchEvent就不会执行了**
 
 ***
 
   * onTouchEvent 方法
    源代码中顺序不方便阅读，下面我们给ACTION换一下位置，从上到下按照触发顺序来分析
    ACTION_DOWN → ACTION_MOVE → ACTION_UP → ACTION_CANCEL
    先对方法内的代码进行简单的阅读并添加注解，按照事件触发的顺序，逐个解析
    
     补充一下，PFLAG_PREPRESSED、PFLAG_PRESSED。
         * PFLAG_PREPRESSED:处理长按事件的标识。在ACTION_DOWN触发后，开启延时线程处理长按事件 但是延时间还未结束的状态
         * PFLAG_PRESSED :view是否被按下，只在setPressed()方法内设置，判断view是否被按下
      在onTouchEvent 这两个标记被大量用于判断，理解了这两个标记的意义对理解整个处理逻辑会极大帮助。一开始光看注释我也是晕的，后来读到后面结合上下文明白。
        
    
```java
 /**
     * Implement this method to handle touch screen motion events.
        实现此方法来处理屏幕触摸事件
     * <p>
     * If this method is used to detect click actions, it is recommended that
      the actions be performed by implementing and calling
      如果这个方法是用来监测点击动作，建议通过调用实现此方法来处理action
     * @return True if the event was handled, false otherwise.
     //事件被处理返回 true 否则返回false
     */
    public boolean onTouchEvent(MotionEvent event) {
        //获取事件触发点的 横轴坐标
        final float x = event.getX();
        //获取事件触发点的 纵坐标
        final float y = event.getY();
        //view状态标记
        final int viewFlags = mViewFlags;
         //获取 事件类型 
        final int action = event.getAction();
        
        if ((viewFlags & ENABLED_MASK) == DISABLED) {
            if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
                setPressed(false);
            }
            // A disabled view that is clickable still consumes the touch
             //events, it just doesn't respond to them.
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
        
        //进入到事件消费的逻辑，进入到这个if判断里面，最终都会返回true 表示已消费此次传递的事件
        if (((viewFlags & CLICKABLE) == CLICKABLE ||
                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) ||
                (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE) {
                //判断是否可点，是否可长按，上下文是否可点
             
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                   //将是否要执行长按 标记 设为 false
                    mHasPerformedLongPress = false;
                 
                    //直接执行的ButtonAction处理 消费了此次事件-暂时还不太明白
                    if (performButtonActionOnTouchDown(event)) {
                        break;
                    }
                     //确定是否在滚动容器内   
                    // Walk up the hierarchy to determine if we're inside a scrolling container.
                    boolean isInScrollingContainer = isInScrollingContainer();
                    
                    //view 在滚动容器内，延迟短期按下的反馈防止这是一个滚动
                    // For views inside a scrolling container, delay the pressed feedback for
                     a short period in case this is a scroll.
                    if (isInScrollingContainer) {
                    //mPrivateFlags 设置为 PFLAG_PREPRESSED（按下状态保存）
                        mPrivateFlags |= PFLAG_PREPRESSED;
                        //创建一个检查器
                        if (mPendingCheckForTap == null) {
                            mPendingCheckForTap = new CheckForTap();
                        }
                        mPendingCheckForTap.x = event.getX();
                        mPendingCheckForTap.y = event.getY();
                        //发送一个延时为TapTimeout = 100 ms的消息，最终会进入 checkForLongClick()中检查执行长按
                        postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else {
                         //不再滚动容器内，立刻反馈
                        // Not inside a scrolling container, so show the feedback right away
                        setPressed(true, x, y);
                        //检查执行长按，延时事件为0
                        checkForLongClick(0);
                    }
                    break;
                 case MotionEvent.ACTION_MOVE:
                 //记录变化的触摸点坐标
                     drawableHotspotChanged(x, y);
                
                        //如果移动到了buttons以外的区域
                     // Be lenient about moving outside of buttons
                     if (!pointInView(x, y, mTouchSlop)) {
                     
                        //移除回掉-里面移除的是CheckForTap，在滚动容器内的延时线程
                         // Outside button
                         removeTapCallback();
                         
                         //判断mPrivateFlags 是否被设置了PFLAG_PRESSED标记
                         if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
                              //移除即将执行的长按回掉
                             // Remove any future long press/tap checks
                             removeLongPressCallback();
                             setPressed(false);
                         }
                     }
                     break;          
                case MotionEvent.ACTION_UP:
                    //是否在执行长按点击事件
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                    //已被按下 或者触发长按事件
                    if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {
                        // take focus if we don't have it already and we should in
                        touch mode.
                        boolean focusTaken = false;
                        if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                            focusTaken = requestFocus();
                        }
                        
                        if (prepressed) {
                            //为了确保用户能看见按下的状态，我们再设置一次按下的状态
                            // The button is being released before we actually
                             showed it as pressed.  Make it show the pressed
                             state now (before scheduling the click) to ensure
                             the user sees it.
                            setPressed(true, x, y);
                       }
                        //不是长按事件，也没有忽略后续的upEvent
                        if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
                            //移除长按处理回掉 既移除CheckForLongPress线程
                            // This is a tap, so remove the longpress check
                            removeLongPressCallback();
                            //只执行一个点击动作
                            // Only perform take click actions if we were in the pressed state
                            if (!focusTaken) {
                             //使用Runnable 不如直接调用performClick ,这可以在点击动作开始前就更新视图
                                 Use a Runnable and post this rather than calling
                                 performClick directly. This lets other visual state
                                 of the view update before click actions start.
                                if (mPerformClick == null) {
                                    mPerformClick = new PerformClick();
                                }
                                  //如果添加PerformClick到线程队列，此线程中会调用 performClick()
                                  //如果失败则立即直接调用performClick()
                                if (!post(mPerformClick)) {
                                    performClick();
                                }
                                //这一做地目的一时不太明白，希望有同学为我解惑呀
                            }
                        }

                        if (mUnsetPressedState == null) {
                            mUnsetPressedState = new UnsetPressedState();
                        }
                        //如果需要处理长按事件，启动延时64ms的线程，同上添加到线程队列失败则立即调用run方法确保达到目的
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
   
   **1.ACTION_DOWN**
    核心代码:
  
```java
                   ··· 
                   
                   if (isInScrollingContainer) {
                        if (mPendingCheckForTap == null) {
                            mPendingCheckForTap = new CheckForTap();
                        }
                   ···
                   
                        postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else {
                   ···
                   
                        checkForLongClick(0);
                    }    
    
    
 ```
    
   上面的代码，主角有两个：
                           1.创建、执行**CheckForTap**线程并延迟100ms
                           2.调用checkForLongClick()方法
                           
   我们先进**CheckForTap**中看看他要做什么
   
```java
        private final class CheckForTap implements Runnable {
        public float x;
        public float y;
        @Override
        public void run() {
            mPrivateFlags &= ~PFLAG_PREPRESSED;
            setPressed(true, x, y);
            checkForLongClick(ViewConfiguration.getTapTimeout());
        }
    }     
```    
    
   为mPrivateFlags取消PFLAG_PREPRESSED标记
    发现最终它也要调用checkForLongClick()
    立刻追进这个方法里,我猜测是要触发长按了(看名字猜的哈哈)
    
```java

    private void checkForLongClick(int delayOffset) {
        if ((mViewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) {
            mHasPerformedLongPress = false;
            if (mPendingCheckForLongPress == null) {
                mPendingCheckForLongPress = new CheckForLongPress();
            }
            mPendingCheckForLongPress.rememberWindowAttachCount();
            postDelayed(mPendingCheckForLongPress,
                    ViewConfiguration.getLongPressTimeout() - delayOffset);
        }
    }   
```    
    
   不是长按事件处理，又套了一层。先将mHasPerformedLongPress再次设为false,为了确保长按一定没有被触发过
    这里又发送了一个延时线程，主角是**CheckForLongPress**
    
```java
    private final class CheckForLongPress implements Runnable {
               ···
          @Override
          public void run() {
               ···
                  if (performLongClick()) {
                      mHasPerformedLongPress = true;
                  }
               ···      
          }
      }
```      
        
   读谷爸的代码就是爽呀，performLongClick()，返回还是布尔值，为true 将mHasPerformedLongPress= true.标记长按已被触发
    其实看这段代码我一直在找一个主角 onLongClick().
       
```java
       public boolean performLongClick() {
           sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED);
           boolean handled = false;
           ListenerInfo li = mListenerInfo;
           if (li != null && li.mOnLongClickListener != null) {
               handled = li.mOnLongClickListener.onLongClick(View.this);
           }
           if (!handled) {
               handled = showContextMenu();
           }
           if (handled) {
               performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
           }
           return handled;
       }
        
```        
    
   一眼就找到了主角onLongClickListener，判断是否添加了OnLongClickListener，如果添加了则执行回调调用onLongClick方法，并且返回true。
    最终返回处理状态 handled 表示长按事件是否被消费，到此长按事件处理就结束了。
    
   但是！蛋蛋是!此处应该被打板子 - -！
    一开始有一个isInScrollingContainer的判断，进入checkForLongClick方法的延时时间不一样，原本我惯性理解为在滚动容器中处理的时间要比不在
    滚动容器中的触发时间长，还在纳闷这么搞的意义何在？多别扭。刚才想起来了这段代码：
    
```java
    postDelayed(mPendingCheckForLongPress,
                       ViewConfiguration.getLongPressTimeout() - delayOffset);   
```      

   我好傻，这样把延时时间一减最后整个时间轴总长度不就一样了吗都是500ms. 靠！不对直觉告诉我不是500ms
    从滚动容器中的逻辑进来，CheckForTap线程先延时了100ms才触发,触发后调用checkForLongClick方法给出延时补偿100ms
    那在checkForLongClick 方法中减去延时补偿，只减去了100ms。
    那就是如果在滚动容器中触发长按时间为400ms
    而在非滚动容器中触发长按时间为500ms.- - 好像区别也不大，但是为毛要这么玩呢~(强迫症)
    
 ***
    
   **2.ACTION_MOVE**  
    
```java
                    ···
                     if (!pointInView(x, y, mTouchSlop)) {
                        //移除回掉-移除的是CheckForTap，在滚动容器内的延时线程
                         // Outside button
                         removeTapCallback();
                         //判断mPrivateFlags 是否被设置了PFLAG_PRESSED标记(是否被点击)，如果是则说明长按的延时处理线程已经启动
                         则移除长按回掉线程
                         if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
                            //移除即将执行的长按回掉
                             // Remove any future long press/tap checks
                             removeLongPressCallback();
                             setPressed(false);
                         }
                     }
```    
   
   这里有两个移除回掉的方法要执行：
   removeTapCallback()
   removeLongPressCallback()
   结合上面的分析，这里调用顺序和对PFLAG_PRESSED的判断其实是必然的，暂且先放一下现看看移除了什么
   
```java
        /**
          移除定时线程~ 跪死在英语上了。。。
        * Remove the tap detection timer.
        */
       private void removeTapCallback() {
           if (mPendingCheckForTap != null) {
               mPrivateFlags &= ~PFLAG_PREPRESSED;
               removeCallbacks(mPendingCheckForTap);
           }
       }
   
 ```  
 
   1 . 判断是否创建了延时线程CheckForTap
   2 . 对mPrivateFlags取消 PFLAG_PREPRESSED标记（还有一个地方取消此标记是在CheckForTap线程执行后，所以上面对于PFLAG_PREPRESSED的解释是没错的）
   3 . 从线程队列中移除mPendingCheckForTap，只要没有被执行就一定会被取消，也就中断了长按事件
   ` 既：在滚动容器内如果再按下后100ms内你滑出了view的范围马上就会丢失长按事件 `
   
   
   
   后面调用removeLongPressCallback()为什么先判断mPrivateFlags是否包含PFLAG_PRESSED的标记？
   因为不是在滚动容器内触发，或者CheckForTap 已经执行了，这两者都会进入CheckForLongPress线程中，也都会调用` setPressed(true,x,y) `
   在其中为mViewFlags添加PFLAG_PRESSED标记,长按事件将要被处理。
   所以如果mPrivateFlags包含着PFLAG_PRESSED标记，则说明CheckForLongPress已被开启，需要移除CheckForLongPress线程，中断回掉。
  ` 既：在非滚动容器内触发长按，在500ms内移出了view的范围则会取消长按事件的处理 `
   
 ***
   
 **3.ACTION_UP** 
   核心代码：
   
```java
                ···
                    //是否将执行长按事件- mPrivateFlags包含PFLAG_PREPRESSED标记，长按的延时检测正在执行
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                    //已被按下 或者触发长按事件
                    if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed) {
                        ···
                        //不是长按事件，也没有忽略后续的upEvent
                        if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) {
                        ···         
                                //移除长按检测线程
                                removeLongPressCallback();
                                
                                if (mPerformClick == null) {
                                    mPerformClick = new PerformClick();
                                }
                                  //如果添加PerformClick到线程队列，此线程中会调用 performClick()
                                  //如果失败则立即直接调用performClick()
                                if (!post(mPerformClick)) {
                                    performClick();
                                }
                                //这么做地目的一时不太明白，希望有同学为我解惑呀
                            }
                        }

                        if (mUnsetPressedState == null) {
                            mUnsetPressedState = new UnsetPressedState();
                        }
                        //如果需要处理长按事件，启动延时64ms的线程
                        //同上添加到线程队列失败则立即调用run方法确保达到目的
                        if (prepressed) {
                            postDelayed(mUnsetPressedState,
                                    ViewConfiguration.getPressedStateDuration());
                        } else if (!post(mUnsetPressedState)) {
                            mUnsetPressedState.run();
                        }
                        //移除长按检测（长按事件还未执行）
                        removeTapCallback();
                    }
                 ···
   
```
   
   * 首先来看这两行代码，我觉这两行的信息两最大
   `boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;`
   ` if ((mPrivateFlags & PFLAG_PRESSED) != 0 || prepressed)`
            
   记得前面只有两个地方取消这个标记，一个是**CheckFotTap**的延时线程执行时，另一个是在触摸移除view范围后
   这里触发了ACTION_UP,只能是前者。
   根据ACTION_DOWN中的判断
   1.在滚动容器中触发ACRION_DOWN,在100ms后才会调用`setPressed(true,x,y)`,将PFLAG_PRESSED标记添加给mViewFlags
   所以在100ms 触发ACTION_UP `prepressed = true`
   2.在**非**滚动容器中触发ACTION_DOWN，会立即调用`setPressed(true,x,y)`,并开启处理长按事件回掉的延时线程
   所以在500ms内触发ACTION_UP `prepressed = true`
   
   
   100ms是非常短的，人的手指一般都无法在100ms内触发，所以下面的if判断基本一直是满足的。
   
   * 再来看`if (!mHasPerformedLongPress && !mIgnoreNextUpEvent) `
   mHasPerformedLongPress什么时候为true呢，还记的上面出现过的CheckForLongPress线程，只有在此线程中执行preformLongClick()
   返回true时mHasPerformedLongPress才等于true.如果没有设置长按监听或者在ACTION_DOWN 触发后的500ms内（在滚动容器内为400ms）
   触发ACTION_UP mHasPerformedLongPress都为false ,会进入这个if.(注意这里仅时进入此判断，并没对长按事件处理有任何干预)
   
   * 进入if里面
   先执行了`removeLongPressCallback();`
   移除CheckForLongPress线程，也就是说这里就中断了长按事件的检测，长按事件从这里就结束了不会再有回掉。
   接着创建了PerformClick线程，并添加了添加到消息队列失败判断。最终都会进入`performClick()`方法中.
   进入一探究竟
   
   ```java
         public boolean performClick() {
                final boolean result;
                final ListenerInfo li = mListenerInfo;
                if (li != null && li.mOnClickListener != null) {
                    playSoundEffect(SoundEffectConstants.CLICK);
                    li.mOnClickListener.onClick(this);
                    result = true;
                } else {
                    result = false;
                }
                sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
                return result;
            }
   
   ```
   
   久违了，终于看到onClick.毫无疑问这里就是对onClickListener监听的回掉。
   
   疑问？上面调用了`removeLongPressCallback()`方法移除了长按事件的检测，那是不是如果执行了onClick 长按事件就不会执行了呢？
   当然不是，只要onLongClick执行返回未false onClick就可以被触发。所以他们是可以同时存在的。
   在CheckForLongPress的run方法中是这样为 mHasPerformedLongPress改变状态的
   
```java
                    if (performLongClick()) {
                        mHasPerformedLongPress = true;
                    }

```
   可以看到，mHasPerformedLongPress只取决于performLongClick的返回值，并不关心方法内是否执行onLongClick.
   所以只要onLongClikc返回false,mHasPerformedLongPress就已然为false.在ACTION_UP中就满足
   `!mHasPerformedLongPress = true` 从而会执行onClick
   
   
   最后还剩两个操作 
   1.创建UnsetPressedState 并最终调用`setPressed(false)`,取消mViewFlags的PFLAG_PRESSED标记,并刷新视图。
   2.执行`removeTapCallback()`移除CheckForTap延时线程(在100ms内触发了ACTION_UP,才会取消)
   
   ***
   
   对于单个View整个事件分发流程的源码学习就结束了，收获还是蛮大的,对于添加监听和点击事件整个处理流程已然有了非常清晰的了解。之前也是看了很多的这方面的博客的
   到底不如自己细细分析来的深刻，读源码一开始挺费劲的，但是稍稍摸索就会发现其乐无穷之后根本停不下来。
   
   在学习过程中发现和知名博主以前记录源码稍有差别，因为谷爹也在更新嘛。
   本篇源码SDK版本为23.
   
   参照博客：[Android View 事件分发机制 源码解析 （上） ](http://blog.csdn.net/lmj623565791/article/details/38960443)