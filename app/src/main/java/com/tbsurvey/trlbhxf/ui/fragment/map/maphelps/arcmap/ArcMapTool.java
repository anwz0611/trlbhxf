package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.tbsurvey.trlbhxf.ui.rxbus.event.BottomLayerChange;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:jxj on 2021/8/25 14:50
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface ArcMapTool {
    /**
     * 初始化map
     */
    void initMapResource();

    /**
     * 初始化底图
     */
    void initBasemap();

    /**
     * 加载shp
     */
    Observable<List<FeatureLayer>> loadShpFile(String path);

    /**
     * 地图缩小
     */
    void zoomIn();

    /**
     * 地图放大
     */
    void zoomOut();

    /**
     * 地图复位
     */
    void zoomReset(List<FeatureLayer> FealayerList);

    /**
     * 地图定位
     *
     * @param point 定位点
     */
    void locationCenter(Point point);

    /**
     * 地图定位
     *
     * @param point 定位
     */
    void location(Point point);

    /**
     * 地图底图切换
     */
    void bottomChangeLayer(BottomLayerChange bottomLayerChange);

    /**
     * 清空要素选择状态
     */
    void clearGraphicSelection();

    /**
     * 清空要素选择状态
     */
    void clearFeatureSelection();

    public enum BaseLayerType {
         TDTYX,TDTSL
    }
}
