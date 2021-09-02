package com.tbsurvey.trlbhxf.ui.fragment.imagemanage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.tbsurvey.trlbhxf.R;
import com.tbsurvey.trlbhxf.data.entity.NetOnlinMapBean;
import com.tbsurvey.trlbhxf.ui.rxbus.event.OfflineMaps;

import java.util.ArrayList;
import java.util.List;

import static com.tbsurvey.trlbhxf.app.AppConfig.MYFILENAME;


/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :要素列表apapeter
 */
public class LocalAdapeter<T> extends RecyclerView.Adapter<LocalAdapeter.ViewHolder> {

    private List<NetOnlinMapBean> mFruitList;
    Context context;
    // 利用接口 -> 给RecyclerView设置点击事件
    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fa_text;
        LinearLayout LinearLayout_layeradapter;
        ToggleButton togbtn;
        TextView togbtn_text;
        LinearLayout local_delete;
        public ViewHolder(View view) {
            super(view);

            fa_text = (TextView) view.findViewById(R.id.local_item_name);
            togbtn_text = (TextView) view.findViewById(R.id.togbtn_text);
            togbtn = (ToggleButton) view.findViewById(R.id.togbtn);
            LinearLayout_layeradapter = (LinearLayout) view.findViewById(R.id.local_show);
            local_delete= (LinearLayout) view.findViewById(R.id.local_delete);

        }

    }

    public LocalAdapeter(Context context, List<NetOnlinMapBean> fruitList) {
        mFruitList = fruitList;
        this.context = context;


    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_local_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fa_text.setText(mFruitList.get(position).getName());
        holder.local_delete.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }

        });

        holder.togbtn.setChecked(mFruitList.get(position).isCheck());
        if (mFruitList.get(position).isCheck()){
            holder.togbtn.setBackgroundDrawable(mFruitList.get(position).isCheck() ? context.getDrawable(R.mipmap.item_chakan) : context.getDrawable(R.mipmap.item_guanbi));
            holder.togbtn_text.setText(mFruitList.get(position).isCheck() ? "查看" : "关闭");
        }
        holder.togbtn.setOnCheckedChangeListener((compoundButton, b) -> {
          new Handler().post(() -> refresh(position, b));
            holder.togbtn.setBackgroundDrawable(b ? context.getDrawable(R.mipmap.item_chakan) : context.getDrawable(R.mipmap.item_guanbi));
            holder.togbtn_text.setText(b ? "查看" : "关闭");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.togbtn_text.setTextColor(b ? R.color.gray : R.color.blue1);
            }
        });


    }

    private void refresh(int position, boolean b) {
        mFruitList.get(position).setCheck(b);
//        notifyDataSetChanged();
//        EventBus.getDefault().post(new EventMessage<>(EventCode.EVENT_J, new OfflineMaps(getChecks())));
    }

    @Override
    public int getItemCount() {
        return mFruitList == null ? 0 : mFruitList.size();
    }
    private List<String> getChecks() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mFruitList.size(); i++) {
            if (mFruitList.get(i).isCheck()) {
                list.add(MYFILENAME + "/离线地图/" +mFruitList.get(i).getName()+".azdb");
            }
        }

        return list;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}