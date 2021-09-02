package com.tbsurvey.trlbhxf.ui.froms.strategy;

import android.content.Context;
import android.widget.EditText;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.froms.entity.DynamicFormItemBean;
import com.tbsurvey.trlbhxf.ui.froms.strategy.base.InputDataCollectionFilledListFunctionStrategy;


/**
 * author:jxj on 2021/7/28 17:23
 * e-mail:592296083@qq.com
 * desc  :
 */
public class InputEditTextStrategy extends InputDataCollectionFilledListFunctionStrategy {
    public InputEditTextStrategy(Context paramContext) {
        this.mContext = paramContext;
    }

    @Override
    public void convert(BaseViewHolder paramBaseViewHolder, DynamicFormItemBean paramT) {
        paramBaseViewHolder.setText(R.id.gd_item_name, paramT.getEditName());
        paramBaseViewHolder.setText(R.id.gd_item_edit, paramT.getTableValue());
        EditText etContent = paramBaseViewHolder.getView(R.id.gd_item_edit);
        RxTextView.textChanges(etContent).subscribe(charSequence -> paramT.setTableValue(charSequence.toString()));
    }

    @Override
    public boolean isCompleted(DynamicFormItemBean paramT) {
        return false;
    }
}
