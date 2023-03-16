package com.example.xingyue.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.xingyue.R;
import com.example.xingyue.base.BaseApplication;


/**
 * UI加载器，用于加载请求的四种不同情况
 */
public abstract class UILoader extends FrameLayout {

    private View mLoadingView;
    private View mSuccessView;
    private View mNetworkErrorView;
    private View mEmptyView;
    private OnRetryClickListener myOnRetryClickListener=null;



    public enum UIStatus{
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context,null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }


    /**
     * 上方两种方法接连跳跃，从而保证了唯一的入口
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void updateStatus(UIStatus status) {
        mCurrentStatus=status;
        if (BaseApplication.getHandler() != null) {
            BaseApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    switchUIByCurrentStatus();
                }
            });
        }
    }


    private void init() { switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        if (mLoadingView == null) {
            mLoadingView=getLoadingView();
            addView(mLoadingView);
        }
        mLoadingView.setVisibility(mCurrentStatus== UIStatus.LOADING?VISIBLE:GONE);
        if (mSuccessView == null) {
            mSuccessView=getSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus== UIStatus.SUCCESS?VISIBLE:GONE);
        if (mNetworkErrorView == null) {
            mNetworkErrorView =getNetworkErrorView();
            addView(mNetworkErrorView);
        }
        mNetworkErrorView.setVisibility(mCurrentStatus== UIStatus.NETWORK_ERROR?VISIBLE:GONE);
        if (mEmptyView == null) {
            mEmptyView=getEmptyView();
            addView(mEmptyView);
        }
        mEmptyView.setVisibility(mCurrentStatus== UIStatus.EMPTY?VISIBLE:GONE);
    }

    protected View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty,this,false);
    }

    protected View getNetworkErrorView() {
        View network_error_view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_error,
                this,
                false);
        LinearLayout linearLayout=network_error_view.findViewById(R.id.network_error_icon);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                if (myOnRetryClickListener != null) {
                    myOnRetryClickListener.onRetryClick();
                }
            }
        });
        return network_error_view;
    }

    protected abstract View getSuccessView(ViewGroup container);

    protected View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading,this,false);
    }

    public void setOnRetryClickListener(OnRetryClickListener mOnRetryClickListener){
        this.myOnRetryClickListener= mOnRetryClickListener;
    }
    public interface OnRetryClickListener {
        void onRetryClick();
    }
}
