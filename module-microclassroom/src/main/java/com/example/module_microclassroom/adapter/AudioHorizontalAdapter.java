package com.example.module_microclassroom.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module_microclassroom.R;
import com.example.module_microclassroom.bean.AudioBean;

import java.util.ArrayList;
import java.util.List;

public class AudioHorizontalAdapter extends RecyclerView.Adapter<AudioHorizontalAdapter.InnnerHolder> {
    private View rootView;
    private List<AudioBean> mArtistList=new ArrayList<>();

    public AudioHorizontalAdapter(List<AudioBean> mArtistList) {
        this.mArtistList = mArtistList;
    }

    @Override
    public InnnerHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_horizontal,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder( InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mArtistList.get(position));

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

        public void setData(AudioBean artistBean) {
            //找到图片部分
            ImageView imageView=itemView.findViewById(R.id.video_image_item);
            TextView tvView=itemView.findViewById(R.id.tv_video_item);

            //设置数据
            tvView.setText(artistBean.getArtistName());

            Glide.with(imageView.getContext()).load(artistBean.getArtistImageId()).into(imageView);

            Log.d("ArtistAdapter","在此处设置数据");
        }
    }
}
