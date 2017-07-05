package net.people.stoolui.modules.network;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.people.stoolui.R;


/**
 * Title: ItemNetWorkInfo <br>
 * Description: <br>
 * Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2016/12/28 9:46
 * Created by Wentao.Shi.
 */
public class NetWorkInfoItemView extends LinearLayout {

    private View mV;
    private TextView mText_url_head;
    private TextView mText_url;
    private TextView mText_start_time_head;
    private TextView mText_start_time;
    private Context mContext;

    public NetWorkInfoItemView(Context context) {
        super(context);
        mContext = context;
    }

    public NetWorkInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public NetWorkInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 导入布局
        mV = LayoutInflater.from(context).inflate(R.layout.stool_item_network_info, this, true);
        mText_url_head = (TextView) mV.findViewById(R.id.text_url_head);
        mText_url = (TextView) mV.findViewById(R.id.text_url);
        mText_start_time_head = (TextView) mV.findViewById(R.id.text_start_time_head);
        mText_start_time = (TextView) mV.findViewById(R.id.text_start_time);
        mText_start_time.setTextColor(ContextCompat.getColor(context, R.color.stool_text_green_259B24));
        mText_start_time_head.setTextColor(ContextCompat.getColor(context, R.color.stool_text_green_259B24));
        mText_url_head.setTextColor(ContextCompat.getColor(context, R.color.stool_text_green_259B24));
        mText_url.setTextColor(ContextCompat.getColor(context, R.color.stool_text_green_259B24));
    }

    /**
     * 如果该条网络信息含有错误信息，将字体颜色设为红色
     */
    public void have_err() {
        mText_start_time.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
        mText_start_time_head.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
        mText_url_head.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
        mText_url.setTextColor(ContextCompat.getColor(mContext, R.color.stool_text_red_E51C23));
    }

    /**
     * 设置请求URL
     *
     * @param text_url 请求URL
     */
    public void setText_url(String text_url) {
        mText_url.setText(text_url);
    }

    /**
     * 设置请求时间
     *
     * @param text_start_time 请求时间
     */
    public void setText_start_time(String text_start_time) {
        mText_start_time.setText(text_start_time);
    }
}
