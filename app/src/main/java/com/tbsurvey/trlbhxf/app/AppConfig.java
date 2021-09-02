package com.tbsurvey.trlbhxf.app;

import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.utils.FileUtils;

import static com.tbsurvey.trlbhxf.utils.StringUtils.getString;

/**
 * author:jxj on 2020/9/24 15:03
 * e-mail:592296083@qq.com
 * desc  :
 */
public class AppConfig {
    public static final String BASE_URL = "http://121.36.226.87/dologin/pcData/";
    public static final String MYFILENAME = FileUtils.getSDCardPath() + "/" + getString(R.string.app_name);//
}
