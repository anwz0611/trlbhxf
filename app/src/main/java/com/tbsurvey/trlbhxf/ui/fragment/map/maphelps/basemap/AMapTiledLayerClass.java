package com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.basemap;

import com.esri.arcgisruntime.arcgisservices.LevelOfDetail;
import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.WebTiledLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author:jxj on 2021/1/21 13:51
 * e-mail:592296083@qq.com
 * desc  :
 */
public class AMapTiledLayerClass {
    private static final List<String> subDomain = Arrays.asList(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"});
    private static final int minZoomLevel = 0;
    private static final int maxZoomLevel = 20;
    private static final int DPI = 96;
    private static final int tileWidth = 256;
    private static final int tileHeight = 256;
    private static final SpatialReference SRID = SpatialReference.create(102113);
    private static final Point origin = new Point(-20037508.342787, 20037508.342787, SRID);
    private static final Envelope envelope = new Envelope(-22041257.773878,
            -32673939.6727517, 22041257.773878, 20851350.0432886, SRID);
    private static final double[] SCALES = {
            591657527.591555,
            295828763.79577702, 147914381.89788899, 73957190.948944002,
            36978595.474472001, 18489297.737236001, 9244648.8686180003,
            4622324.4343090001, 2311162.217155, 1155581.108577, 577790.554289,
            288895.277144, 144447.638572, 72223.819286, 36111.909643,
            18055.954822, 9027.9774109999998, 4513.9887049999998, 2256.994353,
            1128.4971760000001, 564.248588
            , 282.124294, 141.062147
    };
    private static final double[] RESOLUTIONS = {
            156543.03392800014,
            78271.516963999937, 39135.758482000092, 19567.879240999919,
            9783.9396204999593, 4891.9698102499797, 2445.9849051249898,
            1222.9924525624949, 611.49622628138, 305.748113140558,
            152.874056570411, 76.4370282850732, 38.2185141425366,
            19.1092570712683, 9.55462853563415, 4.7773142679493699,
            2.3886571339746849, 1.1943285668550503, 0.59716428355981721,
            0.29858214164761665, 0.149291
            , 0.074646, 0.037323
    };

    public static WebTiledLayer CreateAMapTiledLayer(LayerType layerType) {
        WebTiledLayer webTiledLayer = null;
        String mainUrl;
        TileInfo mainTileInfo;
        String type;
        int typeCode;
        switch (layerType) {
            case AMAP_VECTOR:
                type = "rd";
                typeCode = 8;
                break;
            case AMAP_IMAGE:
                type = "st";
                typeCode = 6;
                break;
            default:
                type = "rd";
                typeCode = 8;
                break;
        }

        mainUrl = "http://web"
                + type
                + "{subDomain}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scl=1&style=" +
                typeCode +
                "&x={col}&y={row}&z={level}";
        List<LevelOfDetail> mainLevelOfDetail = new ArrayList<LevelOfDetail>();
        for (int i = minZoomLevel; i <= maxZoomLevel; i++) {
            LevelOfDetail item = new LevelOfDetail(i, RESOLUTIONS[i], SCALES[i]);
            mainLevelOfDetail.add(item);
        }
        mainTileInfo = new TileInfo(
                DPI,
                TileInfo.ImageFormat.PNG24,
                mainLevelOfDetail,
                origin,
                SRID,
                tileHeight,
                tileWidth
        );
        webTiledLayer = new WebTiledLayer(
                mainUrl,
                subDomain,
                mainTileInfo,
                envelope
        );
        webTiledLayer.loadAsync();

        return webTiledLayer;
    }

    public enum LayerType {
        AMAP_VECTOR,
        AMAP_IMAGE

    }
}
