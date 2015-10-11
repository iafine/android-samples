package com.hyyy.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;

import static java.lang.Math.abs;

/**
 * Project name: AndroidTraining
 * Author: hyyy
 * Date: 15/10/11 下午5:31
 * Description:
 * **************************************************
 * Github: http://github.com/castial/android-samples
 * Blog: http://castial.github.io
 * **************************************************
 */
public class DrawUtils extends View{

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public DrawUtils(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);

        //获取设备尺寸
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawCircle(canvas);
        //drawArc(canvas, true);
        //drawLine(canvas);
        //drawOval(canvas);
        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {
        RectF rect = new RectF(0, (mHeight-mWidth)/2, mWidth, mHeight - (mHeight-mWidth)/2);
        canvas.drawRect(rect, mPaint);
    }

    /**
     * 画一个椭圆
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        RectF oval = new RectF(0, 0, mWidth, mHeight);
        canvas.drawOval(oval, mPaint);
    }

    /**
     * 画一条直线
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, 0, mWidth, mHeight, mPaint);
        canvas.drawLine(mWidth, 0, 0, mHeight, mPaint);
        canvas.drawLine(0, mHeight/2, mWidth, mHeight/2, mPaint);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mPaint);
    }

    /**
     * 或一个弧形
     * @param cancas
     * @param isUseCenter
     */
    private void drawArc(Canvas cancas, boolean isUseCenter) {
        RectF rect = new RectF(0, (mHeight-mWidth)/2, mWidth, mWidth + (mHeight-mWidth)/2);
        cancas.drawArc(rect, 180, 90, isUseCenter, mPaint);
    }

    /**
     *根据手机宽高画一个半径为二分之一宽的圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {

        if(mWidth < mHeight){
            canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, mPaint);
        }else{
            canvas.drawCircle(mWidth/2, mHeight/2, mHeight/2, mPaint);
        }
    }
}
