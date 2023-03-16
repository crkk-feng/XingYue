package com.example.xingyue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.views.TextViewBorder;

import static com.ximalaya.ting.android.opensdk.util.Utils.getContext;

public class HorizontalTextAdapter extends RecyclerView.Adapter<HorizontalTextAdapter.InnnerHolder> {

    private View rootView;
    private String[] lengthArray={"1","2","3","4","5","6","7","8","9","10","11"};
    private int checkedIndex=0;
    private onVideoTextItemClickListener onVideoTextItemClickListener;

    public void setOnVideoTextItemClickListener(HorizontalTextAdapter.onVideoTextItemClickListener onVideoTextItemClickListener) {
        this.onVideoTextItemClickListener = onVideoTextItemClickListener;
    }

    @NonNull
    @Override
    public HorizontalTextAdapter.InnnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(getContext()).inflate(R.layout.item_horizontal_text_layout,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnnerHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.setData(lengthArray[position]);
        if (position==checkedIndex) {
            holder.tvItem.setTextColor(getContext().getResources().getColor(R.color.episode_checked_color));
            holder.tvItem.setBorderColor(getContext().getResources().getColor(R.color.episode_checked_color));
        }else {
            holder.tvItem.setTextColor(getContext().getResources().getColor(R.color.episode_unchecked_color));
            holder.tvItem.setBorderColor(getContext().getResources().getColor(R.color.episode_unchecked_color));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setCheckText(position);
                if (onVideoTextItemClickListener != null) {
                    onVideoTextItemClickListener.itemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lengthArray != null) {
            return lengthArray.length;
        }
        return 0;
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {

        private TextViewBorder tvItem;

        public InnnerHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setData(String text){
            tvItem = itemView.findViewById(R.id.tv_item);
            tvItem.setText(text);
        }

        public void setCheckText(int nowCheckedIndex) {
            if (nowCheckedIndex!=checkedIndex) {
                checkedIndex=nowCheckedIndex;
                notifyDataSetChanged();
            }
        }
    }

    //点击事件外部接口
    public interface onVideoTextItemClickListener{
        void itemClick(int position);
    }
}
