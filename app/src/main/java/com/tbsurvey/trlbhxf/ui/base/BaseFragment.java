package com.tbsurvey.trlbhxf.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tbsurvey.trlbhxf.ui.rxbus.Bus;
import com.tbsurvey.trlbhxf.ui.rxbus.BusProvider;
import com.tbsurvey.trlbhxf.wight.LoadProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :基类 BaseMvpFragment
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder unBinder;
    protected Context context;
    private LoadProgressDialog loadingDialog = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        context=getActivity();
        Intent intent = getActivity().getIntent();
        if (intent != null)
            getIntent(intent);
        unBinder = ButterKnife.bind(this,view);
        if (useEventBus()) {
//            EventBus.getDefault().register(this);//注册eventBus
             BusProvider.getInstance().register(this);
        }
        initView();
//        initData();
        initListener();
        return view;
    }

    /**
     * 是否使用eventBus
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (useEventBus()) {
//            if (EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().unregister(this);//注销eventBus
                BusProvider.getInstance().unregister(this);
        }

    }







    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 创建LoadingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadProgressDialog(getActivity(),"加载数据中...");;
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }




    /**
     * 获取布局 Id
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutId();
    /** 获取 Intent 数据 **/
    protected abstract void getIntent(Intent intent);

    /** 初始化View的代码写在这个方法中 */
    protected abstract void initView();

    /** 初始化监听器的代码写在这个方法中 */
    protected abstract void initListener();





}
