package com.tbsurvey.trlbhxf.ui.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tbsurvey.trlbhxf.ui.base.mvp.BasePresenter;
import com.tbsurvey.trlbhxf.ui.base.mvp.IView;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :基类 BaseMvpActivity
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements IView {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
    }


    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    protected abstract T createPresenter();
    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    protected abstract void initData();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
