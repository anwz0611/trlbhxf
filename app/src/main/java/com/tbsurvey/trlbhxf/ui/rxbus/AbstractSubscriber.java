package com.tbsurvey.trlbhxf.ui.rxbus;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author:jxj on 2021/8/24 13:51
 * e-mail:592296083@qq.com
 * desc  :
 */
abstract class AbstractSubscriber<T> implements Consumer<T>, Disposable {

    private volatile boolean disposed;

    @Override
    public void accept(T event) {
        try {
            acceptEvent(event);
        } catch (Exception e) {
//            throw new RuntimeException("Could not dispatch event: " + event.getClass(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        if (!disposed) {
            disposed = true;
            release();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    protected abstract void acceptEvent(T event) throws Exception;

    protected abstract void release();

}

