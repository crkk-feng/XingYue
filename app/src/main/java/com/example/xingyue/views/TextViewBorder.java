package com.example.xingyue.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.xingyue.R;


/**
 * Created by lp on 2016/9/21.
 */
public class TextViewBorder extends androidx.appcompat.widget.AppCompatTextView {
    private static final int STROKE_WIDTH = 2;
    private int borderCol;

    private Paint borderPaint;

    public TextViewBorder(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TextViewBorder, 0, 0);
        try {
            borderCol = a.getInteger(R.styleable.TextViewBorder_borderColor, 0);//0 is default
        } finally {
            a.recycle();
        }

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(STROKE_WIDTH);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (0 == this.getText().toString().length())
            return;

        borderPaint.setColor(borderCol);


        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();

        RectF r = new RectF(2, 2, w - 2, h - 2);
        canvas.drawRoundRect(r, 5, 5, borderPaint);
        super.onDraw(canvas);
    }

    public int getBordderColor() {
        return borderCol;
    }

    public void setBorderColor(int newColor) {
        borderCol = newColor;
        invalidate();
        requestLayout();
    }

}
