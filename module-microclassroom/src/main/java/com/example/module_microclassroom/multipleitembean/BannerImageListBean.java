package com.example.module_microclassroom.multipleitembean;



import com.example.module_microclassroom.bean.BannerImageBean;
import com.example.module_microclassroom.bean.MultipleBean;

import java.util.ArrayList;
import java.util.List;

public class BannerImageListBean extends MultipleBean {

    private List<BannerImageBean> bannerImageBeanList=new ArrayList<>();
    public BannerImageListBean(int itemType) {
        super(itemType);
    }

    public List<BannerImageBean> getBannerImageBeanList() {
        return bannerImageBeanList;
    }

    public void setBannerImageBeanList(List<BannerImageBean> bannerImageBeanList) {
        this.bannerImageBeanList = bannerImageBeanList;
    }
}
