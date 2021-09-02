package com.tbsurvey.trlbhxf.ui.froms.strategy;

import android.content.Context;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.froms.entity.DynamicFormItemBean;
import com.tbsurvey.trlbhxf.ui.froms.strategy.base.InputDataCollectionFilledListFunctionStrategy;

/**
 * author:jxj on 2021/7/28 17:23
 * e-mail:592296083@qq.com
 * desc  :
 */
public class InputSelectStrategy extends InputDataCollectionFilledListFunctionStrategy {
    public InputSelectStrategy(Context paramContext) {
        this.mContext = paramContext;
    }
    @Override
    public void convert(BaseViewHolder paramBaseViewHolder, DynamicFormItemBean paramT) {
        paramBaseViewHolder.setText(R.id.gd_item_name, paramT.getEditName());
        WheelView spinner = paramBaseViewHolder.getView(R.id.wheel_view);
//        List<DetialZiDian> list=getDictionaries(paramT.getEditName());


    }

    @Override
    public boolean isCompleted(DynamicFormItemBean paramT) {
        return false;
    }
}
