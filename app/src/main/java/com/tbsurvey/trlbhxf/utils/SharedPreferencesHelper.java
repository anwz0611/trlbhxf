package com.tbsurvey.trlbhxf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tbsurvey.trlbhxf.app.MyApplication;


import java.util.ArrayList;
import java.util.List;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :当前类注释:当前为SharedPerferences进行封装基本的方法,SharedPerferences已经封装成单例模式
 * 可以通过SharedPreferences sp=SharedPreferencesHelper.getInstances(FDApplication.getInstance())进行获取当前对象
 * sp.putStringValue(key,value)进行使用
 */
public class SharedPreferencesHelper {
    private static final String SHARED_PATH = "myeditdb_shared";
    private static SharedPreferencesHelper instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static SharedPreferencesHelper getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesHelper(MyApplication.getMyApplication());
        }
        return instance;
    }

    private SharedPreferencesHelper(Context context) {
        sp = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public long getLongValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getLong(key, 0);
        }
        return 0;
    }

    public String getStringValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getString(key, null);
        }
        return null;
    }

    public int getIntValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getInt(key, 0);
        }
        return 0;
    }

    public int getIntValueByDefault(String key) {
        if (key != null && !key.equals("")) {
            return sp.getInt(key, 0);
        }
        return 0;
    }

    public boolean getBooleanValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getBoolean(key, false);
        }
        return true;
    }

    public float getFloatValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getFloat(key, 0);
        }
        return 0;
    }

    public void putStringValue(String key, String value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public void putIntValue(String key, int value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public void putBooleanValue(String key, boolean value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public void putLongValue(String key, long value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    public void putFloatValue(String key, Float value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    /**
     * save json string of data list to share preference
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist)
            return;
        Gson gson = new Gson();
        //change datalist to json
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * get data List from share preferences
     *
     * @param tag share preferences data tag
     * @param cls target list element object class
     * @return list
     */
    public <T> List<T> getDataList(String tag, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Log.d("TAG", "getDataList, json:" + strJson);
        try {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (JsonElement jsonElement : array) {
                datalist.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception : " + e.getMessage());
        }
        return datalist;
    }

    /**
     * save json string of data to share preference
     *
     * @param tag
     * @param data object
     */
    public <T> void setData(String tag, T data) {
        if (null == data)
            return;

        Gson gson = new Gson();
        //change data to json
        String strJson = gson.toJson(data);
        Log.d("TAG", "setData, json:" + strJson);
        editor = sp.edit();
        editor.putString(tag, strJson);
        editor.commit();
    }
    /**
     * get data from share preferences
     * @param tag share preferences data tag
     * @param cls target object class
     * @return target object or null if error happyed
     */
    public <T> T getData(String tag, Class<T> cls) {
        T data = null;
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Log.d("TAG","getData, json:"+strJson);
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = new JsonParser().parse(strJson);
            data = gson.fromJson(jsonElement, cls);
        } catch (Exception e) {
            Log.e("TAG","Exception : "+e.getMessage());
        }
        return data;
    }


}
