package com.tbsurvey.trlbhxf.data.repository;


import com.tbsurvey.trlbhxf.app.MyApplication;
import com.tbsurvey.trlbhxf.data.entity.VuforiaImageTarget;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tbsurvey.trlbhxf.utils.StringUtils.readXML;

/**
 * author:jxj on 2021/7/2 16:58
 * e-mail:592296083@qq.com
 * desc  :
 */
public class ObtainCommon {
    private static ObtainCommon instance;

    public static ObtainCommon getInstance() {
        if (instance == null) {
            instance = new ObtainCommon();
        }
        return instance;
    }


    public Map<String, String> getShpFileXML() {
          HashMap<String, String> shpfile = new HashMap<>();
        List<VuforiaImageTarget> list = null;
        try {
            list = readXML(MyApplication.getMyApplication().getAssets().open("shpfile.xml"));
            for (int i = 0; i < list.size(); i++) {
                shpfile.put(list.get(i).name, list.get(i).value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shpfile;
    }
}
