package com.tbsurvey.trlbhxf.ui.froms.enums;


import com.tbsurvey.trlbhxf.R;

/**
 * author:jxj on 2021/7/28 16:13
 * e-mail:592296083@qq.com
 * desc  :
 */
public enum InputTypeEnum {
    TYPE_INPUT_EDETTEXT(1, "input", R.layout.recycler_froms_input_edittext_item),
    TYPE_INPUT_NUMBER(2, "input", R.layout.recycler_froms_input_number_item),
    TYPE_INPUT_TEXT(3, "input", 2131492970),
    TYPE_SELECT(4, "select", R.layout.recycler_froms_input_select_item),
    TYPE_LAT(6, "lat", 2131492981),
    TYPE_LON(5, "lon", 2131492981);
    private int layoutResId;

    private int type;

    private String typeName;

    InputTypeEnum(int paramInt, String input, int layoutResId) {
        this.type = paramInt;
        this.typeName = input;
        this.layoutResId = layoutResId;
    }
    public static InputTypeEnum parse(String paramString) {
        for (InputTypeEnum inputTypeEnum : values()) {
            if (inputTypeEnum.getTypeName().equals(paramString))
                return inputTypeEnum;
        }
        return null;
    }

    public static InputTypeEnum valueType(int paramInt) {
        for (InputTypeEnum inputTypeEnum : values()) {
            if (paramInt == inputTypeEnum.type)
                return inputTypeEnum;
        }
        return null;
    }
    public int getLayoutResId() {
        return this.layoutResId;
    }

    public int getType() {
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
