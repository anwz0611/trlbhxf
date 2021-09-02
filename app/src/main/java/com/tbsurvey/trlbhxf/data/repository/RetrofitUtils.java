package com.tbsurvey.trlbhxf.data.repository;


import com.tbsurvey.trlbhxf.data.http.api.ApiService;
import com.tbsurvey.trlbhxf.data.http.net.BaseRetrofit;

/**
 * author:jxj on 2020/9/24 15:17
 * e-mail:592296083@qq.com
 * desc  :网络请求管理类
 */
public class RetrofitUtils {
    private static ApiService httpService;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static ApiService getHttpService() {
        if (httpService == null) {
            httpService = BaseRetrofit.getRetrofit().create(ApiService.class);
        }
        return httpService;
    }
}
