package rg.funinglayout.views;


import android.os.Handler;
import android.os.Message;

/**
 * Created by user on 2017/7/9.
 */

public class HelpHandler {

    private int currentProgress = 0;
    private int lastProgress = 0;
    private OnHelpHandlerSendNotice onHelpHandlerSendNotice;
    private  Handler handler;
    public void setOnHelpHandlerSendNotice(OnHelpHandlerSendNotice onHelpHandlerSendNotice) {
        this.onHelpHandlerSendNotice = onHelpHandlerSendNotice;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = msg.arg1;
                HelpHandler.this.onHelpHandlerSendNotice.receiver(progress);
            }
        };
    }

    public HelpHandler() {

    }

    public void startCutting(int currentProgress){
        this.currentProgress = currentProgress;
        for(int i = 1 ; i<=currentProgress;i++){
            Message message = new Message();
            message.arg1 = i;
            handler.sendMessageDelayed(message,1000);
        }
    }

    public interface  OnHelpHandlerSendNotice{
        void receiver(int progress);
    }


    public void setLastProgress(int lastProgress) {
        this.lastProgress = lastProgress;
    }

}
