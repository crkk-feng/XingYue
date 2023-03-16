package com.example.module_microclassroom.multipleitembean;



import com.example.module_microclassroom.bean.AudioBean;
import com.example.module_microclassroom.bean.MultipleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 艺术家的数据封装，内部为艺术家数据集合列表
 */
public class ArtistListBean extends MultipleBean {
    public ArtistListBean(int itemType) {
        super(itemType);
    }
    private List<AudioBean> artistBeanList=new ArrayList<>();

    public List<AudioBean> getArtistBeanList() {
        return artistBeanList;
    }

    public void setArtistBeanList(List<AudioBean> artistBeanList) {
        this.artistBeanList = artistBeanList;
    }
}
