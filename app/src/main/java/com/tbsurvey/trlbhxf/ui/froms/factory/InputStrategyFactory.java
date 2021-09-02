package com.tbsurvey.trlbhxf.ui.froms.factory;

import android.content.Context;

import com.tbsurvey.trlbhxf.ui.froms.enums.InputTypeEnum;
import com.tbsurvey.trlbhxf.ui.froms.strategy.InputDecimalNumberStrategy;
import com.tbsurvey.trlbhxf.ui.froms.strategy.InputEditTextStrategy;
import com.tbsurvey.trlbhxf.ui.froms.strategy.InputSelectStrategy;
import com.tbsurvey.trlbhxf.ui.froms.strategy.base.InputBaseFunctionStrategy;


/**
 * author:jxj on 2021/7/28 17:37
 * e-mail:592296083@qq.com
 * desc  :
 */
public class InputStrategyFactory {
    public static InputBaseFunctionStrategy getInstance(Context paramContext, InputTypeEnum paramInputTypeEnum) {
        switch (paramInputTypeEnum) {
            default:
                return null;
            case TYPE_INPUT_EDETTEXT:
                return new InputEditTextStrategy(paramContext);
            case TYPE_INPUT_NUMBER:
            case TYPE_LAT:
            case TYPE_LON:
                return new InputDecimalNumberStrategy(paramContext);
            case TYPE_SELECT:
                return new InputSelectStrategy(paramContext);
        }
    }
}
