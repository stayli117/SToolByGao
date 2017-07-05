package net.people.stoolui.modules.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transfar.smarttda.bean.ActivityUIBean;

import net.people.stoolui.R;

import java.util.List;

/**
 * Title: UIRecycleViewAdapter <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2017 <br>
 * Created DateTime: 2017-1-9 14:08
 * Created by Wentao.Shi.
 */
public class UIRecycleViewAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    public List<ActivityUIBean> list;
    private Context mContext;

    public UIRecycleViewAdapter(Context context, List<ActivityUIBean> list) {
        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stool_item_ui_info, parent, false);

        return new UIRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((UIRecycleViewHolder) holder).text_page.setText(list.get(position).getName());
        ((UIRecycleViewHolder) holder).text_start_time.setText(list.get(position).getOnCreateTime());
        ((UIRecycleViewHolder) holder).text_end_time.setText(list.get(position).getOnResumeTime());
        ((UIRecycleViewHolder) holder).text_time_use.setText(list.get(position).getShowUiPeriod() + " ms");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class UIRecycleViewHolder extends RecyclerView.ViewHolder {
        TextView text_page, text_start_time, text_end_time, text_time_use;

        public UIRecycleViewHolder(View view) {
            super(view);
            text_page = (TextView) view.findViewById(R.id.text_ui_page);
            text_start_time = (TextView) view.findViewById(R.id.text_ui_start_time);
            text_end_time = (TextView) view.findViewById(R.id.text_ui_end_time);
            text_time_use = (TextView) view.findViewById(R.id.text_ui_time_use);
        }
    }
}
