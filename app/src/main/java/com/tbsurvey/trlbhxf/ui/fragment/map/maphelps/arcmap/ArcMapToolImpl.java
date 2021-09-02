package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.ShapefileFeatureTable;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.esri.arcgisruntime.util.ListenableList;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.entity.LocalShp;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.CacheTiledLayer;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.TianDiTuMethodsClass;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.draw.DrawSymbol;
import com.tbsurvey.trlbhxf.ui.rxbus.event.BottomLayerChange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;

import static com.esri.arcgisruntime.geometry.GeometryType.POINT;
import static com.esri.arcgisruntime.geometry.GeometryType.POLYGON;
import static com.esri.arcgisruntime.geometry.GeometryType.POLYLINE;
import static com.tbsurvey.trlbhxf.app.AppConfig.MYFILENAME;
import static com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR;
import static com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_MERCATOR;
import static com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR;
import static com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap.TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_MERCATOR;
import static com.tbsurvey.trlbhxf.utils.FileUtils.getSDCardPath;
import static com.tbsurvey.trlbhxf.utils.FileUtils.getShpFilesAllName;
import static com.tbsurvey.trlbhxf.utils.StringUtils.getString;

/**
 * author:jxj on 2021/8/25 14:52
 * e-mail:592296083@qq.com
 * desc  :
 */
public class ArcMapToolImpl implements ArcMapTool {
    private Context context;
    private MapView mapView;
    private ArcGISMap map;
    private Map<String, Layer> baseMaps = new ConcurrentHashMap();
    private PictureMarkerSymbol pictureMarkerSymbol;
    private Graphic locationGraphic;
    private GraphicsOverlay locgraphicOverlay = null;//定制显示图层
    private boolean isFirstStart = true;

    public ArcMapToolImpl(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        initLoaction(context);
    }

    private void initLoaction(Context context) {
        locgraphicOverlay = new GraphicsOverlay();
        this.mapView.getGraphicsOverlays().add(locgraphicOverlay);
        pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) context.getResources().getDrawable(R.mipmap.map_marker_loc));
        pictureMarkerSymbol.setHeight(22);
        pictureMarkerSymbol.setWidth(22);
        locationGraphic = new Graphic(new Point(116.3972282, 39.909604, SpatialReferences.getWgs84()), pictureMarkerSymbol);
    }

    @Override
    public void initMapResource() {
//        ArcGISRuntimeEnvironment.setLicense(getString(R.string.appKid));
        mapView.setAttributionTextVisible(false);
        map = new ArcGISMap();
        mapView.setMap(map);
        initBasemap();
        mapView.getMap().setMaxScale(1500);

    }

    @Override
    public void initBasemap() {
        RequestConfiguration requestConfiguration = new RequestConfiguration();
        requestConfiguration.getHeaders().put("referer", "http://www.arcgis.com");

        String cachePath = MYFILENAME + "/网络地图缓存";

        CacheTiledLayer tianDiTuTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TIANDITU_IMAGE_MERCATOR, cachePath + "/TIANDITU_IMAGE_MERCATOR");
        tianDiTuTiledLayer.setRequestConfiguration(requestConfiguration);
        tianDiTuTiledLayer.loadAsync();
        baseMaps.put("TIANDITU_IMAGE_MERCATOR", tianDiTuTiledLayer);


        CacheTiledLayer tianDiTuTiledLayerBz = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR, cachePath + "/TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR");
        tianDiTuTiledLayerBz.setRequestConfiguration(requestConfiguration);
        tianDiTuTiledLayerBz.loadAsync();
        baseMaps.put("tianDiTuTiledLayerBz", tianDiTuTiledLayerBz);

        CacheTiledLayer tianDiTuTiledLayerSl = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TIANDITU_VECTOR_MERCATOR, cachePath + "/TIANDITU_VECTOR_MERCATOR");
        tianDiTuTiledLayerSl.setRequestConfiguration(requestConfiguration);
        tianDiTuTiledLayerSl.loadAsync();
        baseMaps.put("tianDiTuTiledLayerSl", tianDiTuTiledLayerSl);

        CacheTiledLayer tianDiTuTiledLayerSlBz = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR, cachePath + "/TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR");
        tianDiTuTiledLayerSlBz.setRequestConfiguration(requestConfiguration);
        tianDiTuTiledLayerSlBz.loadAsync();
        baseMaps.put("tianDiTuTiledLayerSlBz", tianDiTuTiledLayerSlBz);

