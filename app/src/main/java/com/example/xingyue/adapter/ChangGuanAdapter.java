package com.example.xingyue.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xingyue.R;
import com.example.xingyue.data.ChangGuanDataBean;
import com.example.xingyue.data.LocationDataBean;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChangGuanAdapter extends RecyclerView.Adapter<ChangGuanAdapter.InnnerHolder> {


    private List<ChangGuanDataBean> mChangGuanList=new ArrayList<>();
    private List<LocationDataBean> mChangGuanLocation=new ArrayList<>();

    private chooseWayToGoToChangGuan mChooseWayToGoToChangGuan;

    public chooseWayToGoToChangGuan getmChooseWayToGoToChangGuan() {
        return mChooseWayToGoToChangGuan;
    }

    public void setmChooseWayToGoToChangGuan(chooseWayToGoToChangGuan mChooseWayToGoToChangGuan) {
        this.mChooseWayToGoToChangGuan = mChooseWayToGoToChangGuan;
    }

    public void setmChangGuanLocation(List<LocationDataBean> mChangGuanLocation) {
        this.mChangGuanLocation = mChangGuanLocation;
    }

    @Override
    public InnnerHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_changguan,parent,false);
        return new InnnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  InnnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mChangGuanList.get(position));
        //设置点击事件
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChooseWayToGoToChangGuan != null) {
                    mChooseWayToGoToChangGuan.showGoToChooseWay(position);
                }
//                goGaodeMap(v.getContext(),mChangGuanLocation.get(position).getLatitude(),
//                        mChangGuanLocation.get(position).getLongitude(),
//                        mChangGuanLocation.get(position).getLocationName());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mChangGuanList != null) {
            return mChangGuanList.size();
        }else {
            return 0;
        }
    }

    public void setData(List<ChangGuanDataBean> mChangGuanList){
        if (this.mChangGuanList != null) {
            this.mChangGuanList.clear();
            this.mChangGuanList.addAll(mChangGuanList);
        }
        //更新UI数据
        notifyDataSetChanged();
    }

    public class InnnerHolder extends RecyclerView.ViewHolder {

        private Button button;

        public InnnerHolder(@NonNull  View itemView) {
            super(itemView);
        }
        public void setData(ChangGuanDataBean mData){
            //找到相关的控件，从而设置数据
            //场馆图片
            ImageView ivChangGuan=itemView.findViewById(R.id.iv_ChangGuan);
            //场馆名字
            TextView tvChangGuan=itemView.findViewById(R.id.tv_ChangGuanName);
            //场馆价格
            TextView tvChangGuanPrice=itemView.findViewById(R.id.tv_ChangGuanPrice);
            //场馆地址
            TextView tvChangGuanAddress=itemView.findViewById(R.id.tv_ChangGuanAddress);
            //场馆联系电话
            TextView tvChangGuanPhone=itemView.findViewById(R.id.tv_chanGuanPhone);
            //场馆开放时间
            TextView tvChangGuanTime=itemView.findViewById(R.id.tv_chanGuantime);
            //场馆按钮
            button = itemView.findViewById(R.id.button_cg);

            //设置场馆图片
            ivChangGuan.setImageResource(mData.getImageId());
            //设置场馆名字
            tvChangGuan.setText(mData.getChangGuanName());
            //设置场馆价格
            tvChangGuanPrice.setText("参考价"+mData.getPrice());
            //设置场馆地址
            tvChangGuanAddress.setText(mData.getAddress());
            //设置场馆联系电话
            tvChangGuanPhone.setText(mData.getPhoneNumber());
            //设置场馆的开放时间
            tvChangGuanTime.setText(mData.getTime());
        }
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

    public interface chooseWayToGoToChangGuan{
        void showGoToChooseWay(int position);
    }
}
