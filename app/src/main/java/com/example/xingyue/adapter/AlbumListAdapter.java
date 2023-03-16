package com.example.xingyue.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.xingyue.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.InnerHolder> {

    private static final String TAG = "RecommendListAdapter";
    private List<Album> mData=new ArrayList<>();
    private onAlbumItemClickListener mAlbumItemClickListener;
    private onAlbumItemLongClickListener mLongClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建每一个Item,找到View
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //封装数据
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlbumItemClickListener != null) {
                    mAlbumItemClickListener.onItemClick((Integer) v.getTag(),mData.get(position));
                }
                Log.d(TAG,"Item clicked is -->"+v.getTag());
            }
        });
        holder.setData(mData.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position= (int) v.getTag();
                mLongClickListener.onItemLongClick(position,mData.get(position));
                //这里返回True的时候表示消费掉该事件

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        if (mData != null) {
           return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (mData != null) {
             mData.clear();
             mData.addAll(albumList);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setData(Album album){
            //找到各个控件，设置数据
            //专辑方面
            ImageView albumCoverIv=itemView.findViewById(R.id.album_item);
            //专辑标题
            TextView tv_title=itemView.findViewById(R.id.album_title_tv);
            //专辑描述
            TextView tv_sub=itemView.findViewById(R.id.album_description_tv);
            //播放数量
            TextView tv_count=itemView.findViewById(R.id.album_play_count);
            //专辑内容数量
            TextView tv_content_size=itemView.findViewById(R.id.album_content_size);

            //开始填入数据
            tv_title.setText(album.getAlbumTitle());
            tv_sub.setText(album.getAlbumIntro());
            tv_count.setText(album.getPlayCount()+"");
            tv_content_size.setText(album.getIncludeTrackCount()+"");
            Picasso.with(tv_title.getContext()).load(album.getCoverUrlLarge()).into(albumCoverIv);
        }
    }

    public void setOnItemClickListener(onAlbumItemClickListener onAlbumItemClickListener){
        this.mAlbumItemClickListener=onAlbumItemClickListener;
    }

    public void setOnItemLongClickListener(onAlbumItemLongClickListener monAlbumItemLongClickListener){
        this.mLongClickListener=monAlbumItemLongClickListener;
    }
    public interface onAlbumItemClickListener{
        void onItemClick(int position, Album album);
    }

    public interface onAlbumItemLongClickListener{
        void onItemLongClick(int position,Album album);
    }

}