//        Basemap basemap = new Basemap();
//        basemap.getBaseLayers().add(tianDiTuTiledLayer);
//        basemap.getBaseLayers().add(tianDiTuTiledLayerBz);
//        map.setBasemap(basemap);

    }

    @Override
    public Observable<List<FeatureLayer>> loadShpFile(String path) {
        return Observable.create(emitter -> {
            List<FeatureLayer> layers = new ArrayList<>();
            List<LocalShp> localShps = getShpFilesAllName(path);
            if (localShps == null) {
//                emitter.onError(new Exception(MYFILENAME + getString(R.string.ToastMsg31)));
                emitter.onNext(layers);
                emitter.onComplete();
            }
            for (int i = 0; i < localShps.size(); i++) {
                String name = localShps.get(i).getName();
                ShapefileFeatureTable shapefileFeatureTable = new ShapefileFeatureTable(localShps.get(i).getUri());
                shapefileFeatureTable.loadAsync();
                shapefileFeatureTable.addDoneLoadingListener(() -> {
                    if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {
                        FeatureLayer featureLayer = new FeatureLayer(shapefileFeatureTable);
                        featureLayer.setName(name);
                        featureLayer.setSelectionColor(Color.YELLOW);
                        GeometryType type = featureLayer.getFeatureTable().getGeometryType();
                        if (type == POINT) {
                            featureLayer.setRenderer(new SimpleRenderer(DrawSymbol.otherRedMarkerSymbol));
                            mapView.setViewpointGeometryAsync(shapefileFeatureTable.getExtent());
                            layers.add(featureLayer);
                        } else if (type == POLYLINE) {
                            featureLayer.setRenderer(new SimpleRenderer(DrawSymbol.mSelectLineSymbol));
                            layers.add(featureLayer);
                        } else if (type == POLYGON) {
                            featureLayer.setRenderer(new SimpleRenderer(DrawSymbol.mFillSymbol1));
                            layers.add(featureLayer);
                        } else {
                            layers.add(featureLayer);
                        }

                    }

                });
            }

            mapView.getMap().getOperationalLayers().addAll(layers);
            emitter.onNext(layers);
            emitter.onComplete();

        });
    }

    @Override
    public void zoomIn() {
        mapView.setViewpointScaleAsync(mapView.getMapScale() * (1.0 / 2));
    }

    @Override
    public void zoomOut() {
        mapView.setViewpointScaleAsync(mapView.getMapScale() * 2);
    }

    @Override
    public void zoomReset(List<FeatureLayer> FealayerList) {
        if (FealayerList.size() > 0) {
            mapView.setViewpointGeometryAsync(FealayerList.get(0).getFullExtent());
        }
    }

    @Override
    public void locationCenter(Point point) {
        if (point != null) {
            mapView.setViewpointCenterAsync(point, 2000);
        }
    }

    @Override
    public void location(Point point) {
        if (point != null) {
            if (isFirstStart) {
                mapView.setViewpointCenterAsync(point, 2200);//定位到中心
                isFirstStart = false;//仅定位到中心一次
            }
            locgraphicOverlay.getGraphics().clear();
            locationGraphic.setGeometry(point);
            locgraphicOverlay.getGraphics().add(locationGraphic);
        }
    }

    @Override
    public void bottomChangeLayer(BottomLayerChange bottomLayerChange) {
        if (bottomLayerChange != null) {
            map.getBasemap().getBaseLayers().clear();
            Basemap basemap = new Basemap();
            if (bottomLayerChange.isSelect()) {
                if (bottomLayerChange.getBaseLayerType() == BaseLayerType.TDTYX) {
                    basemap.getBaseLayers().add(baseMaps.get("TIANDITU_IMAGE_MERCATOR"));
                    basemap.getBaseLayers().add(baseMaps.get("tianDiTuTiledLayerBz"));
                } else if (bottomLayerChange.getBaseLayerType() == BaseLayerType.TDTSL) {
                    basemap.getBaseLayers().add(baseMaps.get("tianDiTuTiledLayerSl"));
//                    basemap.getBaseLayers().add(baseMaps.get("tianDiTuTiledLayerSlBz"));
                }
            }
            map.setBasemap(basemap);
        }

    }

    @Override
    public void clearGraphicSelection() {
        ListenableList<GraphicsOverlay> listenableList = mapView.getGraphicsOverlays();
        if (listenableList != null) {
            for (int i = 0; i < listenableList.size(); i++) {
                GraphicsOverlay g = listenableList.get(i);
                g.clearSelection();
            }
        }
    }

    @Override
    public void clearFeatureSelection() {
        LayerList layers = mapView.getMap().getOperationalLayers();
        if (layers != null) {
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get(i) instanceof FeatureLayer) {
                    FeatureLayer featurelayer = (FeatureLayer) layers.get(i);
                    featurelayer.clearSelection();//清空选择
                }
            }
        }
    }
}
