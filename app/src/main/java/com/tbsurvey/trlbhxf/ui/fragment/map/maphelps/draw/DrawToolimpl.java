package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.esri.arcgisruntime.mapping.view.SketchStyle;


/**
 * author:jxj on 2021/1/30 10:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public class DrawToolimpl implements IdrawTool {
    public SketchEditor mSketchEditor;
    private SketchStyle mainSketchStyle;
    private MapView mapView;
    private Feature feature;
    private FeatureLayer featureLayer;
    private int type = 0;
    private boolean isQM=false;
    private String object;
    public DrawToolimpl(MapView mapView) {
        this.mapView = mapView;
        /** 初始化编辑工具 **/
        mSketchEditor = new SketchEditor();
        mainSketchStyle = new SketchStyle();
        mSketchEditor.setSketchStyle(mainSketchStyle);
        mapView.setSketchEditor(mSketchEditor);
        mSketchEditor.clearGeometry();
    }

    @Override
    public void Start(Feature feature, FeatureLayer featureLayer) {
//        mapView.setSketchEditor(mSketchEditor);
        this.feature = feature;
        this.featureLayer = featureLayer;
        if (featureLayer != null && feature != null) {
            featureLayer.setFeatureVisible(feature, false);
            mSketchEditor.start(feature.getGeometry());
        }
        GeometryChangedListener();
    }

    @Override
    public void Start(SketchCreationMode sketchCreationMode) {
//        mapView.setSketchEditor(mSketchEditor);
        mSketchEditor.start(sketchCreationMode);
        GeometryChangedListener();
    }

    @Override
    public void Start(Geometry geometry, int type) {
        this.type = type;
//        mapView.setSketchEditor(mSketchEditor);
        mSketchEditor.clearGeometry();
        mSketchEditor.start(geometry);
        GeometryChangedListener();
    }

    @Override
    public void undo() {
        if (mSketchEditor.canUndo()) {
            mSketchEditor.undo();
        }
    }

    @Override
    public void redo() {
        if (mSketchEditor.canRedo()) {
            mSketchEditor.redo();
        }
    }

    @Override
    public void stop() {
        type = 0;
        if (!mSketchEditor.isSketchValid()) {
            mSketchEditor.stop();
            return;
        }
        if (feature != null && featureLayer != null) {
            featureLayer.setFeatureVisible(feature, true);
        }
        this.feature = null;
        this.featureLayer = null;
        // get the geometry from sketch editor
//        mapView.setSketchEditor(null);
        mSketchEditor.stop();
    }

    @Override
    public boolean isSketchValid() {
        return mSketchEditor.isSketchValid();
    }

    @Override
    public void GeometryChangedListener() {
        mSketchEditor.addGeometryChangedListener(sketchGeometryChangedEvent -> {

        });
    }


    @Override
    public Geometry getGeometry() {
        return mSketchEditor.getGeometry();
    }



    @Override
    public void clearGeometry() {
        if (mSketchEditor != null) {
            mSketchEditor.clearGeometry();
        }

    }

    @Override
    public SketchEditor getSketchEditor() {
        return this.mSketchEditor;
    }

    @Override
    public void replaceGeometry(Geometry geometry) {
        mSketchEditor.replaceGeometry(geometry);
    }

}
