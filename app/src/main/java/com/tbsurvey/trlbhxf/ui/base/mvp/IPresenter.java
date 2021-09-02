package com.tbsurvey.trlbhxf.ui.base.mvp;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface IPresenter<V extends IView> {

    /**
     * 绑定 View
     */
    void attachView(V mView);

    /**
     * 解除 View
     */
    void detachView();
}
