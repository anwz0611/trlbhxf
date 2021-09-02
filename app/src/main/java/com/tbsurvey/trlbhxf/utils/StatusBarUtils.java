package com.tbsurvey.trlbhxf.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * author:jxj on 2021/8/24 15:11
 * e-mail:592296083@qq.com
 * desc  :
 */
public class StatusBarUtils {
    /**
     * 设置状态栏颜色
     *
     * @param activity
     */
    public static void setStatusColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP) {
            //直接调用系统提供的方法 setStatusBarColor
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT  <= Build.VERSION_CODES.KITKAT) {
            // 4.4 - 5.0 之间 采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusbarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
            viewGroup.addView(view);
            // 获取activity中setContentView布局的根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View activityView = contentView.getChildAt(0);
            activityView.setFitsSystemWindows(true);
        }
    }

    private static int getStatusbarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(statusHeightId);
    }
    public static void setActivityTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT  <= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
