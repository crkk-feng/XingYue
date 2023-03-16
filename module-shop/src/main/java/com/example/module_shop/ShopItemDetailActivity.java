package com.example.module_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib_base.data.UserLoginBean;
import com.example.module_shop.adapter.MultipleShopItemDetailAdapter;
import com.example.module_shop.data.BannerImageBean;
import com.example.module_shop.data.LargePicBean;
import com.example.module_shop.data.MultipleBean;
import com.example.module_shop.data.PriceAndNameBean;
import com.example.module_shop.data.PublishInformationBean;
import com.example.module_shop.data.SellerInformationBean;
import com.example.module_shop.data.ShopItemPostBean;
import com.example.module_shop.data.multipleitembean.BannerImageListBean;
import com.example.module_shop.views.ConfirmDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Route(path = "/shop/ShopItemDetailActivity")
public class ShopItemDetailActivity extends AppCompatActivity implements ConfirmDialog.OnDialogActionClickListener {
    private static final String TAG = "ShopItemDetailActivity";
    private RecyclerView rvMainBody;
    private Button button_back;
    private Button button_more;
    private Button btnBuyInstant;
    private FloatingActionButton mFloatButton;
    private LargePicBean largeDescriptionPicBean;
    private PriceAndNameBean priceAndNameBean;
    private SellerInformationBean sellerInformationBean;
    private PublishInformationBean collectionInfoBean;
    private ConfirmDialog confirmDialog;

    private List<BannerImageBean> bannerImageBeanList;//轮播图的数据列表
    private ArrayList<MultipleBean> MultiDataList;//封装了轮播图数据列表的适配器数据项


    private BannerImageListBean bannerImageListBean;//封装了轮播图数据列表的适配器数据项

    private MultipleShopItemDetailAdapter adapter;

