package com.tbsurvey.trlbhxf.ui.fragment.map;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap.ArcMapTool;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap.ArcMapToolImpl;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw.MeasureTool;
import com.tbsurvey.trlbhxf.ui.rxbus.event.BottomLayerChange;
import com.tbsurvey.trlbhxf.ui.rxbus.event.LocationInfoEvent;
import com.tbsurvey.trlbhxf.ui.base.BaseMvpFragment;
import com.tbsurvey.trlbhxf.ui.rxbus.Subscribe;
import com.tbsurvey.trlbhxf.ui.rxbus.event.MapSelectEvent;
import com.tbsurvey.trlbhxf.utils.SharedPreferencesHelper;
import com.tbsurvey.trlbhxf.utils.ViewUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tbsurvey.trlbhxf.utils.ViewUtils.Direction.BOTTOM_TO_TOP;
import static com.tbsurvey.trlbhxf.utils.ViewUtils.Direction.TOP_TO_BOTTOM;


/**
 * author:jxj on 2020/8/24 11:33
 * e-mail:592296083@qq.com
 * desc  :数据检查
 */
public class MapFragment extends BaseMvpFragment<MapPresenter> implements MapContract.View {
    @BindView(R.id.map)
    MapView mapView;
    private ArcMapTool arcMapTool;
    private Point locationCenter = new Point(116.3972282, 39.909604, SpatialReferences.getWgs84());
    private MeasureTool measureTool;
    public static MapFragment newInstance() {

        return new MapFragment();
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        arcMapTool = new ArcMapToolImpl(getContext(), mapView);
        arcMapTool.initMapResource();
        measureTool=new MeasureTool(mapView);
        int a = SharedPreferencesHelper.getInstance().getIntValue(" bottomChangeLayer");
        if (a==0){
            arcMapTool.bottomChangeLayer(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTYX,true,a));
        }else if (a==1){
            arcMapTool.bottomChangeLayer(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTSL,true,a));
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.zoom_in_layout, R.id.zoom_out_layout, R.id.zoom_reset_layout, R.id.Location_layout, R.id.Path_layout
            ,R.id.edit_widget_ceju,R.id.edit_widget_cemian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zoom_in_layout://放大
                arcMapTool.zoomIn();
                break;
            case R.id.zoom_out_layout://缩小
                arcMapTool.zoomOut();
                break;
            case R.id.zoom_reset_layout://复位
                arcMapTool.zoomReset(null);
                break;
            case R.id.Location_layout://定位
                arcMapTool.locationCenter(locationCenter);
                break;
            case R.id.Path_layout://轨迹

                break;
            //测面
            case R.id.edit_widget_cemian:
                    measureTool.ceMian();
                break;
            //测距
            case R.id.edit_widget_ceju:
                measureTool.ceJu();
                break;
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Subscribe
    public void onLocationInfoEvent(LocationInfoEvent event) {
        if (event!=null){
            locationCenter = event.getPoint();
            arcMapTool.location(event.getPoint());
        }
    }

    @Subscribe
    public void onMapSelectEventEvent(MapSelectEvent event) {
        Logger.d(event.toString());
    }

    @Subscribe
    public void onBottomLayerChangeEvent(BottomLayerChange event) {
        if (event.isSelect()) {
            SharedPreferencesHelper.getInstance().putIntValue(" bottomChangeLayer", event.getPosition());
        } else {
            SharedPreferencesHelper.getInstance().putIntValue(" bottomChangeLayer", -1);
        }
        arcMapTool.bottomChangeLayer(event);
    }
}
