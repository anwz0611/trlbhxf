package com.tbsurvey.trlbhxf.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.activity.imagemanage.ImageActivity;
import com.tbsurvey.trlbhxf.ui.base.BaseMvpActivity;
import com.tbsurvey.trlbhxf.ui.fragment.map.MapFragment;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw.MeasureTool;
import com.tbsurvey.trlbhxf.ui.service.LocationService;
import com.tbsurvey.trlbhxf.utils.ViewUtils;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tbsurvey.trlbhxf.utils.ViewUtils.Direction.BOTTOM_TO_TOP;
import static com.tbsurvey.trlbhxf.utils.ViewUtils.Direction.TOP_TO_BOTTOM;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.ExpandableLayoutLayermanager)
    ExpandableLayout ExpandableLayoutLayermanager;//图层侧滑菜单
    @BindView(R.id.base_widget_view_tools_linerview)
    LinearLayout baseWidgetViewToolsLinerview;//主页下方菜单
    @BindView(R.id.imageButtonShowHide)
    ImageButton imageButtonShowHide; //图层菜单显示按钮
    private boolean show = false;  //侧滑图层菜单状态
    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initView() {
       startService(new Intent(this, LocationService.class));
        initFragment();
        ExpandableLayoutLayermanager.setOrientation(ExpandableLayout.HORIZONTAL);
        ExpandableLayoutLayermanager.collapse();
    }

    @Override
    protected void initListener() {
        ExpandableLayoutLayermanager.setOnExpansionUpdateListener(expansionFraction -> {
            if (expansionFraction == 0) {
                show = false;
                imageButtonShowHide.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_right2));
                if (baseWidgetViewToolsLinerview.getVisibility() == View.GONE) {
                    ViewUtils.slideIn(baseWidgetViewToolsLinerview, 500, null, BOTTOM_TO_TOP);
                }
            } else if (expansionFraction == 1) {
                show = true;
                imageButtonShowHide.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_left2));
                if (baseWidgetViewToolsLinerview.getVisibility() == View.VISIBLE) {
                    ViewUtils.slideOut(baseWidgetViewToolsLinerview, 500, null, TOP_TO_BOTTOM);
                }
            }
        });
    }
    private void initFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, MapFragment.newInstance());
        transaction.commitNow();
    }
    @OnClick({R.id.imageButtonShowHide,R.id.btn_scheme, R.id.btn_layerManager,
            R.id.btn_userManager, R.id.btn_draw, R.id.btn_shadow,R.id.btn_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            //主页下方菜单
            //方案
            case R.id.btn_scheme:
//                Bundle bundle = new Bundle();
//                bundle.putInt("type", modelType);
//                startActivity(ExportActivity.class, bundle);
                break;
            //图层管理
            case R.id.btn_layerManager:
            case R.id.imageButtonShowHide://图层侧滑菜单显示
//                setBarVisibility(true);
                show = !show;
                setBarVisibility(show);
                break;
            //用户管理
            case R.id.btn_userManager:
//                startActivity(UsersActivity.class);
                break;
            //绘制
            case R.id.btn_draw:
                setBarVisibility(true);
                break;
            //影像
            case R.id.btn_shadow:
                startActivity(ImageActivity.class);
                break;
            //设置
            case R.id.btn_setting:
//                startActivity(SettingActivity.class);
                break;

        }
    }
    //图层侧滑菜单显示
    @SuppressLint("UseCompatLoadingForDrawables")
    private void setBarVisibility(boolean visibility) {
        show = visibility;
        if (visibility) {
            ExpandableLayoutLayermanager.expand(true);
            imageButtonShowHide.setImageDrawable(context.getResources().getDrawable(R.mipmap.arrow_left2));
        } else {
            ExpandableLayoutLayermanager.collapse(true);
            imageButtonShowHide.setImageDrawable(context.getResources().getDrawable(R.mipmap.arrow_right2));
        }
    }
    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LocationService.class));
    }
}