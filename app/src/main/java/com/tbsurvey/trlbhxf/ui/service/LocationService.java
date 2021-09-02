package com.tbsurvey.trlbhxf.ui.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.orhanobut.logger.Logger;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.ui.rxbus.event.LocationInfoEvent;
import com.tbsurvey.trlbhxf.ui.activity.main.MainActivity;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.gps.GPSLocationListener;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.gps.GPSLocationManager;
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.gps.GPSProviderStatus;
import com.tbsurvey.trlbhxf.ui.rxbus.BusProvider;
import com.tbsurvey.trlbhxf.wight.MyToast;

public class LocationService extends Service {
    MyListener myListener = new MyListener();

    private Notification buildNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            @SuppressLint("WrongConstant") NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
            String str = getPackageName();
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(str, "BackgroundLocation", 3);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(getApplicationContext(), str);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity((Context) this, 0, new Intent((Context) this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setContentTitle(getString(R.string.app_name)).setContentText("正在后台定位").setContentIntent(pendingIntent).setWhen(System.currentTimeMillis());
        return (Build.VERSION.SDK_INT >= 16) ? builder.build() : builder.getNotification();
    }

    private void initLocation() {
        GPSLocationManager.getInstances(getApplicationContext()).start(myListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        @SuppressLint("WrongConstant") PowerManager powerManager = (PowerManager) getSystemService("power");
        return (powerManager != null) ? powerManager.isIgnoringBatteryOptimizations(getPackageName()) : false;
    }
    @Override
    public IBinder onBind(Intent paramIntent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (GPSLocationManager.getInstances(getApplicationContext()) != null) {
                GPSLocationManager.getInstances(getApplicationContext()).stop();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        stopForeground(true);
    }

    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        if (Build.VERSION.SDK_INT >= 23 && !isIgnoringBatteryOptimizations())
            requestIgnoreBatteryOptimizations();
        startForeground(2001, buildNotification());
        return super.onStartCommand(paramIntent, 1, paramInt2);
    }

    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(getPackageName());
            intent.setData(Uri.parse(stringBuilder.toString()));
            startActivity(intent);
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    class MyListener implements GPSLocationListener {

        @Override
        public void UpdateLocation(Location location) {
            if (location != null) {
                double lon = location.getLongitude();
                double lat = location.getLatitude();
//                double[] locex = EvilTransform.gcj02_To_Gps84(lon, lat);//坐标偏移
                Point pt_mapsr = new Point(lon, lat, SpatialReferences.getWgs84());
//                Logger.d(pt_mapsr.getX()+"  "+pt_mapsr.getY());
                BusProvider.getInstance().post(new LocationInfoEvent(pt_mapsr,location.getBearing(), location.getAccuracy()));
            }
        }

        @Override
        public void UpdateStatus(String provider, int status, Bundle extras) {
            if ("gps" == provider) {

            }

        }

        @Override
        public void UpdateGPSProviderStatus(int gpsStatus) {
            switch (gpsStatus) {
                case GPSProviderStatus.GPS_ENABLED:
                    MyToast.info("GPS开启");
                    break;
                case GPSProviderStatus.GPS_DISABLED:
                    MyToast.info("GPS关闭");
                    break;
                case GPSProviderStatus.GPS_OUT_OF_SERVICE:
                    MyToast.info("GPS不可用");
                    break;
                case GPSProviderStatus.GPS_TEMPORARILY_UNAVAILABLE:
                    MyToast.info("GPS暂时不可用");
                    break;
                case GPSProviderStatus.GPS_AVAILABLE:
                    MyToast.info("GPS可用");
                    break;
            }
        }
    }

}
