package com.tbsurvey.trlbhxf.ui.activity.imagemanage;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.base.BaseActivity;
import com.tbsurvey.trlbhxf.ui.base.BaseFragment;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.LocalImgFragment;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.NetImgFragment;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:jxj on 2020/9/2 13:50
 * e-mail:592296083@qq.com
 * desc  :影像管理
 */
public class ImageActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabSegment)
    TabLayout mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;
    String[] pages = {"网络地图管理", "本地影像管理"};
    public static List<String> string=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void getIntent(Intent intent) {
//       Bundle bundle= intent.getExtras();
//        List<String> strings= bundle.getStringArrayList("localImg");
//        string.addAll(strings);
    }

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(R.mipmap.back_img);
        toolbar.setNavigationOnClickListener(view -> finish());
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager());
        mTabSegment.addTab(mTabSegment.newTab().setText(pages[0]));
        mTabSegment.addTab(mTabSegment.newTab().setText(pages[1]));
        adapter.addFragment(new NetImgFragment(), pages[0]);
        adapter.addFragment(new LocalImgFragment(), pages[1]);
        mContentViewPager.setAdapter(adapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setupWithViewPager(mContentViewPager, false);

    }

    @Override
    protected void initListener() {

    }

    @OnClick({})
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



}
