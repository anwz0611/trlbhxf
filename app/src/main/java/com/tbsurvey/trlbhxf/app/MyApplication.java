package com.tbsurvey.trlbhxf.app;

import android.os.Environment;

import androidx.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tbsurvey.trlbhxf.ui.activity.main.MainActivity;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.cache.CacheManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import es.dmoral.toasty.Toasty;
import io.reactivex.plugins.RxJavaPlugins;


/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :
 */
public class MyApplication extends MultiDexApplication {
    public static MyApplication Instance;
    private String APP_ID = "77a9690868";
    //是否 任务完成, 不再需要服务运行? 最好使用SharePreference，注意要在同一进程中访问该属性
    public static boolean isCanStartWorkService;
    @Override
    public void onCreate() {
        super.onCreate();
        this.Instance = this;
        initNothing();
    }

    private void initNothing() {
        CacheManager.init(this);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag(getString(R.string.app_name))                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        RxJavaPlugins.setErrorHandler(throwable -> {
            Logger.d(throwable.getMessage());
            throwable.printStackTrace();//这里处理所有的Rxjava异常
        });
        Toasty.Config.getInstance().allowQueue(false).apply();
        upgradeInit();
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);
            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            method.invoke(field.get(null));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public static MyApplication getMyApplication() {
        return Instance;
    }
    private void upgradeInit() {
        /**** Beta高级设置*****/
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        Beta.initDelay = 1 * 1000;

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        Beta.largeIconId = R.mipmap.ic_launcher;

        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;


        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher;

        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Bugly.init(getApplicationContext(), APP_ID, true);
    }


}