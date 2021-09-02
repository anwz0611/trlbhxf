package com.tbsurvey.trlbhxf.ui.rxbus.event;

import com.esri.arcgisruntime.geometry.Point;

import java.io.Serializable;

import lombok.Data;

/**
 * author:jxj on 2021/8/25 11:07
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class LocationInfoEvent implements Serializable {
    private Point point;
    private float Bearing;
    private float Accuracy;

    public LocationInfoEvent(Point point, float bearing, float accuracy) {
        this.point = point;
        Bearing = bearing;
        Accuracy = accuracy;
    }
}
