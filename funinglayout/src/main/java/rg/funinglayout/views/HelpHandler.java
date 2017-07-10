package rg.funinglayout.views;


import android.os.Handler;
import android.os.Message;

import static android.R.attr.delay;
import static android.R.id.message;

/**
 * Created by user on 2017/7/9.
 */

public class HelpHandler {
    private OnStartCuttinProgressListener onStartCuttinProgressListener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            onStartCuttinProgressListener.onCutting(progress);
        }
    };

    public HelpHandler(OnStartCuttinProgressListener l) {
        this.onStartCuttinProgressListener = l;
    }

    public void startCutting(int currentProgress) {
        int interval = 1500 / currentProgress;
        int delay = 0;
        for (int i = 1; i <= currentProgress; i++) {
            Message message = handler.obtainMessage();
            message.arg1 = i;
            handler.sendMessageDelayed(message, delay);
            delay += interval;
        }
    }

    public interface OnStartCuttinProgressListener {
        void onCutting(int progress);
    }


}
