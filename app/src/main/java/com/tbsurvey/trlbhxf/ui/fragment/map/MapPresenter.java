package com.tbsurvey.trlbhxf.ui.fragment.map;


import com.tbsurvey.trlbhxf.ui.base.mvp.BasePresenter;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public class MapPresenter extends BasePresenter<MapContract.Model, MapContract.View> {


    @Override
    protected MapContract.Model createModel() {
        return new MapModel();
    }

}
