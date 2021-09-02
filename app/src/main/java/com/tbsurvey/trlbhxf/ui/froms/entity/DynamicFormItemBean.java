package com.tbsurvey.trlbhxf.ui.froms.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

import lombok.Data;

/**
 * author:jxj on 2021/7/28 17:20
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class DynamicFormItemBean implements Serializable, MultiItemEntity {

    /**
     * editName : 样方编号:
     * editValue : YANGDI_BH
     * tableValue :
     * isMust : true
     * type : TEXT
     * inputType : 1
     * execSQL : SELECT YANGFANG_H FROM 草本调查表 WHERE YANGDI_H='unknown' AND YANGFANG_H='substitute'
     * msg : 样方编号必填
     */

    private String editName;
    private String editValue;
    private String tableValue;
    private String isMust;
    private String type;
    private Integer inputType;
    private String execSQL;
    private String msg;

    @Override
    public int getItemType() {
        return inputType;
    }
}
