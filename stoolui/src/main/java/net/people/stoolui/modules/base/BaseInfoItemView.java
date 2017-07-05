package net.people.stoolui.modules.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.people.stoolui.R;


/**
 * Title: BaseInfoItemView$ <br> Description: <br> Copyright (c) 传化物流版权所有 2016 <br> Created
 * DateTime: 2017/1/3$ 14:18$ <br> Created by  nongwenxue.
 */
public class BaseInfoItemView extends LinearLayout {

    TextView txtViewBaseInfo;
    TextView txtLabelViewBaseInfo;

    String labelBaseInfoText;
    String baseInfoText;


    public BaseInfoItemView(Context context) {
        super(context);
        initView(context);
    }

    public BaseInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //加载自定义的属性
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.stool_base_info);
        labelBaseInfoText=a.getString(R.styleable.stool_base_info_stool_label_base_info_text);
        baseInfoText=a.getString(R.styleable.stool_base_info_stool_base_info_text);
        //回收资源，这一句必须调用
        a.recycle();
        initView(context);
    }

    public BaseInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.stool_item_base_info,this);
        txtLabelViewBaseInfo = (TextView) view.findViewById(R.id.txt_label_app_info);
        txtViewBaseInfo = (TextView) view.findViewById(R.id.txt_app_info);
        txtLabelViewBaseInfo.setText(labelBaseInfoText);
        txtViewBaseInfo.setText(baseInfoText);
    }


    public void setBaseInfoText(String text){
        txtViewBaseInfo.setText(text);
    }

}
