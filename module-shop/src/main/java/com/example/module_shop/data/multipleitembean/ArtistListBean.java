package com.example.module_shop.data.multipleitembean;



import com.example.module_shop.data.ArtistBean;
import com.example.module_shop.data.MultipleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 艺术家的数据封装，内部为艺术家数据集合列表
 */
public class ArtistListBean extends MultipleBean {
    public ArtistListBean(int itemType) {
        super(itemType);
    }
    private List<ArtistBean> artistBeanList=new ArrayList<>();

    public List<ArtistBean> getArtistBeanList() {
        return artistBeanList;
    }

    public void setArtistBeanList(List<ArtistBean> artistBeanList) {
        this.artistBeanList = artistBeanList;
    }
}
