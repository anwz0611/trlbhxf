package com.tbsurvey.trlbhxf.ui.fragment.map;



import com.tbsurvey.trlbhxf.ui.base.mvp.IModel;
import com.tbsurvey.trlbhxf.ui.base.mvp.IView;

import io.reactivex.Observable;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :契约类
 */
public interface MapContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //显示loading
        void showLoading(String Msg);
//        <T> void QueryData(T strings);
    }

    interface Model extends IModel {


    }
}