    @Autowired(name="LargePicUri")
    String largePicUri;//展示图链接
    String shopItemName;//商品名字
    String shopItemPrice;//商品价格
    String shopDescriptionUri;//商品描述图片Uri
    String shopItemSellerName;//商铺名称
    String shopItemSellerUri;//商铺头像链接
    String shopItemSellerDescription;//商铺描述信息
    String publisher;//发行方姓名
    String publishDate;//发行日期
    String publishId;//发行编号
    int shopItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop_item_detail);
        initData();
        initView();
    }



    /**
     * 设置页面所需要用到的数据
     */
    private void initData() {
        //处理上一个页面传递过来的数据
        largePicUri=getIntent().getStringExtra("LargePicUri");
        shopItemName=getIntent().getStringExtra("itemName");
        shopItemPrice=getIntent().getStringExtra("itemPrice");
        shopDescriptionUri=getIntent().getStringExtra("itemDescriptionUri");
        shopItemSellerUri=getIntent().getStringExtra("itemSellerUri");
        shopItemSellerName=getIntent().getStringExtra("itemSellerName");
        shopItemSellerDescription=getIntent().getStringExtra("itemSellerDescription");
        publisher=getIntent().getStringExtra("publisher");
        publishId=getIntent().getStringExtra("publishId");
        publishDate=getIntent().getStringExtra("publishDate");
        shopItemId=getIntent().getIntExtra("shopItemId",1);

        //复用轮播图处理上端大图
        bannerImageBeanList = new ArrayList<>();
        if (largePicUri == null) {
            largePicUri=getIntent().getStringExtra("LargePicUri");
        }
        bannerImageBeanList.add(new BannerImageBean(Uri.parse(largePicUri)));//根据上一个页面传过来的Uri显示界面

        bannerImageListBean = new BannerImageListBean(MultipleShopItemDetailAdapter.TOPBANNER);
        bannerImageListBean.setBannerImageBeanList(bannerImageBeanList);

        //商品价格部分
        priceAndNameBean = new PriceAndNameBean(MultipleShopItemDetailAdapter.NAMEANDPRICE);
        priceAndNameBean.setItemName(shopItemName);
        priceAndNameBean.setItemPrice(shopItemPrice);

        //卖家信息部分
        sellerInformationBean = new SellerInformationBean(MultipleShopItemDetailAdapter.ITEMSELLERINTRODUCE);
        sellerInformationBean.setSellerUri(Uri.parse(shopItemSellerUri));
        sellerInformationBean.setSellerName(shopItemSellerName);
        sellerInformationBean.setSellerIntro(shopItemSellerDescription);

        //商品描述部分
        largeDescriptionPicBean = new LargePicBean(MultipleShopItemDetailAdapter.COLLECTIONDESC);
        largeDescriptionPicBean.setLargePicUri(Uri.parse(shopDescriptionUri));

        //商品发行信息
        collectionInfoBean = new PublishInformationBean(MultipleShopItemDetailAdapter.COLLECTIONINFO);
        collectionInfoBean.setBand(publisher);
        collectionInfoBean.setDate(publishDate);
        collectionInfoBean.setNumber(publishId);
    }

    private void initView() {
        confirmDialog=new ConfirmDialog(this);
        btnBuyInstant=findViewById(R.id.buy_item_instant);
        button_back = findViewById(R.id.btn_back);
        button_more = findViewById(R.id.btn_more);
        mFloatButton=findViewById(R.id.flb_buy_into_cart);

        if (button_back != null &&button_more!=null) {
            button_more.setAlpha((float) 0.10);
            button_back.setAlpha((float) 0.10);
        }
        initAll();
        loadDataIntoView();
        loadDivder();
        adapter = new MultipleShopItemDetailAdapter(MultiDataList);
        rvMainBody.setAdapter(adapter);

        mFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopItemDetailActivity.this,"商品加入购物车成功",Toast.LENGTH_SHORT).show();
            }
        });
        btnBuyInstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.show();

            }
        });
        confirmDialog.setOnDialogActionClickListener(this);
    }


    private void initAll(){
        rvMainBody = findViewById(R.id.rv_shop_main_body);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMainBody.setLayoutManager(linearLayoutManager);
    }

    private void loadDataIntoView() {
        MultiDataList = new ArrayList<>();
        MultiDataList.add(bannerImageListBean);
        MultiDataList.add(priceAndNameBean);
        MultiDataList.add(sellerInformationBean);
        MultiDataList.add(new MultipleBean(MultipleShopItemDetailAdapter.BUYERRECENTYL));//最近购买
        MultiDataList.add(largeDescriptionPicBean);
        MultiDataList.add(collectionInfoBean);
    }

    private void loadDivder() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//设置一个分割线
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_item_bg));
        rvMainBody.addItemDecoration(dividerItemDecoration);
    }

    private void buyShopItemInstant(){
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();



        /**
         * 创建请求
         */
        MediaType mediaType= MediaType.parse("multipart/form-data");


        RequestBody requestBody= new FormBody.Builder()
                .add("shopItemId", String.valueOf(shopItemId))
                .add("shopItemName",shopItemName)
                .add("shopItemPrice",shopItemPrice)
                .add("shopItemUri",largePicUri)
                .add("shopItemSellerName",shopItemSellerName)
                .add("shopItemSellerUri",shopItemSellerUri)
                .build();
        Request request= new Request.Builder().
                post(requestBody).
                url("http://120.24.169.142:8888/order/addOrder").
                build();

        /**
         * 浏览器创建数据
         */
        Call task= okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure->"+e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code=response.code();
                Log.d(TAG,"code->"+code);
                ResponseBody body= response.body();
                Log.d(TAG,"body->"+body.string());
            }
        });
    }

    @Override
    public void onCancelSubClick() {

    }

    @Override
    public void onCheckUpClick() {
        buyShopItemInstant();
        confirmDialog.dismiss();
        finish();
        Toast.makeText(ShopItemDetailActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
    }
}