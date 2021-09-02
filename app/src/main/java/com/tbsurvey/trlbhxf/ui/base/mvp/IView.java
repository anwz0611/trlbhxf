package com.tbsurvey.trlbhxf.ui.base.mvp;
/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface IView {


    //显示loading
    void showLoading();
    //显示loading
    void showLoading(String msg);
    //隐藏loading
    void hideLoading();

    //显示吐司
    void showError(String msg);
}
