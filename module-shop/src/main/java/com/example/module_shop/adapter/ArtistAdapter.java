package com.example.module_shop.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module_shop.R;
import com.example.module_shop.data.ArtistBean;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.InnnerHolder> {
    private View rootView;
    private List<ArtistBean> mArtistList=new ArrayList<>();
    private artistClickListener mArtistClickListener;
    public ArtistAdapter(List<ArtistBean> mArtistList) {
        this.mArtistList = mArtistList;
    }

    public artistClickListener getmArtistClickListener() {
        return mArtistClickListener;
    }

    public void setmArtistClickListener(artistClickListener mArtistClickListener) {
        this.mArtistClickListener = mArtistClickListener;
    }

    @Override
    public InnnerHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_horizontal,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder( InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mArtistList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArtistClickListener != null) {
                    mArtistClickListener.onArtistClick((Integer) v.getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mArtistList != null) {
            return mArtistList.size();
        }
        return 0;
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {
        public InnnerHolder(View itemView) {
            super(itemView);
        }

        public void setData(ArtistBean artistBean) {
            //找到图片部分
            ImageView imageView=itemView.findViewById(R.id.video_image_item);
            TextView tvView=itemView.findViewById(R.id.tv_video_item);

            //设置数据
            tvView.setText(artistBean.getArtistName());

            Glide.with(imageView.getContext()).load(artistBean.getArtistImageId()).into(imageView);

            Log.d("ArtistAdapter","在此处设置数据");
        }
    }

    interface artistClickListener{
        void onArtistClick(int position);
    }
}
