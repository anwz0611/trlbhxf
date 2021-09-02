package com.tbsurvey.trlbhxf.ui.fragment.imagemanage;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.entity.NetOnlinMapBean;
import com.tbsurvey.trlbhxf.ui.base.BaseFragment;
import com.tbsurvey.trlbhxf.ui.fragment.imagemanage.adapter.LocalAdapeter;
import com.tbsurvey.trlbhxf.ui.rxbus.event.OfflineMaps;
import com.tbsurvey.trlbhxf.utils.FileUtils;
import com.tbsurvey.trlbhxf.utils.SharedPreferencesHelper;
import com.tbsurvey.trlbhxf.wight.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.tbsurvey.trlbhxf.app.AppConfig.MYFILENAME;
import static com.tbsurvey.trlbhxf.utils.FileUtils.getFileNameNoEx;
import static com.tbsurvey.trlbhxf.utils.FileUtils.getFilesAzdbAllName;

/**
 * author:jxj on 2020/8/24 11:33
 * e-mail:592296083@qq.com
 * desc  :要素列表
 */
public class LocalImgFragment<T> extends BaseFragment {
    int REQUESTCODE_FROM_ACTIVITY = 1000;
    public static String LISTLAYERTAG = "ListLayerFragment";
    @BindView(R.id.rv_lixian)
    RecyclerView rv_lixian; //在线列表
    private LocalAdapeter localAdapeter;
    private List<NetOnlinMapBean> list = new ArrayList<>();
    private String path;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_img;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv_lixian.setLayoutManager(layoutManager);
        localAdapeter = new LocalAdapeter(getActivity(), list);
        rv_lixian.setAdapter(localAdapeter);


    }

    private void initData() {
        list.clear();
        String path = MYFILENAME + "/离线地图";
        List<String> strings = getFilesAzdbAllName(path);
        OfflineMaps offlineMaps= SharedPreferencesHelper.getInstance().getData("OfflineMaps", OfflineMaps.class);
//        if (offlineMaps!=null){
//            EventBus.getDefault().post(new EventMessage<>(EventCode.EVENT_J,offlineMaps));
//        }
        if (strings == null) {
            return;
        }
        for (int i = 0; i < strings.size(); i++) {

            NetOnlinMapBean netOnlinMapBean = new NetOnlinMapBean();
            netOnlinMapBean.setName(getFileNameNoEx(strings.get(i)));
            netOnlinMapBean.setCheck(false);
            if (offlineMaps!=null&&offlineMaps.getPaths().size()>0){
                for (int j = 0; j <offlineMaps.getPaths().size() ; j++) {
                    if (offlineMaps.getPaths().get(j).contains(netOnlinMapBean.getName())){
                        netOnlinMapBean.setCheck(true);
                        break;
                    }
                }
            }
            list.add(netOnlinMapBean);
        }

    }

    @Override
    protected void initListener() {
        localAdapeter.setOnItemClickListener(position -> {

            String dbPath = MYFILENAME + "/离线地图/" +  list.get(position).getName() + ".azdb";
            if (FileUtils.deleteFile(dbPath)){
                MyToast.success("删除文件成功");
            }else {
                MyToast.error("删除文件失败");
            }
           initData();
            localAdapeter.notifyDataSetChanged();
        });
    }

    @OnClick({R.id.local_add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_add_btn:
                showSelect();
                break;
            default:
                break;
        }

    }
    public void showSelect() {
        new LFilePicker()
                .withActivity(getActivity())
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withTitle(context.getString(R.string.main_lxsjy))
                .withStartPath("/storage/emulated/0")
                .withIconStyle(Constant.ICON_STYLE_GREEN)
                .withMutilyMode(false)
                .withMaxNum(1)
                .withFileFilter(new String[]{".azdb"})
                .withBackgroundColor("#62B19B")
                .withIsGreater(false)
                .withFileSize(500 * 1024)
                .start();
    }
    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                //如果是文件选择模式，需要获取选择的所有文件的路径集合
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                //如果是文件夹选择模式，需要获取选择的文件夹路径
                 path = data.getStringExtra("path");

            }
        }

    }
}
