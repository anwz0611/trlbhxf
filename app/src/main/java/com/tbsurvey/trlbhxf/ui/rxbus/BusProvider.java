package com.tbsurvey.trlbhxf.ui.rxbus;

/**
 * author:jxj on 2021/8/24 13:52
 * e-mail:592296083@qq.com
 * desc  :
 */
public final class BusProvider {

    private BusProvider() {
    }

    public static Bus getInstance() {
        return BusHolder.INSTANCE;
    }

    private static final class BusHolder {
        final static Bus INSTANCE = new RxBus();
    }
}
