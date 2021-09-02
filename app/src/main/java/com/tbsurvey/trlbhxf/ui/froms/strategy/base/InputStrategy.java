package com.tbsurvey.trlbhxf.ui.froms.strategy.base;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * author:jxj on 2021/7/28 15:53
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface InputStrategy<T> {
    void convert(BaseViewHolder paramBaseViewHolder, T paramT);

    boolean isCompleted(T paramT);
}
