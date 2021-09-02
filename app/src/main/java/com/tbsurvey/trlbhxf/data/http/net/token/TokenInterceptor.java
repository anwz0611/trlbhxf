package com.tbsurvey.trlbhxf.data.http.net.token;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.tbsurvey.trlbhxf.data.http.entity.BaseHttpResult;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Token失效的处理方案二，如果服务端没有遵循设计规范，可以尝试使用如下方法
 * 使用方法：addInterceptor(new TokenInterceptor());
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        /**
         * 通过如下方法提前获取到请求完成的数据
         */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);
        Log.d("body---------->", bodyString);
        BaseHttpResult baseHttpResult = new Gson().fromJson(bodyString, BaseHttpResult.class);

        if (baseHttpResult.getCode() == 10010) {// 根据和服务端的约定判断Token是否过期
//            // 通过一个特定的接口获取新的Token，此处要用到同步的Retrofit请求
            // 获取到新的Token
            String token = getNewToken();
//            SharedPreferencesHelper.getInstance().putStringValue("token", token);
//            CacheManager.putString("token", token);
            // create a new request and modify it accordingly using the new token
            Request newRequest = request.newBuilder().header("X-Access-Token", token).build();
            originalResponse.body().close();
            // retry the request
            return chain.proceed(newRequest);
        }

        // otherwise just pass the original response on
        return originalResponse;
    }


    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
//        Map<String, Object> rqMap = new HashMap<>();
//        rqMap.put("remember_me", true);
//        rqMap.put("username", SharedPreferencesHelper.getInstance().getStringValue("login_user"));
//        rqMap.put("password", base64(SharedPreferencesHelper.getInstance().getStringValue("login_pass")));
//        rqMap.put("flag", "mobile");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        LoginService loginService = retrofit.create(LoginService.class);
//        Call<BaseHttpResult<LoginBean>> call = loginService.Login(rqMap);//
//        retrofit2.Response<BaseHttpResult<LoginBean>> loginBeanBaseHttpResult = call.execute();
//        BaseHttpResult<LoginBean> loginBeanBaseHttpResult1=  loginBeanBaseHttpResult.body();
//        return loginBeanBaseHttpResult1.getResult().getToken();
        return null;
    }

}