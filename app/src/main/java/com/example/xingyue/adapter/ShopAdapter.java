package com.example.xingyue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.data.ShopDataBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.InnnerHolder> {

    private List<ShopDataBean> mShopDataList=new ArrayList<>();
    private OnShopItemClickListener onShopItemClickListener;
    @NonNull
    @Override
    public InnnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_layout,parent,false);
        return new InnnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mShopDataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShopItemClickListener != null) {
                    onShopItemClickListener.ItemClick(position,mShopDataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mShopDataList != null) {
            return mShopDataList.size();
        }else {
            return 0;
        }
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {

        private Button button;

        public InnnerHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setData(ShopDataBean shopDataBean){
            //找到相关的控件，从而设置数据
            //物品图片
            ImageView ivShopItem=itemView.findViewById(R.id.shopItemImage);
            //物品名字
            TextView tvShopTitle=itemView.findViewById(R.id.shopItemText);
            //物品价格
            TextView tvShopPrice=itemView.findViewById(R.id.shopItemPrice);
            //场馆按钮
            button = itemView.findViewById(R.id.shop_item_buy_button);

            //设置商品图片
            ivShopItem.setImageResource(shopDataBean.getImageId());
            //设置商品名字
            tvShopTitle.setText(shopDataBean.getGoodsName());
            //设置场馆名字
            tvShopPrice.setText(shopDataBean.getPrice());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public void setOnShopItemClickListener(OnShopItemClickListener shopItemClickListener){
        this.onShopItemClickListener=shopItemClickListener;
    }
    public interface OnShopItemClickListener {
        void ItemClick(int position,ShopDataBean shopDataBean);
    }

    public void setmShopDataList(List<ShopDataBean> mShopDataList) {
        if (mShopDataList != null) {
//            mShopDataList.clear();
            this.mShopDataList = mShopDataList;
        }
        notifyDataSetChanged();
    }

}
