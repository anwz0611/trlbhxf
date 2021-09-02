package com.tbsurvey.trlbhxf.ui.rxbus.event;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.mapselect.MapSelectListener;

import java.io.Serializable;

import lombok.Data;

/**
 * author:jxj on 2021/8/25 14:32
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class MapSelectEvent implements Serializable {
    private Feature feature;
    private Graphic graphic;
    private MapSelectListener.SelectType selectType;

    public MapSelectEvent(Feature feature, Graphic graphic, MapSelectListener.SelectType selectType) {
        this.feature = feature;
        this.graphic = graphic;
        this.selectType = selectType;
    }
}
