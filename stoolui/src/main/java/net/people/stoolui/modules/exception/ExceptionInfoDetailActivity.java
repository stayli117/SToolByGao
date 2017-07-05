package net.people.stoolui.modules.exception;

import android.os.Bundle;
import android.widget.TextView;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;


/**
 * Title: ExceptionInfoDetailActivity$ <br> Description: <br> Copyright (c) 传化物流版权所有 2016 <br>
 * Created DateTime: 2017/1/4$ 15:20$ <br> Created by  nongwenxue.
 */
public class ExceptionInfoDetailActivity extends BaseActivity {

    TextView txtExceptionTime;
    TextView txtExceptionDetail;

    public static final String EXCEPTION_BEAN = "EXCEPTION_BEAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_exception_info_detail);
        setCustomTitle("异常详情");
        initView();
        initData();
    }

    private void initView() {
        txtExceptionTime = (TextView) findViewById(R.id.txt_exception_time_detail);
        txtExceptionDetail = (TextView) findViewById(R.id.txt_exception_detail);
        txtExceptionDetail.setTextIsSelectable(true);
    }

    private void initData() {
        ExceptionBean bean = (ExceptionBean) getIntent().getSerializableExtra(EXCEPTION_BEAN);
        if (bean != null){
            txtExceptionTime.setText(bean.getTime());
            txtExceptionDetail.setText(bean.getDetailMessage());
        }
    }
}
