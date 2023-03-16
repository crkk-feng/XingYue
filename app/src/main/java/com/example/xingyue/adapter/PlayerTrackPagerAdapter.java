package com.example.xingyue.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.xingyue.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayerTrackPagerAdapter extends PagerAdapter {
    private static final String TAG = "PlayerTrackPagerAdapter";
    private List<Track> mData=new ArrayList<>();

    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_pager,
                container,
                false);
        container.addView(itemView);
        //设置数据
        //找到控件
        ImageView iV_View=itemView.findViewById(R.id.track_pager_item);
        //设置图片
        Track track = mData.get(position);
        String coverUrlLarge = track.getCoverUrlLarge();
        Log.d(TAG,"Container.getContext()"+container.getContext());
        Log.d(TAG,"coverUrlLarge"+coverUrlLarge);
        if (iV_View != null) {
            Log.d(TAG,"IV_VIEW IS NOT NULL");
        }

        if (coverUrlLarge != null) {
            Picasso.with(container.getContext()).load(coverUrlLarge).into(iV_View);
        }
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public void setData(List<Track> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

}
