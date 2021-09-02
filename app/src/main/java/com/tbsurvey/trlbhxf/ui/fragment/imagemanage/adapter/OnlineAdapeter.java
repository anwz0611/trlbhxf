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
import com.tbsurvey.trlbhxf.ui.fragment.map.maphelps.arcmap.ArcMapTool;
import com.tbsurvey.trlbhxf.ui.rxbus.BusProvider;
import com.tbsurvey.trlbhxf.ui.rxbus.event.BottomLayerChange;
import com.tbsurvey.trlbhxf.utils.SharedPreferencesHelper;


import java.util.List;

/**
 * author:jxj on 2020/7/29 11:33
 * e-mail:592296083@qq.com
 * desc  :要素列表apapeter
 */
public class OnlineAdapeter<T> extends RecyclerView.Adapter<OnlineAdapeter.ViewHolder> {

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

        public ViewHolder(View view) {
            super(view);

            fa_text = (TextView) view.findViewById(R.id.zaixian_item_name);
            togbtn_text = (TextView) view.findViewById(R.id.togbtn_text);
            togbtn = (ToggleButton) view.findViewById(R.id.togbtn);
            LinearLayout_layeradapter = (LinearLayout) view.findViewById(R.id.zaixian_show);


        }

    }

    public OnlineAdapeter(Context context, List<NetOnlinMapBean> fruitList) {
        mFruitList = fruitList;
        this.context = context;


    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_online_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fa_text.setText(mFruitList.get(position).getName());
        holder.LinearLayout_layeradapter.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }

        });


        holder.togbtn.setOnCheckedChangeListener((compoundButton, b) -> {
          new Handler().post(() -> refresh(position, b));

            if (!b) {
                for (int i = 0; i < mFruitList.size(); i++) {
                    if (mFruitList.get(i).isCheck()) {
                        if (i==position){
                            if (position==0){
                                BusProvider.getInstance().post(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTYX, false,position));
                            }else if (position==1){
                                BusProvider.getInstance().post(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTSL, false,position));
                            }
                        }
                    }
                }
            } else {
                if (position==0){
                    BusProvider.getInstance().post(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTYX, true,position));
                    SharedPreferencesHelper.getInstance().putIntValue(" bottomChangeLayer",0);
                }else if (position==1){
                    SharedPreferencesHelper.getInstance().putIntValue(" bottomChangeLayer",1);
                    BusProvider.getInstance().post(new BottomLayerChange(ArcMapTool.BaseLayerType.TDTSL, true,position));
                }

            }
            holder.togbtn.setBackgroundDrawable(b ? context.getDrawable(R.mipmap.item_chakan) : context.getDrawable(R.mipmap.item_guanbi));
            holder.togbtn_text.setText(b ? "查看" : "关闭");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.togbtn_text.setTextColor(b ? R.color.gray : R.color.blue1);
            }
        });
        holder.togbtn.setChecked(mFruitList.get(position).isCheck());

    }

    private void refresh(int position, boolean b) {
        if (!b) {
            return;
        }
        for (int i = 0; i < mFruitList.size(); i++) {
            if (position == i) {
                mFruitList.get(i).setCheck(true);
            } else {
                mFruitList.get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFruitList == null ? 0 : mFruitList.size();
    }


}