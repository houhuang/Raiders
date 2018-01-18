package com.jd.raiders.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.icu.math.BigDecimal;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by houhuang on 18/1/11.
 */
public class TrigonView extends View{

    public TrigonView(Context context) {
        super(context);
    }

    public TrigonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //三角形
        Paint p = new Paint();
        p.setColor(Color.RED);
        //实例化路径
        Path path = new Path();
//        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(this.getWidth(), 0);
        path.lineTo(0, this.getHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);


        //五角星
        Paint wPaint = new Paint();
        Path  wPath = new Path();
        int width=getWidth();
        float r=width/5;
        canvas.translate((float)(getWidth()/3.5), (float)(getWidth()/3.5) );
        canvas.rotate(30);


        wPath.moveTo(0, -r);//A

        wPath.lineTo(  r* sin(36), r*cos(36));//C

        wPath.lineTo( -r* sin(72), -r*cos(72));//E

        wPath.lineTo(  r* sin(72), -r*cos(72));//B

        wPath.lineTo(  -r* sin(36), r*cos(36));//D

        wPath.close();

        wPaint.setAntiAlias(true);
        wPaint.setColor(Color.YELLOW);
        wPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        wPaint.setStrokeWidth(5);
        canvas.drawPath(wPath, wPaint);

    }

    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
}
