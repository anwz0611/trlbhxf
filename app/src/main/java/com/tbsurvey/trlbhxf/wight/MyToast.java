package com.tbsurvey.trlbhxf.wight;

import android.widget.Toast;

import com.tbsurvey.trlbhxf.app.MyApplication;

import es.dmoral.toasty.Toasty;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :Toast
 */
public class MyToast {
    public static void  error(String msg){
        Toasty.error(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT, true).show();
    }
    public static void  success(String msg){
        Toasty.success(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT, true).show();
    }
    public static void  successLong(String msg){
        Toasty.success(MyApplication.getMyApplication(), msg, Toast.LENGTH_LONG, true).show();
    }
    public static void  info(String msg){
        Toasty.info(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT, true).show();
    }
    public static void  warning(String msg){
        Toasty.warning(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT, true).show();
    }
    public static void  longerror(String msg){
        Toasty.error(MyApplication.getMyApplication(), msg, Toast.LENGTH_LONG, true).show();
    }
}
