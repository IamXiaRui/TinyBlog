package com.tinyblog.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author xiarui
 * @date 2016/10/12 9:35
 * @desc 自定义静态圆形背景ImageView
 */
public class StaticCircleImageView extends ImageView {
    public StaticCircleImageView(Context context) {
        super(context);
    }

    public StaticCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#42A5F5"));
        //画圆
        canvas.drawCircle(0.5f * getWidth(), -0.2f * getHeight(), 1.2f * getHeight(), paint);
        //TODO：也可以画很多圆
    }
}
