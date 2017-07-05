package net.people.stoolui.modules.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.people.stoolui.R;

import java.util.List;

/**
 * Title: UIRecycleViewAdapter <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2017 <br>
 * Created DateTime: 2017-1-9 14:08
 * Created by Wentao.Shi.
 */
public class UIRecycleViewThresholdAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    public List<Long> list;
    private Context mContext;

    public UIRecycleViewThresholdAdapter(Context context, List<Long> list) {
        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stool_item_popwindow_ui_threshold, parent, false);

        return new UIRecycleViewThresholdHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((UIRecycleViewThresholdHolder) holder).text_item.setText(String.valueOf(list.get(position)));
        ((UIRecycleViewThresholdHolder) holder).text_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UIInfoActivity) mContext).setThreshold(String.valueOf(list.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class UIRecycleViewThresholdHolder extends RecyclerView.ViewHolder {
        TextView text_item;

        public UIRecycleViewThresholdHolder(View view) {
            super(view);
            text_item = (TextView) view.findViewById(R.id.text_ui_threshold_item);
        }
    }
}
