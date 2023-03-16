package com.example.module_microclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module_microclassroom.R;
import com.example.module_microclassroom.bean.NewsItem;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.InnnerHolder> {
    private Context mContext;
    private List<NewsItem> mNewsList=new ArrayList<>();
    private View rootView;
    private newsClickListener mNewsClickListener;
    private List<String> newsTitleStringList;

    public newsClickListener getmNewsClickListener() {
        return mNewsClickListener;
    }

    public void setmNewsClickListener(newsClickListener mNewsClickListener) {
        this.mNewsClickListener = mNewsClickListener;
    }

    public List<NewsItem> getmNewsList() {
        return mNewsList;
    }

    public void setmNewsList(List<NewsItem> mNewsList) {
        this.mNewsList = mNewsList;
    }

    public List<String> getNewsTitleStringList() {
        return newsTitleStringList;
    }

    public void setNewsTitleStringList(List<String> newsTitleStringList) {
        this.newsTitleStringList = newsTitleStringList;
    }

    @NonNull
    @Override
    public InnnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_view,parent,false);
        return new InnnerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mNewsList.get(position),position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewsClickListener != null) {
                    mNewsClickListener.onNewsClick((Integer) v.getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNewsList != null) {
            return mNewsList.size();
        }
        return 0;
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mDate;
        TextView mPublisher;
        ImageView mHead;
        public InnnerHolder(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            mContent=itemView.findViewById(R.id.tv_content);
            mDate=itemView.findViewById(R.id.tv_publish_date);
            mPublisher=itemView.findViewById(R.id.tv_publisher);
            mHead=itemView.findViewById(R.id.news_imageview);
        }

        public void setData(NewsItem newsItem,int pos) {

            //设置数据
            mTitle.setText(newsTitleStringList.get(pos));
            mContent.setText(newsItem.getTitle());
            mDate.setText(newsItem.getDate());
            mPublisher.setText(newsItem.getResource());
            if (newsItem.isHavePic()) {
                Glide.with(mHead.getContext()).load(newsItem.getImageUri()).into(mHead);
            }else{
                mHead.setVisibility(View.GONE);
            }
        }
    }

    public interface newsClickListener{
        void onNewsClick(int position);
    }
}
