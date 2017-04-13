//子View未消费，交给父类自己处理
  if(mFirstTouchTarget==null){
          // No touch targets so treat this as an ordinary view.
          handled=dispatchTransformedTouchEvent(ev,canceled,null,
          TouchTarget.ALL_POINTER_IDS);
            }else{//下面为了保险，如果已分发的事件traget!=newTouchTraget则再分发一次
            TouchTarget target=mFirstTouchTarget;
          while(target!=null){
               final TouchTarget next=target.next;
               if(alreadyDispatchedToNewTouchTarget&&target==newTouchTarget){
                  handled=true;
                     }else{
              final boolean cancelChild=resetCancelNextUpFlag(target.child)
                   ||intercepted;
                if(dispatchTransformedTouchEvent(ev,cancelChild,
                    target.child,target.pointerIdBits)){
                        handled=true;
                     }
                }