package com.tbsurvey.trlbhxf.utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author:jxj on 2021/5/24 13:42
 * e-mail:592296083@qq.com
 * desc  :
 */
public class ThreadUtils {

    //主线程做操作
    public static void doOnUIThread(UITask uiTask){
        Observable.just(uiTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(UITask::doOnUI);

    }

    //io线程做操作
    public static void doOnThread(ThreadTask threadTask){
        Observable.just(threadTask)
                .observeOn(Schedulers.io())
                .subscribe(ThreadTask::doOnThread);

    }

    public interface ThreadTask{
        void doOnThread();
    }

    public interface UITask{
        void doOnUI();
    }
}

