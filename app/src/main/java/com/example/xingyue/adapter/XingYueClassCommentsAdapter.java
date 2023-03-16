package com.example.xingyue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.data.CommentDataBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class XingYueClassCommentsAdapter extends RecyclerView.Adapter<XingYueClassCommentsAdapter.InnerHolder>{
    private List<CommentDataBean> commentDataList=new ArrayList<>();

    public void setCommentDataList(List<CommentDataBean> commentDataList) {
        this.commentDataList = commentDataList;
    }

    @NonNull
    @Override
    public XingYueClassCommentsAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建每一个Item,找到View
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_comment_layout,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull XingYueClassCommentsAdapter.InnerHolder holder, int position) {
        //封装数据
        holder.itemView.setTag(position);

        holder.setData(commentDataList.get(position));
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                int position= (int) v.getTag();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (commentDataList != null) {
            return commentDataList.size();
        }else {
            return 0;
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(CommentDataBean commentDataBean) {
            //头像
            ImageView headImage=itemView.findViewById(R.id.rv_user_headImage);
            //用户名称
            TextView tvUserName=itemView.findViewById(R.id.tv_user_name);
            //用户事件
            TextView tvTime=itemView.findViewById(R.id.tv_time);
            //点赞数量
            TextView tvNumOfThumbsUp=itemView.findViewById(R.id.tv_num_of_thumbs_up);
            //评论内容
            TextView tvContent=itemView.findViewById(R.id.tv_comment_content);


            //开始填入数据
            Picasso.with(headImage.getContext()).load(commentDataBean.getHeadSculptureUri()).into(headImage);//用户头像
            tvUserName.setText(commentDataBean.getUserName());
            tvTime.setText(commentDataBean.getDataTime());
            tvNumOfThumbsUp.setText(commentDataBean.getSumOfThumbsUp()+"");
            tvContent.setText(commentDataBean.getContentOfComment());
        }
    }
}
