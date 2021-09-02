package com.tbsurvey.trlbhxf.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.tbsurvey.trlbhxf.ui.rxbus.Bus;
import com.tbsurvey.trlbhxf.ui.rxbus.BusProvider;
import com.tbsurvey.trlbhxf.utils.StatusBarUtils;
import com.tbsurvey.trlbhxf.wight.LoadProgressDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :基类 BaseMvpActivity
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder unBinder;
    protected Context context;
    private LoadProgressDialog loadingDialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        StatusBarUtils.setActivityTranslucent(this);
        context=this;
        Intent intent = getIntent();
        if (intent != null)
            getIntent(intent);
        unBinder = ButterKnife.bind(this);
        if (useEventBus()) {
//            EventBus.getDefault().register(this);//注册eventBus
            BusProvider.getInstance().register(this);
        }
        initView();
//        initData();
        initListener();
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
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (useEventBus()) {
//            if (EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().unregister(this);//注销eventBus
//            }
            BusProvider.getInstance().unregister(this);
        }

    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
            loadingDialog = new LoadProgressDialog(this,"加载数据中...");
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


//   @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getEventBus(EventMessage eventMessage) {
////        Logger.d("getEventBus");
//
//    }


}
