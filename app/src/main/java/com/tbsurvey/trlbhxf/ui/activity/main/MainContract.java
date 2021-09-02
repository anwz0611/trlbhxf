package com.tbsurvey.trlbhxf.ui.activity.main;

import com.tbsurvey.trlbhxf.ui.base.mvp.IModel;
import com.tbsurvey.trlbhxf.ui.base.mvp.IView;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :契约类
 */
public interface MainContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    interface Model extends IModel {

    }
}
