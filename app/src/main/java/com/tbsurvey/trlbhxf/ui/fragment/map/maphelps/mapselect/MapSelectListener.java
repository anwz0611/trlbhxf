package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.mapselect;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.util.ListenableList;
import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.ui.rxbus.BusProvider;
import com.tbsurvey.trlbhxf.ui.rxbus.event.MapSelectEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * author:jxj on 2021/1/15 09:23
 * e-mail:592296083@qq.com
 * desc  :
 */
public class MapSelectListener extends DefaultMapViewOnTouchListener {
    private Context context;
    private boolean isOnLongpress = false;
    public static int SELECT_G_0 = 0; //短按
    public static int SELECT_G_1 = 1;//长按
    public static int SELECT_F_0 = 2;
    public static int SELECT_F_1 = 3;
    public static int SELECT_F_2 = 4;
    public static int SELECT_F_3 = 5;
    private Feature selectFeature;

    public MapSelectListener(Context context, MapView mapView) {
        super(context, mapView);
        this.context = context;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        isOnLongpress = true;
        super.onLongPress(e);
    }

    @Override
    public boolean onUp(MotionEvent e) {
        Logger.d("onUp");
        if (isOnLongpress) {
            identifyMapLayers(e);
        }
        return super.onUp(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        identifyMapLayers(e);
        isOnLongpress = false;
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onRotate(MotionEvent event, double rotationAngle) {
        return false;
    }

    /**
     * 地图点击查询
     *
     * @param e
     */
    private void identifyMapLayers(MotionEvent e) {
        Point clickPoint = new Point(Math.round(e.getX()), Math.round(e.getY()));
        int tolerance = 5;
        getSelectGraphic(clickPoint, tolerance);

    }

    private void getSelectFeature(Point clickPoint, int tolerance) {
        final ListenableFuture<List<IdentifyLayerResult>> identifyFuture = mMapView.identifyLayersAsync(clickPoint, tolerance, false);
        identifyFuture.addDoneListener(() -> {
            try {
                List<Feature> selectFeatureList = new ArrayList<>();
                List<IdentifyLayerResult> identifyLayersResults = identifyFuture.get();
                for (IdentifyLayerResult identifyLayerResult : identifyLayersResults) {
                    for (GeoElement identifiedElement : identifyLayerResult.getElements()) {
//                        identifyLayerResult.getLayerContent();
                        if (identifiedElement instanceof Feature) {
                            Feature identifiedFeature = (Feature) identifiedElement;
                            selectFeatureList.add(identifiedFeature);
                        }
                    }
                }
                selectFeature(selectFeatureList);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void getSelectGraphic(Point clickPoint, int tolerance) {
        List<Graphic> selectGraphicList = new ArrayList<>();
        final ListenableFuture<IdentifyGraphicsOverlayResult> identifyFuture = mMapView.identifyGraphicsOverlayAsync(mMapView.getGraphicsOverlays().get(0), clickPoint, tolerance, false);
        identifyFuture.addDoneListener(() -> {
            try {
                IdentifyGraphicsOverlayResult identifyLayersResults = identifyFuture.get();
                for (GeoElement identifiedElement : identifyLayersResults.getGraphics()) {
                    if (identifiedElement instanceof Graphic) {
                        Graphic identifiedFeature = (Graphic) identifiedElement;
                        selectGraphicList.add(identifiedFeature);
                    }
                }
                if (selectGraphicList.size() > 0) {
                    selectGraphic(selectGraphicList);
                } else {
                    getSelectFeature(clickPoint, tolerance);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * 用户选择要素
     *
     * @param selectGraphicList
     */
    private void selectGraphic(final List<Graphic> selectGraphicList) {
        clearAllFeatureSelect();//清空选择
        int num = selectGraphicList.size();
        if (num == 0) {

        } else if (num == 1) {
            setGraphicSelect(selectGraphicList.get(0));
        } else {
            boolean a = true;
            for (int i = 0; i < selectGraphicList.size(); i++) {
                if (selectGraphicList.get(i).getGeometry().getGeometryType() == GeometryType.POINT) {
                    setGraphicSelect(selectGraphicList.get(i));
                    a = false;
                    return;
                }
            }
            if (a) {
                double d = Double.MAX_VALUE;
                int index = -1;
                for (int i = 0; i < selectGraphicList.size(); i++) {
                    if (d > GeometryEngine.area((Polygon) selectGraphicList.get(i).getGeometry())) {
                        d = GeometryEngine.area((Polygon) selectGraphicList.get(i).getGeometry());
                        index = i;
                    }
                }
                setGraphicSelect(selectGraphicList.get(index));
            }
        }
    }

    /**
     * 用户选择要素
     *
     * @param selectFeatureList
     */
    private void selectFeature(final List<Feature> selectFeatureList) {
        clearAllFeatureSelect();//清空选择
        int num = selectFeatureList.size();
        if (num == 0) {
            BusProvider.getInstance().post(new MapSelectEvent(null, null, SelectType.NOTHING));
        } else if (num == 1) {
            setFeatureSelect(selectFeatureList.get(0));
        } else {
            for (int i = 0; i < selectFeatureList.size(); i++) {
                if (selectFeatureList.get(i).getGeometry().getGeometryType() == GeometryType.POINT) {
                    setFeatureSelect(selectFeatureList.get(i));
                    return;
                }
            }
            for (int i = 0; i < selectFeatureList.size(); i++) {
                if (selectFeatureList.get(i).getGeometry().getGeometryType() == GeometryType.POLYLINE) {
                    setFeatureSelect(selectFeatureList.get(i));
                    return;
                }
            }
            double d = Double.MAX_VALUE;
            int index = -1;
            for (int i = 0; i < selectFeatureList.size(); i++) {
                if (d > GeometryEngine.area((Polygon) selectFeatureList.get(i).getGeometry())) {
                    d = GeometryEngine.area((Polygon) selectFeatureList.get(i).getGeometry());
                    index = i;
                }
            }
            setFeatureSelect(selectFeatureList.get(index));
        }
    }

    /**
     * 设置要素选中
     *
     * @param feature
     */
    public void setFeatureSelect(Feature feature) {
        if (selectFeature != null) {
            if (feature.getGeometry().equals(selectFeature.getGeometry())) {
                clearAllFeatureSelect();
                selectFeature = null;
                BusProvider.getInstance().post(new MapSelectEvent(feature, null, SelectType.FEATURE));
                return;
            }
        }

        this.selectFeature = feature;
        //设置要素选中
        FeatureLayer identifiedidLayer = feature.getFeatureTable().getFeatureLayer();
        identifiedidLayer.setSelectionColor(Color.YELLOW);
        identifiedidLayer.setSelectionWidth(20);
        identifiedidLayer.selectFeature(feature);
        if (isOnLongpress) {

        } else {

        }
        BusProvider.getInstance().post(new MapSelectEvent(feature, null, SelectType.FEATURE));
        isOnLongpress = false;
    }

    /**
     * 设置要素选中
     *
     * @param graphic
     */
    public void setGraphicSelect(Graphic graphic) {
        //设置要素选中
        List<Graphic> graphics = new ArrayList<>();
        graphics.add(graphic);
        GraphicsOverlay identifiedidLayer = graphic.getGraphicsOverlay();
        identifiedidLayer.setSelectionColor(Color.YELLOW);
        identifiedidLayer.selectGraphics(graphics);

        if (isOnLongpress) {

        } else {

        }

        BusProvider.getInstance().post(new MapSelectEvent(null, graphic, SelectType.FEATURE));
        isOnLongpress = false;
    }

    /**
     * 清空所有要素选择
     */
    public void clearAllFeatureSelect() {
        List<Layer> layers = mMapView.getMap().getOperationalLayers();
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i) instanceof FeatureLayer) {
                FeatureLayer featureLayer = (FeatureLayer) layers.get(i);
                featureLayer.clearSelection();
            }

        }
        ListenableList<GraphicsOverlay> listenableList = mMapView.getGraphicsOverlays();
        for (int i = 0; i < listenableList.size(); i++) {
            GraphicsOverlay graphicsOverlay = (GraphicsOverlay) listenableList.get(i);
            graphicsOverlay.clearSelection();
        }
    }

    /**
     * 恢复默认状态
     */
    public void clear() {
        clearAllFeatureSelect();

    }

    /**
     * 获取待选择要素列表名称
     *
     * @param selectFeature 待选中要素列表信息
     * @return
     */
    private String[] getSelectFeatureInfoListName(List<Feature> selectFeature) {
        List<String> lsname = new ArrayList<>();
        for (int i = 0; i < selectFeature.size(); i++)
            lsname.add(selectFeature.get(i).getFeatureTable().getTableName());
        return lsname.toArray(new String[lsname.size()]);
    }

    public enum SelectType {
        FEATURE, GRAPHIC, NOTHING
    }
}
