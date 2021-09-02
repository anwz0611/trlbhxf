package com.tbsurvey.trlbhxf.ui.rxbus.event;

import java.util.List;

import lombok.Data;

/**
 * author:jxj on 2021/6/28 13:49
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class OfflineMaps {
    private List<String> paths;

    public OfflineMaps(List<String> paths) {
        this.paths = paths;
    }
}
