package com.example.xingyue;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.xingyue.dao.CommentDao;
import com.example.xingyue.dao.DataBaseHelper;
import com.example.xingyue.dao.ShopItemDetailDao;
import com.example.xingyue.data.CommentDataBean;
import com.example.xingyue.fragments.ChangGuanFragment;
import com.example.xingyue.fragments.LunTanFragment;
import com.example.xingyue.fragments.MyFragment;
import com.example.xingyue.fragments.ShopFragment;
import com.example.xingyue.fragments.WeiKeTangFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDeviceInfoProvider;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.util.SharedPreferencesUtil;

import org.xutils.common.util.LogUtil;

import java.nio.channels.MembershipKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FrameLayout mFrameLayout;
    private BottomNavigationView bottomNav;
    private int currentPosition;
    private Fragment mcgFragment=null,mShopFragment=null,mPersonFragment=null,mktFragment=null;
    String oaid;
    private static final String KEY_LAST_OAID = "last_oaid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
        //插入几条评论
//        initDataBaseData();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        ShopItemDetailDao shopItemDetailDao = new ShopItemDetailDao(dataBaseHelper);
        shopItemDetailDao.insertShopOriginalData();
        initDataBaseData();
    }

    private void initDataBaseData() {
        CommentDataBean commentDataBeanTest = new CommentDataBean("想再见一面", Uri.parse("android.resource://"+getPackageName()+"/"+R.mipmap.touxiang),
                23,
                "2022年12月30号",
                "这场讲座真是太赞了，令我印象深刻,还记得第一次见到雷老师的时候就是在现场");
        CommentDataBean commentDataBeanTest1 = new CommentDataBean("天下谁人不识君", Uri.parse("android.resource://"+getPackageName()+"/"+R.mipmap.touxiang1),
                88,
                "2023年1月1号",
                "雷总工程师给我介绍了我国航天的发展历程，详细讲述了祖国航天事业的辉煌历史，还拓展了很多与航天有关的文化知识，" +
                        "涉及到数学、物理、化学、地理、英语等很多文化课。李叔叔讲的滔滔不绝，我听的津津有味。体会到了祖国的强盛和伟大。");
        CommentDataBean commentDataBeanTest2 = new CommentDataBean("风雨兼程", Uri.parse("android.resource://"+getPackageName()+"/"+R.mipmap.touxiang2),
                79,
                "2023年1月2号",
                "雷总工程师还讲解了卫星发射的一些原理，不同卫星发射所起的作用和意义，他讲述了宇航员所付出的艰辛，在茫茫宇宙中，宇航员要克服失重、缺氧等困难才能生存下来。感谢李总工程师的讲说，感谢为航天事业奋斗的人。");
        //Dao层
        CommentDao commentDao = new CommentDao(getBaseContext());
        commentDao.insert(commentDataBeanTest);
        commentDao.insert(commentDataBeanTest1);
        commentDao.insert(commentDataBeanTest2);
    }

    private void initView() {
        mFrameLayout = findViewById(R.id.fragment_container);
        bottomNav = findViewById(R.id.mainBottomNavigation);
//        bottomNav.setItemIconTintList(null);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNav.setSelectedItemId(R.id.changGuanFragment);
        switchFragment(0);
        currentPosition = 0;
    }

    /**
     * 点击底部的按钮
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.changGuanFragment:
                    if (currentPosition != 0) {
                        switchFragment(0);
                        currentPosition = 0;
                    }
                    break;
                case R.id.weiKeTangFragment:
                    if (currentPosition != 1) {
                        switchFragment(1);
                        currentPosition = 1;
                    }
                    break;
                case R.id.shopFragment:
                    if (currentPosition != 2) {
                        switchFragment(2);
                        currentPosition = 2;
                    }
                    break;
                case R.id.homeFragment:
                    if (currentPosition != 3) {
                        switchFragment(3);
                        currentPosition = 3;
                    }
            }
            return true;
        }
    };

    /**
     * 切换fragment
     *
     * @param position fragment的位置
     */
    private void switchFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (position == 0) {
            if (mcgFragment == null) {
//                homeFragment = new HomeFragment();
                mcgFragment = new ChangGuanFragment();
                transaction.add(R.id.fragment_container, mcgFragment);
            }
            //隐藏所有fragment
            hideFragment(transaction);
            //显示需要显示的fragment
            transaction.show(mcgFragment);

        } else if (position == 2) {
            if (mShopFragment == null) {
                mShopFragment = new ShopFragment();
                transaction.add(R.id.fragment_container, mShopFragment);
            }
            //隐藏所有fragment
            hideFragment(transaction);
            //显示需要显示的fragment
            transaction.show(mShopFragment);
        } else if (position == 1) {
            if (mktFragment == null) {
                mktFragment = new WeiKeTangFragment();
                transaction.add(R.id.fragment_container, mktFragment);
            }
            //隐藏所有fragment
            hideFragment(transaction);
            //显示需要显示的fragment
            transaction.show(mktFragment);
        } else if (position == 3) {
            if (mPersonFragment == null) {
                mPersonFragment = new MyFragment();
                transaction.add(R.id.fragment_container, mPersonFragment);
            }
            //隐藏所有fragment
            hideFragment(transaction);
            //显示需要显示的fragment
            transaction.show(mPersonFragment);
        }
        transaction.commit();
    }

    public IDeviceInfoProvider getDeviceInfoProvider(Context context) {
        return new DeviceInfoProviderDefault(context) {
            @Override
            public String oaid() {
                // 合作方要尽量优先回传用户真实的oaid，使用oaid可以关联并打通喜马拉雅主app中记录的用户画像数据，对后续个性化推荐接口推荐给用户内容的准确性会有极大的提升！
                return oaid;
            }
        };
    }

    /**
     * 隐藏所有的fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mcgFragment != null) {
            transaction.hide(mcgFragment);
        }
        if (mShopFragment != null) {
            transaction.hide(mShopFragment);
        }
        if (mktFragment != null) {
            transaction.hide(mktFragment);
        }
        if (mPersonFragment != null) {
            transaction.hide(mPersonFragment);
        }
    }
}