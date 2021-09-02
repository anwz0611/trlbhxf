package com.tbsurvey.trlbhxf.ui.fragment.froms;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.base.BaseMvpFragment;
import com.tbsurvey.trlbhxf.ui.fragment.froms.adapter.DataCollectionFilledAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:jxj on 2020/8/24 11:33
 * e-mail:592296083@qq.com
 * desc  :动态表单
 */
public class DataCollectionFragment extends BaseMvpFragment<DataCollectionPresenter> implements DataCollectionContract.View {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    private DataCollectionFilledAdapter mAdapter = new DataCollectionFilledAdapter(null);

    public static DataCollectionFragment newInstance() {
        return new DataCollectionFragment();
    }

    @Override
    protected DataCollectionPresenter createPresenter() {
        return new DataCollectionPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_collection_filled;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.rvData.setNestedScrollingEnabled(false);
        this.rvData.setLayoutManager(linearLayoutManager);
        this.rvData.setAdapter(this.mAdapter);
    }

    @Override
    protected void initListener() {
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void QueryData(Object strings) {

    }

    @Override
    public void showError(String msg) {

    }

}
