package com.example.xingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopItemDetailActivity extends AppCompatActivity {

    private Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop_item_detail);
        initData();
        initView();
    }

    private void initData() {
        mBundle=this.getIntent().getExtras();
    }

    private void initView() {
        ImageView iv_item_image = findViewById(R.id.iv_image);
        TextView tv_item_introducation=findViewById(R.id.tv_introduce);
        TextView tv_item_price=findViewById(R.id.tv_price);

        iv_item_image.setImageResource((Integer) mBundle.get("imageId"));
        tv_item_introducation.setText((String) mBundle.get("goodName"));
        tv_item_price.setText((String)mBundle.get("goodprice"));
    }
}