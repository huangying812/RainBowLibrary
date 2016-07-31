package com.zsw.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CustomView extends View implements View.OnClickListener {
    //弄一只画笔
    private Paint paint;

    private int count = 0;

    public CustomView(Context context) {
        super(context);
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.setOnClickListener(this);
    }

    public CustomView(Context con, AttributeSet set) {
        super(con, set);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //给画笔设置颜色
        paint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        canvas.drawText(String.valueOf(count), getWidth() / 2, getHeight() / 2, paint);
    }


    @Override
    public void onClick(View v) {
        count++;
        //调用重绘判断
        invalidate();
    }
}
