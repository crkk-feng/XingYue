package com.example.xingyue.utils;



import com.example.xingyue.base.BaseFragment;
import com.example.xingyue.fragments.shopfragments.ClothingShopFragment;
import com.example.xingyue.fragments.shopfragments.IntegrationShopFragment;
import com.example.xingyue.fragments.shopfragments.StationeryShopFragment;
import com.example.xingyue.fragments.shopfragments.BookShopFragment;
import com.example.xingyue.fragments.shopfragments.FoodShopFragment;
import com.example.xingyue.fragments.shopfragments.ToyShopFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentsCreator {
    public final static int INDEX_BOOK=0;
    public final static int INDEX_FOOD=1;
    public final static int INDEX_STATIONERY=2;
    public final static int INDEX_TOY=3;
    public final static int INDEX_CLOTHING=4;
    public final static int INDEX_INTEGRATION=5;

    public final static int PAGE_COUNT=6;

    //存放页面的Fragment,起到一个缓存的作用
    private static Map<Integer, BaseFragment> mCache=new HashMap<>();

    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment=mCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index){
            case INDEX_BOOK:
                baseFragment=new BookShopFragment();
                break;
            case INDEX_FOOD:
                baseFragment=new FoodShopFragment();
                break;
            case INDEX_STATIONERY:
                baseFragment=new StationeryShopFragment();
                break;
            case INDEX_TOY:
                baseFragment=new ToyShopFragment();
                break;
            case INDEX_CLOTHING:
                baseFragment=new ClothingShopFragment();
                break;
            case INDEX_INTEGRATION:
                baseFragment=new IntegrationShopFragment();
                break;
            default:
                break;
        }
        mCache.put(index,baseFragment);
        return  baseFragment;
    }
}
