package com.tbsurvey.trlbhxf.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;


import com.arialyy.aria.BuildConfig;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.FileUtil;

import java.io.File;
import java.io.IOException;

import static android.service.controls.ControlsProviderService.TAG;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :跟App相关的辅助类
 */
public class AppUtils {
    private static final String ARIA_SHARE_PRE_KEY = "ARIA_SHARE_PRE_KEY";
    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 检查实体是否有效
     *
     * @return true 实体有效
     */
    public static boolean chekEntityValid(AbsEntity entity) {
        return entity != null && entity.getId() != -1;
    }

    /**
     * http下载示例代码
     */
    public static File getHelpCode(Context context, String fileName) throws IOException {
        String path = String.format("%s/code/%s", context.getFilesDir().getPath(), fileName);
        File ftpCode = new File(path);
        if (!ftpCode.exists()) {
            FileUtil.createFile(ftpCode);
            FileUtil.createFileFormInputStream(context.getAssets()
                            .open(String.format("help_code/%s", fileName)),
                    path);
        }
        return ftpCode;
    }

    /**
     * 读取配置文件字段
     *
     * @param key key
     * @param defStr 默认字符串
     */
    public static String getConfigValue(Context context, String key, String defStr) {
        SharedPreferences preferences =
                context.getSharedPreferences(ARIA_SHARE_PRE_KEY, Context.MODE_PRIVATE);
        return preferences.getString(key, defStr);
    }

    /**
     * set配置文件字段
     *
     * @param key key
     * @param value 需要保存的字符串
     */
    public static void setConfigValue(Context context, String key, String value) {
        SharedPreferences preferences =
                context.getSharedPreferences(ARIA_SHARE_PRE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 调用系统文件管理器选择文件
     *
     * @param file 不能是文件夹
     * @param mineType android 可用的minetype
     * @param requestCode 请求码
     */
    public static void chooseFile(Activity activity, File file, String mineType, int requestCode) {
        if (file.isDirectory()) {
            ALog.e(TAG, "不能选择文件夹");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity.getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }

        intent.setDataAndType(uri, TextUtils.isEmpty(mineType) ? "*/*" : mineType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.startActivityForResult(intent, requestCode);
    }
    public static boolean isSix() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ;
    }
}
