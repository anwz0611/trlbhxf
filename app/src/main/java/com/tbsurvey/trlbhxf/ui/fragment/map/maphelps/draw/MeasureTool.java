package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw;

import android.widget.TextView;

import com.esri.arcgisruntime.geometry.AreaUnit;
import com.esri.arcgisruntime.geometry.AreaUnitId;
import com.esri.arcgisruntime.geometry.GeodeticCurveType;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.LinearUnit;
import com.esri.arcgisruntime.geometry.LinearUnitId;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.esri.arcgisruntime.mapping.view.SketchStyle;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.tbsurvey.trlbhxf.data.entity.BitmapBean;
import com.tbsurvey.trlbhxf.utils.Utils;
import com.tbsurvey.trlbhxf.wight.MyToast;

import java.text.DecimalFormat;

import static com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw.DrawSymbol.markerSymbol;

/**
 * author:jxj on 2021/1/19 17:26
 * e-mail:592296083@qq.com
 * desc  :
 */
public class MeasureTool {
    private SketchEditor mainSketchEditor;
    private SketchStyle mainSketchStyle;
    private MapView mapView;
    private DecimalFormat df = new DecimalFormat("0.000000");
    private GraphicsOverlay measureGraphocs = new GraphicsOverlay();

    public MeasureTool(MapView mapView) {
        this.mapView = mapView;
        /** 初始化编辑工具 **/
        mainSketchEditor = new SketchEditor();
        mainSketchStyle = new SketchStyle();
        mainSketchEditor.setSketchStyle(mainSketchStyle);
        mapView.setSketchEditor(mainSketchEditor);
        addListener();
        mapView.getGraphicsOverlays().add(measureGraphocs);
    }

    private void addListener() {
        mainSketchEditor.addGeometryChangedListener(sketchGeometryChangedEvent -> {
            Geometry geometry = sketchGeometryChangedEvent.getGeometry();
            if (geometry == null || geometry.isEmpty()) return;
            GeometryType type = geometry.getGeometryType();
            switch (type) {
                case POINT:
                    Point point = (Point) GeometryEngine.project(geometry, SpatialReference.create(4490));
                    String point_str = "经度：" + df.format(point.getX()) + " 纬度：" + df.format(point.getY());
                    break;
                case POLYLINE:
                    double length = GeometryEngine.lengthGeodetic((Polyline) geometry, new LinearUnit(LinearUnitId.METERS), GeodeticCurveType.GEODESIC);
                    String length_str ="长度:" +getLengthString(length);
                    toGraphics(geometry, length_str);
                    break;
                case POLYGON:
                    double area = GeometryEngine.areaGeodetic((Polygon) geometry, new AreaUnit(AreaUnitId.SQUARE_METERS), GeodeticCurveType.GEODESIC);
                    String area_str = "面积:" + getAreaString(area);
                    toGraphics(geometry, area_str);
                    break;

            }
        });
    }

    private void toGraphics(Geometry geometry, String length_str) {
        measureGraphocs.getGraphics().clear();
        BitmapBean bean = Utils.generateBitmap(length_str);
        PictureMarkerSymbol markerSymbol = new PictureMarkerSymbol(bean.getDrawable());
        markerSymbol.setHeight(bean.getHigh());
        markerSymbol.setWidth(bean.getWight());
        markerSymbol.setOffsetY(11);
        markerSymbol.loadAsync();
        Graphic measureGraphic = new Graphic(geometry.getExtent().getCenter(), markerSymbol);
        measureGraphocs.getGraphics().add(measureGraphic);
    }

    public void inactive() {
//        mapView.setSketchEditor(null);
        mainSketchEditor.stop();

    }

    public void ceDian() {
        mainSketchEditor.clearGeometry();
        mainSketchEditor.start(SketchCreationMode.POINT);

    }



    public void ceJu() {
        mainSketchEditor.clearGeometry();
        mainSketchEditor.start(SketchCreationMode.POLYLINE);
    }

    public void ceMian() {
        mainSketchEditor.clearGeometry();
        mainSketchEditor.start(SketchCreationMode.POLYGON);
    }

    /**
     * 获取长度信息
     *
     * @param dValue
     * @return
     */
    private String getLengthString(double dValue) {
        long length = Math.abs(Math.round(dValue));
        String sLength = "";
        if (length > 1000) {
            double dArea = length / 1000.0;
            sLength = Double.toString(dArea) + "千米";//平方公里
        } else {
            sLength = Double.toString(length) + "米";//平方米
        }
        return sLength;
    }

    /**
     * 获取面积信息
     *
     * @param dValue
     * @return
     */
    private String getAreaString(double dValue) {
        long area = Math.abs(Math.round(dValue));
        String sArea = "";
        // 顺时针绘制多边形，面积为正，逆时针绘制，则面积为负
        if (area >= 1000000) {
            double dArea = area / 1000000.0;
            sArea = Double.toString(dArea) + "平方千米";//平方公里
        } else
            sArea = Double.toString(area) + "平方米";//平方米
        return sArea;
    }

    public enum MeasureType {

    }
    private boolean checkBaseLayer() {
        if (mapView.getMap().getBasemap().getBaseLayers().size()==0){
            MyToast.info("缺少底图");
            return true;
        }
        return false;
    }
}
