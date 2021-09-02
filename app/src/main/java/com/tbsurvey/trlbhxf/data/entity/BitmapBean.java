package com.tbsurvey.trlbhxf.data.entity;

import android.graphics.drawable.BitmapDrawable;

import lombok.Data;

/**
 * author:jxj on 2021/8/31 14:48
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class BitmapBean {
    private int high;
    private int wight;
    private BitmapDrawable drawable;

    public BitmapBean(int high, int wight, BitmapDrawable drawable) {
        this.high = high;
        this.wight = wight;
        this.drawable = drawable;
    }
}
