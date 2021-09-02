package com.tbsurvey.trlbhxf.data.http.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * author:jxj on 2020/9/4 16:05
 * e-mail:592296083@qq.com
 * desc  :
 */
@Data
public class BaseHttpResult<T> implements Serializable {
    private static final long serialVersionUID = 2690553609250007325L;
    /**
     * code : 200
     * msg : SUCCESS
     * success : true
     * data : null
     * result : {"schema":[{"schema_id":"001","schema_name":"方案1"},{"schema_id":"002","schema_name":"方案2"},{"schema_id":"003","schema_name":"方案3"}]}
     */
    private int code;
    private String msg;
    private boolean success;
    private String data;
    private T result;
}
