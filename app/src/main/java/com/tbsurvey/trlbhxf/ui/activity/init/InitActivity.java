package com.tbsurvey.trlbhxf.ui.activity.init;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.activity.main.MainActivity;
import com.tbsurvey.trlbhxf.utils.AppUtils;
import com.tbsurvey.trlbhxf.utils.SharedPreferencesHelper;
import com.tbsurvey.trlbhxf.wight.MyToast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import static com.tbsurvey.trlbhxf.app.AppConfig.MYFILENAME;
import static com.tbsurvey.trlbhxf.utils.FileUtils.createDir;
import static com.tbsurvey.trlbhxf.utils.FileUtils.getSDCardPath;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :应用程序初始化页面
 */
@RuntimePermissions
public class InitActivity extends AppCompatActivity {

    private static String TAG = "InitActivity";
    private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟时间
    private Context context = null;
    private static final int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入存储
            Manifest.permission.ACCESS_FINE_LOCATION,//位置信息
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA //相机
    };
    private String ss="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_init);
        appInit();
        TextView textView = (TextView) this.findViewById(R.id.activity_init_versionTxt);
        String version = AppUtils.getVersionName(this);
        textView.setText("版本号:" + version);
    }

    @SuppressLint("CheckResult")
    private void toMain() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> startActivity());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity();
    }

    /**
     * 跳转
     */
    private void startActivity() {
        Intent mainIntent = new Intent(context, MainActivity.class);
        context.startActivity(mainIntent);
        ((Activity) context).finish();
    }

//    /**
//     * 跳转
//     */
//    private void startLogin() {
//        Intent mainIntent = new Intent(context, LoginActivity.class);
//        context.startActivity(mainIntent);
//        ((Activity) context).finish();
//    }

    /**
     * 应用程序初始化
     */

    public void appInit() {
//        boolean isOk = AppWorksSpaceInit.init(context);//初始化系统文件夹路径
//        startPermissionsActivity();
        InitActivityPermissionsDispatcher.dateWithCheck(this);

    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION})
    public void date() {
        SharedPreferences shared = getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer = shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor = shared.edit();
//        File file = new File(getSDCardPath()+"/"+getString(R.string.app_name)+"/数据库文件");
//        File file1 = new File(getSDCardPath()+"/"+getString(R.string.app_name) +"/数据库文件/"+ getString(R.string.test_db));
        if (isfer ) {
            //第一次进入跳转
            SharedPreferencesHelper.getInstance().putIntValue(" bottomChangeLayer",-1);
            SharedPreferencesHelper.getInstance().putStringValue("LayerId", "-1");
            editor.putBoolean("isfer", false);
            File file2 = new File(getSDCardPath()+"/"+getString(R.string.app_name)+"/方案模板");
            File file3 = new File(  getSDCardPath() + "/" + getString(R.string.app_name) + "/方案导出");
            File file4 = new File(   MYFILENAME + "/离线地图");
            File file5 = new File(MYFILENAME+"/方案");
            createDir(file2);
            createDir(file3);
            createDir(file4);
            createDir(file5);
            editor.commit();
        }
        toMain();
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION})
        //提示用户为何要开启权限
    void showWhy(final PermissionRequest request) {
        new AlertDialog.Builder(InitActivity.this)
                .setMessage("该应用需要这些权限，不开启将无法正常使用")
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    request.proceed(); //再次执行权限请求
                })
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void showNotAsk() {
        new AlertDialog.Builder(this)
                .setMessage("该应用需要这些权限，不开启将无法正常使用")
                .setPositiveButton("前去设置", (dialogInterface, i) -> {
                    //跳转到当前APP的设置页面
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(intent);
                })
                .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        InitActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
