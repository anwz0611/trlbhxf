package com.tbsurvey.trlbhxf.ui.fragment.froms;
import com.tbsurvey.trlbhxf.ui.base.mvp.BasePresenter;
/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public class DataCollectionPresenter extends BasePresenter<DataCollectionContract.Model, DataCollectionContract.View> {


    @Override
    protected DataCollectionContract.Model createModel() {
        return new DataCollectionModel();
    }

}
