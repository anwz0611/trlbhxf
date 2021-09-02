package com.tbsurvey.trlbhxf.ui.rxbus;

import androidx.annotation.NonNull;

import io.reactivex.functions.Consumer;

/**
 * author:jxj on 2021/8/24 13:48
 * e-mail:592296083@qq.com
 * desc  :
 */
public interface Bus {

    void register(@NonNull Object observer);

    <T> CustomSubscriber<T> obtainSubscriber(@NonNull Class<T> eventClass, @NonNull Consumer<T> receiver);

    <T> void registerSubscriber(@NonNull Object observer, @NonNull CustomSubscriber<T> subscriber);

    void unregister(@NonNull Object observer);

    void post(@NonNull Object event);

}
