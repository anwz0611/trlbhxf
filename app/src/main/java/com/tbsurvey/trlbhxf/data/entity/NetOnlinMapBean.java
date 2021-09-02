package com.tbsurvey.trlbhxf.data.entity;

import java.io.Serializable;

/**
 * author:jxj on 2020/11/4 14:04
 * e-mail:592296083@qq.com
 * desc  :
 */
public class NetOnlinMapBean implements Serializable {
    private String  name;
    private boolean isCheck;
    private String  url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
