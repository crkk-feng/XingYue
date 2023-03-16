package com.example.xingyue.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.xingyue.R;


@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView {

    //旋转角度
    private int rotateDegree;

    private boolean mNeedRotation=false;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotation=true;
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree+=30;
                rotateDegree=rotateDegree<=360?rotateDegree:0;
                invalidate();
                //判断是否继续旋转
                if (mNeedRotation) {
                    postDelayed(this, 80);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //从windows中解绑
        mNeedRotation=false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 第一个参数为旋转的角度
         * 第二个参数为旋转中心点的横坐标
         * 第三个参数为旋转中心点的纵坐标
         */
        canvas.rotate(rotateDegree,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }
}
