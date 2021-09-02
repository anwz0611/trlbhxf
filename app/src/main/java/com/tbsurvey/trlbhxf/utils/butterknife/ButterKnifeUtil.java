package com.tbsurvey.trlbhxf.utils.butterknife;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author:jxj on 2020/9/23 11:22
 * e-mail:592296083@qq.com
 * desc  :
 */
public class ButterKnifeUtil {
    private static final String TAG = "ButterKnifeUtil";

    public static void bind(@NonNull Object target, @NonNull View source) {
        LifeCycleHolder.handle(ActivityUtil.getActivity(source.getContext()), ButterKnife.bind(target, source), new LifeCycleHolder.Callback<Unbinder>() {
            @Override
            public void onDestroy(@Nullable Unbinder obj) {

                try {
                    if (obj != null) {
                        obj.unbind();
                    }
                } catch (Exception e) {

                }
            }
        });
    }
}
