package com.example.xingyue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.data.HorizontalDataBean;

import java.util.ArrayList;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.util.Utils.getContext;

public class HorizontalVideoAdapter extends RecyclerView.Adapter<HorizontalVideoAdapter.InnnerHolder> {

    private View rootView;
    private List<HorizontalDataBean> mVideoData=new ArrayList<>();
    private onVideoItemClickListener monVideoItemClickListener;

    public void setmVideoData(List<HorizontalDataBean> mVideoData) {
        this.mVideoData = mVideoData;
    }



    @NonNull
    @Override
    public InnnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.item_video_horizontal,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mVideoData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= (int) v.getTag();
                if (monVideoItemClickListener != null) {
                    monVideoItemClickListener.itemClick(position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        if (mVideoData != null) {
            return mVideoData.size();
        }else {
            return 0;
        }
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {

        private TextView mText;

        public InnnerHolder(@NonNull  View itemView) {
            super(itemView);
        }
        /*
        找到相关的控件，设置数据
         */
        public void setData(HorizontalDataBean mData){
            //找到图片部分
            ImageView imageView=itemView.findViewById(R.id.video_image_item);
            TextView tvView=itemView.findViewById(R.id.tv_video_item);

            //设置数据
            imageView.setImageResource(mData.getImageId());
            tvView.setText(mData.getVideoText());
        }
    }

    public interface onVideoItemClickListener{
        void itemClick(int position);
    }

    public void setMonVideoItemClickListener(onVideoItemClickListener monVideoItemClickListener) {
        this.monVideoItemClickListener = monVideoItemClickListener;
    }
}
