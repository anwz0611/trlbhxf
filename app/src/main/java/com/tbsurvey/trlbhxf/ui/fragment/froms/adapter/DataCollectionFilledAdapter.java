package com.tbsurvey.trlbhxf.ui.fragment.froms.adapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tbsurvey.trlbhxf.ui.froms.entity.DynamicFormItemBean;
import com.tbsurvey.trlbhxf.ui.froms.enums.InputTypeEnum;
import com.tbsurvey.trlbhxf.ui.froms.factory.InputStrategyFactory;
import com.tbsurvey.trlbhxf.ui.froms.strategy.base.InputBaseFunctionStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:jxj on 2021/7/28 17:55
 * e-mail:592296083@qq.com
 * desc  :
 */
public class DataCollectionFilledAdapter extends BaseMultiItemQuickAdapter<DynamicFormItemBean, BaseViewHolder> {
    private Map<Integer, InputBaseFunctionStrategy> map = new HashMap<>();

    public DataCollectionFilledAdapter(List<DynamicFormItemBean> paramList) {
        super(paramList);
        for (InputTypeEnum inputTypeEnum : InputTypeEnum.values())
            addItemType(inputTypeEnum.getType(), inputTypeEnum.getLayoutResId());
    }

    protected void convert(BaseViewHolder paramBaseViewHolder, DynamicFormItemBean paramDynamicFormItemBean) {
        InputBaseFunctionStrategy inputBaseFunctionStrategy2 = this.map.get(Integer.valueOf(paramBaseViewHolder.getItemViewType()));
        InputBaseFunctionStrategy inputBaseFunctionStrategy1 = inputBaseFunctionStrategy2;
        if (inputBaseFunctionStrategy2 == null) {
            inputBaseFunctionStrategy1 = InputStrategyFactory.getInstance(getContext(), InputTypeEnum.valueType(paramBaseViewHolder.getItemViewType()));
            this.map.put(Integer.valueOf(paramBaseViewHolder.getItemViewType()), inputBaseFunctionStrategy1);
        }
        inputBaseFunctionStrategy1.convert(paramBaseViewHolder, paramDynamicFormItemBean);
    }
}
