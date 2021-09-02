package com.tbsurvey.trlbhxf.ui.fragment.imagemanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arialyy.annotations.Download;
import com.arialyy.annotations.DownloadGroup;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.task.DownloadGroupTask;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.ALog;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.entity.NetOnlinMapBean;
import com.tbsurvey.trlbhxf.ui.base.BaseFragment;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.adapter.OnlineAdapeter;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.adapter.downadapter.DownloadAdapter;
import com.tbsurvey.trlbhxf.utils.SharedPreferencesHelper;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:jxj on 2020/8/24 11:33
 * e-mail:592296083@qq.com
 * desc  :网络地图
 */
public class NetImgFragment<T> extends BaseFragment {
    public static String TAG = "NetImgFragment";
    @BindView(R.id.rv_zaixian)
    RecyclerView rv_zaixian; //在线列表
    @BindView(R.id.load_rv)
    RecyclerView load_rv; //离线下载列表
    @BindView(R.id.net_load_btn)
    LinearLayout net_load_btn;
    private OnlineAdapeter adapeter;
    private DownloadAdapter mAdapter;
    private List<NetOnlinMapBean> zaixianLise = new ArrayList<>();
    private List<AbsEntity> mData = new ArrayList<>();
    String[] mapName=new String[]{"天地图影像","天地图矢量"};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_net_img;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Aria.download(this).register();

    }

    @Override
    public void onResume() {
        super.onResume();
//        mData.clear();
//        List<AbsEntity> temps = Aria.download(this).getTotalTaskList();
//        if (temps != null && !temps.isEmpty()) {
//
//            for (AbsEntity temp : temps) {
//                ALog.d(TAG, "state = " + temp.getState());
//            }
//            mData.addAll(temps);
//        }
//        mAdapter.notifyDataSetChanged();
//        Aria.download(this).resumeAllTask();
    }

    @Override
    protected void initView() {

        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_zaixian.setLayoutManager(layoutManager);
        adapeter = new OnlineAdapeter(getActivity(), zaixianLise);
        rv_zaixian.setAdapter(adapeter);
        mAdapter = new DownloadAdapter(getContext(), mData);
        load_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        load_rv.setAdapter(mAdapter);
    }

    private void initData() {
        int a = SharedPreferencesHelper.getInstance().getIntValue(" bottomChangeLayer");
        for (int i = 0; i <mapName.length ; i++) {
            NetOnlinMapBean netOnlinMapBean = new NetOnlinMapBean();
            netOnlinMapBean.setName(mapName[i]);
            if (a==i){
                netOnlinMapBean.setCheck(true);
            }else {
                netOnlinMapBean.setCheck(false);
            }
            zaixianLise.add(netOnlinMapBean);
        }
//
        InitTemps(false);
    }

    private void InitTemps(boolean isUpdate) {
        mData.clear();
        List<AbsEntity> temps = Aria.download(this).getTotalTaskList();

        if (temps != null && !temps.isEmpty()) {
            for (AbsEntity temp : temps) {
                ALog.d(TAG, "state = " + temp.getState());
                if (isUpdate){
                    if (mAdapter!=null){
                        mAdapter.updateState(temp);
                    }
                }
            }
            mData.addAll(temps);
        }
    }

    @Override
    protected void initListener() {
    }

    @OnClick({R.id.net_load_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0x001){
            InitTemps(false);
            mAdapter=new DownloadAdapter(getContext(), mData);
            load_rv.setAdapter(mAdapter);
            Aria.download(this).resumeAllTask();
        }
    }

    @Download.onPre void onPre(DownloadTask task) {
        mAdapter.updateState(task.getEntity());
        Log.d(TAG, task.getTaskName() + ", " + task.getState());
    }

    @Download.onWait void onWait(DownloadTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskStart void taskStart(DownloadTask task) {
        Log.d(TAG, task.getTaskName() + ", " + task.getState());
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskResume void taskResume(DownloadTask task) {
        Log.d(TAG, task.getTaskName() + ", " + task.getState());
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskStop void taskStop(DownloadTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskCancel void taskCancel(DownloadTask task) {
        mAdapter.updateState(task.getEntity());
        List<DownloadEntity> tasks = Aria.download(this).getAllNotCompleteTask();
        if (tasks != null){
            ALog.d(TAG, "未完成的任务数：" + tasks.size());
        }
    }

    @Download.onTaskFail void taskFail(DownloadTask task) {
        if (task == null || task.getEntity() == null){
            return;
        }
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskComplete void taskComplete(DownloadTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @Download.onTaskRunning void taskRunning(DownloadTask task) {
        mAdapter.setProgress(task.getEntity());
    }

    //////////////////////////////////// 下面为任务组的处理 /////////////////////////////////////////

    @DownloadGroup.onPre void onGroupPre(DownloadGroupTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskStart void groupTaskStart(DownloadGroupTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onWait void groupTaskWait(DownloadGroupTask task) {
        ALog.d(TAG, String.format("group【%s】wait---", task.getTaskName()));
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskResume void groupTaskResume(DownloadGroupTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskStop void groupTaskStop(DownloadGroupTask task) {
        ALog.d(TAG, String.format("group【%s】stop", task.getTaskName()));
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskCancel void groupTaskCancel(DownloadGroupTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskFail void groupTaskFail(DownloadGroupTask task) {
        if (task != null) {
            ALog.d(TAG, String.format("group【%s】fail", task.getTaskName()));
            mAdapter.updateState(task.getEntity());
        }
    }

    @DownloadGroup.onTaskComplete void groupTaskComplete(DownloadGroupTask task) {
        mAdapter.updateState(task.getEntity());
    }

    @DownloadGroup.onTaskRunning() void groupTaskRunning(DownloadGroupTask task) {
        ALog.d(TAG, String.format("group【%s】running", task.getTaskName()));
        mAdapter.setProgress(task.getEntity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }
}
