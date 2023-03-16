package com.example.xingyue.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xingyue.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.util.Utils.getContext;

public class HorizontalRadioAdapter extends RecyclerView.Adapter<HorizontalRadioAdapter.InnnerHolder>{

    private static final String TAG = "HorizontalRadioAdapter";
    private View rootView;
    private List<Album> mRadioList=new ArrayList<>();
    private onAlbumItemClickListener mAlbumItemClickListener;
    private onAlbumItemLongClickListener mLongClickListener=null;


    public void setmRadioList(List<Album> RadioList) {
        if (mRadioList != null) {
            mRadioList.clear();
            mRadioList.addAll(RadioList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HorizontalRadioAdapter.InnnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(getContext()).inflate(R.layout.item_radio_horizontal_video,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mRadioList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlbumItemClickListener != null) {
                    mAlbumItemClickListener.onItemClick((Integer) v.getTag(),mRadioList.get(position));
                }
                Log.d(TAG,"Item clicked is -->"+v.getTag());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position= (int) v.getTag();
                mLongClickListener.onItemLongClick(position,mRadioList.get(position));
                //这里返回True的时候表示消费掉该事件

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRadioList != null) {
            return mRadioList.size();
        }
        return 0;
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {
        public InnnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到图片部分
            ImageView imageView=itemView.findViewById(R.id.video_image_item);
            TextView tvView=itemView.findViewById(R.id.tv_video_item);

            //设置数据
            tvView.setText(album.getAlbumTitle());

            Glide.with(getContext()).load(album.getCoverUrlLarge()).into(imageView);

            Log.d(TAG,"在此处设置数据");
        }
    }

    public interface onAlbumItemClickListener{
        void onItemClick(int position, Album album);
    }

    public interface onAlbumItemLongClickListener{
        void onItemLongClick(int position,Album album);
    }

    public void setOnItemClickListener(onAlbumItemClickListener onAlbumItemClickListener){
        this.mAlbumItemClickListener=onAlbumItemClickListener;
    }

    public void setOnItemLongClickListener(onAlbumItemLongClickListener monAlbumItemLongClickListener){
        this.mLongClickListener=monAlbumItemLongClickListener;
    }
}
