package com.example.xingyue.adapter;


import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.xingyue.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author WeiZhen
 * @since 2020-02-28
 */
public class RecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RecyclerAdapter() {
        super(R.layout.user_item_view_layout);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable String s) {
          baseViewHolder.setText(R.id.tv_item,s);
//          baseViewHolder.getView(0).setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  Toast.makeText(getContext(),"点击",Toast.LENGTH_SHORT).show();
//              }
//          });
    }
}
