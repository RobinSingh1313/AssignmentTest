package com.example.assignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularArcView extends View {

    private int mColor;
    private int mStartAngle;
    private int mSweepAngle;
    private Paint mPaint;

    public CircularArcView(Context context) {
        super(context);
        init();
    }

    public CircularArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mColor = Color.RED;
        mStartAngle = 0;
        mSweepAngle = 120;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawArc(rect, mStartAngle, mSweepAngle, false, mPaint);
    }

    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }

    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    public void setSweepAngle(int sweepAngle) {
        mSweepAngle = sweepAngle;
        invalidate();
    }

}

