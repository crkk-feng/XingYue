package com.example.xingyue.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.WebviewActivity;
import com.example.xingyue.adapter.ChangGuanAdapter;
import com.example.xingyue.data.ChangGuanDataBean;
import com.example.xingyue.data.LocationDataBean;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChangGuanFragment extends Fragment implements ChangGuanAdapter.chooseWayToGoToChangGuan {

    private static final String TAG = "ChangGuanFragment";
    private View rootView;
    private RecyclerView cgContainerRv;
    private ChangGuanAdapter changGuanAdapter;
    private List<ChangGuanDataBean> mNewChangGuanDataBeanList =new ArrayList<ChangGuanDataBean>();
    private List<LocationDataBean> mLocationDataBeanList=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_changguan_view,container,false);
        initData();
        initView(rootView);
        return rootView;
    }

    private void initData() {
        ChangGuanDataBean changGuanDataBean = new ChangGuanDataBean(R.mipmap.zhejiangsciencemuseum, "浙江科技馆",
                "4.0",
                "免费开放",
                "拱墅区西湖文化广场A座1号",
                "0571-85090500",
                "09:00-17:00"
        );
        ChangGuanDataBean changGuanDataBean1 = new ChangGuanDataBean(R.mipmap.yuhangkeji, "余杭科技馆",
                "5.0",
                "免费开放",
                "下城区中山北路581号西湖文化广场",
                "0571-86160588",
                "08:30-16:30"
        );
        ChangGuanDataBean changGuanDataBean2 = new ChangGuanDataBean(R.mipmap.yuntongzhineng, "云童智能科技馆",
                "3.5",
                "免费开放",
                "拱墅区叶青兜路59号",
                "18357188415",
                "09:30-20:30"
        );
        ChangGuanDataBean changGuanDataBean3 = new ChangGuanDataBean(R.mipmap.huituokeji, "惠拓科技航空体验馆",
                "3.5",
                "￥80/人",
                "萧山区空港大道杭州萧山国际机场3F层",
                "0571-85090500",
                "09:00-16:30"
        );
        mNewChangGuanDataBeanList.add(changGuanDataBean);
        mNewChangGuanDataBeanList.add(changGuanDataBean1);
        mNewChangGuanDataBeanList.add(changGuanDataBean2);
        mNewChangGuanDataBeanList.add(changGuanDataBean3);

        LocationDataBean mLocationData=new LocationDataBean(30.282042,120.171172,
                "浙江省科技馆");
        LocationDataBean mLocationData1=new LocationDataBean(30.430459,120.301904,
                "余杭科技馆");
        LocationDataBean mLocationData2=new LocationDataBean(30.243229,120.44326,
                "云童智能科技馆");
        LocationDataBean mLocationData3=new LocationDataBean(30.28899,120.16216,
                "惠拓科技航空体验馆");

        mLocationDataBeanList.add(mLocationData);
        mLocationDataBeanList.add(mLocationData1);
        mLocationDataBeanList.add(mLocationData2);
        mLocationDataBeanList.add(mLocationData3);

    }

    private void initView(View rootView) {
        cgContainerRv = rootView.findViewById(R.id.cgContainer);

        //给RecyclerView设置适配器及布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        cgContainerRv.setLayoutManager(linearLayoutManager);
        //设置适配器
        changGuanAdapter = new ChangGuanAdapter();
        if (changGuanAdapter != null) {
            changGuanAdapter.setData(mNewChangGuanDataBeanList);
            changGuanAdapter.setmChangGuanLocation(mLocationDataBeanList);
        }
        cgContainerRv.setAdapter(changGuanAdapter);
        //设置两边边距进行美观处理
        cgContainerRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(),2);
                outRect.bottom = UIUtil.dip2px(view.getContext(),2);
                outRect.left = UIUtil.dip2px(view.getContext(),2);
                outRect.right = UIUtil.dip2px(view.getContext(),2);
            }
        });
        changGuanAdapter.setmChooseWayToGoToChangGuan(this);
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 让用户选择浏览方式
     */
    @Override
    public void showGoToChooseWay(int position) {
        trackChangguan(position+1);
        showDialog(position);
    }

    public void showDialog(int position){
        AlertDialog.Builder add_Dialog= new AlertDialog.Builder(getContext());
        /*
        设置标题和内容
         */
        add_Dialog.setTitle("请选择前往场馆的方式");
        String[] dialog_Item={"在线云游","一键导航"};

        /*
        设置修饰的相机小Icon
         */
        add_Dialog.setIcon(R.drawable.vr_pic);

        /*
        设置提示框的点击事件
         */
        add_Dialog.setItems(dialog_Item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case    0:
                        trackVisitWay(1);
                        Intent intent = new Intent(getContext(), WebviewActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //一键导航
                        trackVisitWay(2);
                        goGaodeMap(getContext(),
                                mLocationDataBeanList.get(position).getLatitude(),
                                mLocationDataBeanList.get(position).getLongitude(),
                                mLocationDataBeanList.get(position).getLocationName());
                        break;
                }
            }
        });
        add_Dialog.create().show();
    }


    /**
     * 判断手机中是否安装指定包名的软件
     * @param context
     * @param pkgname 包名
     */
    public static boolean isInstallApk(Context context, String pkgname) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(pkgname)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /**
     * 跳转到高德地图
     * @param context
     * @param latitude 纬度
     * @param longtitude 经度
     * @param address 终点
     * */
    private void goGaodeMap(Context context,double latitude, double longtitude, String address) {
        if (isInstallApk(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=&poiname=" + address + "&lat=" + latitude
                        + "&lon=" + longtitude + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                Log.e("goError", e.getMessage());
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_SHORT).show();
        }
    }

    private void trackVisitWay(int id){
        String requestUri="http://120.24.169.142:8888/changguanvisitwaycalculate/query?id="+id+"&flag=true";
        Log.d(TAG,requestUri);
        /*
        要有客户端，类似于我们要有一个浏览器
         */
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();
        /*
        创建链接，请求内容
         */
        Request request= new Request.Builder().get().url(requestUri).build();

        /*
        用client创建请求任务
         */
        Call task=okHttpClient.newCall(request);

        /*
        异步请求
         */
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG","onFailure->"+e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code=response.code();
                Log.d("TAG","code->"+code);
                ResponseBody body= response.body();
                Log.d(TAG,body.string());
            }
        });
    }

    private void trackChangguan(int id){
        String requestUri="http://120.24.169.142:8888/changguaninterestcalculate/query?id="+id+"&flag=true";
        Log.d(TAG,requestUri);
        /*
        要有客户端，类似于我们要有一个浏览器
         */
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();
        /*
        创建链接，请求内容
         */
        Request request= new Request.Builder().get().url(requestUri).build();

        /*
        用client创建请求任务
         */
        Call task=okHttpClient.newCall(request);

        /*
        异步请求
         */
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG","onFailure->"+e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code=response.code();
                Log.d("TAG","code->"+code);
                ResponseBody body= response.body();
                Log.d(TAG,body.string());
            }
        });
    }

}
