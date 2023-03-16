package com.example.xingyue.viewholder;

import android.view.View;
import android.widget.TextView;

import com.example.xingyue.R;
import com.ocnyang.cartlayout.viewholder.CartViewHolder;

public class GroupViewHolder extends CartViewHolder {
    public TextView textView;

    public GroupViewHolder(View itemView, int chekbox_id) {
        super(itemView, chekbox_id);
        textView = itemView.findViewById(R.id.tv);
    }
}
