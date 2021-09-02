package com.tbsurvey.trlbhxf.ui.rxbus.event;

import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap.ArcMapTool;

import java.io.Serializable;

import lombok.Data;

/**
 * author:jxj on 2021/8/26 16:48
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class BottomLayerChange implements Serializable {
    private ArcMapTool.BaseLayerType  baseLayerType;
    private boolean isSelect;
    private int position;
    public BottomLayerChange(ArcMapTool.BaseLayerType baseLayerType, boolean isSelect, int position) {
        this.baseLayerType = baseLayerType;
        this.isSelect = isSelect;
        this.position = position;
    }
}
