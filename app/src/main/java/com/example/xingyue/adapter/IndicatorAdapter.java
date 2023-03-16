package com.example.xingyue.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.xingyue.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 用于MagicIndicator作适配器
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {

    private String[] titles;
    private Context mContext;
    private OnIndicatorTabListener mOnIndicatorTabListener;

    public IndicatorAdapter(Context mContext) {
        this.mContext = mContext;
        titles= mContext.getResources().getStringArray(R.array.indicator_name);
    }


    @Override
    public int getCount() {
        if (titles != null) {
            return titles.length;
        }else {
            return 0;
        }
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        //创建View
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况为灰色
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffff"));
        //设置选中情况为黑色
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        //单位sp
        colorTransitionPagerTitleView.setTextSize(16);
        //设置要显示的内容
        colorTransitionPagerTitleView.setText(titles[index]);
        //设置点击事件
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击时调用，切换ViewPager的内容
                if (mOnIndicatorTabListener != null) {
                    mOnIndicatorTabListener.onTabClick(index);
                }
            }
        });

        //返回View
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.parseColor("#ffffff"));
        return linePagerIndicator;    }

    public void setOnIndicatorTabClickListener(OnIndicatorTabListener onIndicatorTabClickListener){
        this.mOnIndicatorTabListener=onIndicatorTabClickListener;
    }
    /**
     * 暴露接口用于外部点击Tab时修改ViewPager的呈现内容
     */
    public interface OnIndicatorTabListener{
        void onTabClick(int index);
    }
}
