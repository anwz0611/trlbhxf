package com.tbsurvey.trlbhxf.ui.rxbus;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * author:jxj on 2021/8/24 13:55
 * e-mail:592296083@qq.com
 * desc  :
 */
class AnnotatedSubscriber<T> extends AbstractSubscriber<T> {

    private final int hashCode;

    private Object observer;
    private Method method;

    AnnotatedSubscriber(@NonNull Object observer, @NonNull Method method) {
        this.observer = observer;
        this.method = method;

        hashCode = 31 * observer.hashCode() + method.hashCode();
    }

    @Override
    protected void acceptEvent(T event) throws Exception {
        method.invoke(observer, event);
    }

    @Override
    protected void release() {
        observer = null;
        method = null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        AnnotatedSubscriber<?> that = (AnnotatedSubscriber<?>) other;

        return observer.equals(that.observer) && method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
