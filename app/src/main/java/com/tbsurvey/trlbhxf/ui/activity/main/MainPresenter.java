package com.tbsurvey.trlbhxf.ui.activity.main;


import com.tbsurvey.trlbhxf.ui.base.mvp.BasePresenter;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> {


    @Override
    protected MainContract.Model createModel() {
        return  new MainModel();
    }
}
