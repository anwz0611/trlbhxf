package com.tbsurvey.trlbhxf.ui.froms.strategy.base;

import android.content.Context;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * author:jxj on 2021/7/28 16:20
 * e-mail:592296083@qq.com
 * desc  :
 */
public abstract class InputBaseFunctionStrategy <T> implements InputStrategy<T> {
    protected String dbValue;

    private boolean isShowToast = true;

    protected Context mContext;

    public abstract void convert(BaseViewHolder paramBaseViewHolder, T paramT);

    public String getDbValue() {
        return this.dbValue;
    }

    public void hideToast() {
        this.isShowToast = false;
    }

    public void setDbValue(String paramString) {
        this.dbValue = paramString;
    }

    protected void verifyDataFail(String paramString) {
        if (this.isShowToast){
        }

    }
}
