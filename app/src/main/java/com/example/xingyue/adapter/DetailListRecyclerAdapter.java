package com.example.xingyue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailListRecyclerAdapter extends RecyclerView.Adapter<DetailListRecyclerAdapter.InnerHolder>{

    private List<Track> mtrackList=new ArrayList<>();
    //格式化时间
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd");
    private SimpleDateFormat durationDateFormat=new SimpleDateFormat("mm:ss");
    private onItemClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_view,
                parent,
                false);
        return new InnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //找到控件
        View itemView=holder.itemView;
        //顺序ID
        TextView textView_Id=itemView.findViewById(R.id.order_text);
        //标题
        TextView titleTv=itemView.findViewById(R.id.detail_item_title);
        //播放次数
        TextView playCountTv=itemView.findViewById(R.id.detail_item_play_count);
        //时长
        TextView durationTv=itemView.findViewById(R.id.detail_item_duration);
        //更新日期
        TextView updateDateTv=itemView.findViewById(R.id.detail_item_update_time);

        //设置数据
        Track track = mtrackList.get(position);
        textView_Id.setText((position+1)+"");
        titleTv.setText(track.getTrackTitle());
        playCountTv.setText(track.getPlayCount()+"");
        //因为返回单位是秒，所以需要转换为毫秒，从而进行格式转换
        int durationMil= track.getDuration()*1000;
        String durationFormat=durationDateFormat.format(durationMil);
        durationTv.setText(durationFormat);
        String updateFormat = simpleDateFormat.format(track.getUpdatedAt());
        updateDateTv.setText(updateFormat);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(mtrackList,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mtrackList.size();
    }

    public void setData(List<Track> trackList){
        //清除原始数据
        mtrackList.clear();
        //添加数据
        mtrackList.addAll(trackList);
        //更新UI
        notifyDataSetChanged();
    }
    public class InnerHolder extends RecyclerView.ViewHolder {

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.mItemClickListener=onItemClickListener;
    }
    public interface onItemClickListener{
        void onItemClick(List<Track> mtrackList, int position);
    }
}
