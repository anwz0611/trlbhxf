package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;

import java.util.List;


/**
 * author:jxj on 2021/1/30 11:08
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface IdrawTool {
    void Start(Feature feature, FeatureLayer featureLayer);
    void Start(SketchCreationMode sketchCreationMode);
    void Start(Geometry geometry, int type);
    void undo();
    void redo();
    void stop();
    boolean isSketchValid();
    void GeometryChangedListener();
    Geometry getGeometry();
    void clearGeometry();
    SketchEditor getSketchEditor();
    void replaceGeometry(Geometry geometry);
}
