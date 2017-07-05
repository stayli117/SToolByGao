package net.people.stoolui.modules.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transfar.smarttda.bean.HttpFull;

import net.people.stoolui.R;

import java.util.List;

/**
 * Title: NetWorkViewHolderAdapter <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/12/28 16:01
 * Created by Wentao.Shi.
 */
public class NetWorkViewHolderAdapter extends BaseAdapter {
    private List<HttpFull> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public NetWorkViewHolderAdapter(Context context, List<HttpFull> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HttpFull getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.stool_item_network_info, null);
            holder.mTextViewURL = (TextView) convertView.findViewById(R.id.text_url);
            holder.mTextViewStartTime = (TextView) convertView.findViewById(R.id.text_start_time);
            holder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.llayout_network_info_item);
            holder.mTextViewURLHead = (TextView) convertView.findViewById(R.id.text_url_head);
            holder.mTextViewStartTimeHead = (TextView) convertView.findViewById(R.id.text_start_time_head);
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NetWorkDetailActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextViewURL.setText(mData.get(position).getName());
        holder.mTextViewStartTime.setText(mData.get(position).getStartTime());
        holder.haveError(!TextUtils.isEmpty(mData.get(position).getErrDes()));
        return convertView;
    }

    public final class ViewHolder {
        public LinearLayout mLinearLayout;
        public TextView mTextViewURL;
        public TextView mTextViewStartTime;
        public TextView mTextViewURLHead;
        public TextView mTextViewStartTimeHead;

        /**
         * 设置有error与无error时列表的颜色
         *
         * @param haveError
         */
        public void haveError(boolean haveError) {
            if (haveError) {
                mTextViewURLHead.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
                mTextViewURL.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
                mTextViewStartTimeHead.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
                mTextViewStartTime.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
            } else {
                mTextViewURLHead.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_green_259B24));
                mTextViewURL.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_green_259B24));
                mTextViewStartTimeHead.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_green_259B24));
                mTextViewStartTime.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_green_259B24));
            }
        }
    }
}
