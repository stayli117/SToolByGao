package net.people.stoolui.modules.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transfar.smarttda.bean.HttpFull;
import com.transfar.smarttda.core.DataAgent;
import com.transfar.smarttda.tool.StringUtils;
import com.transfar.smarttda.tool.TimeUtils;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;
import net.people.stoolui.tool.StringFormatUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class NetWorkDetailActivity extends BaseActivity {
    private HttpFull mHttpFull;
    private TextView mTextViewResponseContent, mTextViewErrorContent, mTextViewRequsetName, mTextViewRequestTime, mTextViewRequestEndTime,
            mTextViewProtocolType, mTextViewRequestMode, mTextViewRequestContentHint, mTextViewRequestHead;
    private LinearLayout mLinearLayoutResponse, mLinearLayoutException;
    private Button btn_resend;
    private ImageView mImageViewFoldExpand;
    private int position = -1;
    private boolean haveerr, haveresponse;
    private OkHttpClient mOkHttpClient;
    private static final int MSG_REFREASH_DATA = 99;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REFREASH_DATA:
                    refreshData();
                    Toast.makeText(NetWorkDetailActivity.this, getResources().getString(R.string.stool_resend_finish), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_net_work_detail);
        setCustomTitle(getResources().getString(R.string.stool_network_detail));
        initData();
        initView();
        initListener();
    }

    private void initData() {
        haveerr = false;
        haveresponse = false;
        position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            mHttpFull = (HttpFull) DataAgent.getInstance().getFullHttps().get(position).clone();
            haveerr = !TextUtils.isEmpty(mHttpFull.getErrDes());
            haveresponse = !TextUtils.isEmpty(mHttpFull.getContent());
        }
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }


    private void initView() {
        mLinearLayoutResponse = (LinearLayout) findViewById(R.id.llayout_response);
        mLinearLayoutException = (LinearLayout) findViewById(R.id.llayout_exception);
        mTextViewResponseContent = (TextView) findViewById(R.id.text_response_content);
        mTextViewErrorContent = (TextView) findViewById(R.id.text_err_content);
        mTextViewRequsetName = (TextView) findViewById(R.id.text_request_name);
        mTextViewRequestTime = (TextView) findViewById(R.id.text_request_start_time);
        mTextViewRequestEndTime = (TextView) findViewById(R.id.text_request_end_time);
        mTextViewProtocolType = (TextView) findViewById(R.id.text_protocol_type);
        mTextViewRequestMode = (TextView) findViewById(R.id.text_request_mode);
        mImageViewFoldExpand = (ImageView) findViewById(R.id.img_fold_expand);
        mTextViewRequestContentHint = (TextView) findViewById(R.id.text_response_content_hint);
        mTextViewRequestHead = (TextView) findViewById(R.id.text_response_head);
        btn_resend = (Button) findViewById(R.id.btn_resend);
        refreshData();
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHttpFull();
                mHttpFull.setStartTime(TimeUtils.getThreadLocalCurrentTimeStr());
                mOkHttpClient.newCall(mHttpFull.getRequest()).enqueue(new Callback() {


                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHttpFull.setName(call.request().url().toString());
                        mHttpFull.setErrDes(StringUtils.getExceptionString(e));
                        mHandler.sendEmptyMessage(MSG_REFREASH_DATA);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mHttpFull.setName(call.request().url().toString());
                        mHttpFull.setEndTime(TimeUtils.getThreadLocalCurrentTimeStr());
                        mHttpFull.setProtocolType(response.protocol().name());
                        mHttpFull.setRequestType(response.request().method());
                        mHttpFull.setCode(String.valueOf(response.code()));
                        mHttpFull.setContentLength(String.valueOf(response.body().contentLength()));
                        mHttpFull.setContent(response.body().string());
                        mHandler.sendEmptyMessage(MSG_REFREASH_DATA);
                    }
                });
            }
        });
        mTextViewRequestHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextViewResponseContent.getVisibility() == View.VISIBLE) {
                    mTextViewResponseContent.setVisibility(View.GONE);
                    mImageViewFoldExpand.setImageDrawable(ContextCompat.getDrawable(NetWorkDetailActivity.this, R.drawable.ic_expand));
                    mTextViewRequestContentHint.setText(R.string.stool_response_content_hint_fold);
                } else {
                    mTextViewResponseContent.setVisibility(View.VISIBLE);
                    mImageViewFoldExpand.setImageDrawable(ContextCompat.getDrawable(NetWorkDetailActivity.this, R.drawable.ic_fold));
                    mTextViewRequestContentHint.setText(R.string.stool_response_content_hint_expand);
                }
            }
        });
        //防止开始时scrollview滚动到中间
        mTextViewRequsetName.setFocusable(true);
        mTextViewRequsetName.setFocusableInTouchMode(true);
        mTextViewRequsetName.requestFocus();
    }

    private void clearHttpFull() {
        mHttpFull.setContent(null);
        mHttpFull.setErrDes(null);
    }

    private void refreshData() {
        haveerr = !TextUtils.isEmpty(mHttpFull.getErrDes());
        haveresponse = !TextUtils.isEmpty(mHttpFull.getContent());
        String name = getResources().getString(R.string.stool_url) + mHttpFull.getName();
        String start_time = getResources().getString(R.string.stool_start_time) + mHttpFull.getStartTime();
        mTextViewRequsetName.setText(name);
        mTextViewRequestTime.setText(start_time);
        if (haveresponse) {
            mLinearLayoutResponse.setVisibility(View.VISIBLE);
            String end_time = getResources().getString(R.string.stool_end_time) + mHttpFull.getEndTime();
            String protocol_type = getResources().getString(R.string.stool_protocol_type) + mHttpFull.getProtocolType();
            String request_type = getResources().getString(R.string.stool_request_type) + mHttpFull.getRequestType();
            mTextViewRequestEndTime.setText(end_time);
            mTextViewProtocolType.setText(protocol_type);
            mTextViewRequestMode.setText(request_type);
            String orginText = mHttpFull.getContent();
            if (orginText.length() > 20000) {
                mTextViewResponseContent.setText(orginText.substring(0, 20000) + "......");
            } else {
                String textViewResponseContentText = StringFormatUtils.getFormatedString(orginText, 80);
                mTextViewResponseContent.setText(textViewResponseContentText);
            }

        } else {
            mLinearLayoutResponse.setVisibility(View.GONE);
        }
        if (haveerr) {
            mLinearLayoutException.setVisibility(View.VISIBLE);
            mTextViewErrorContent.setText(StringFormatUtils.getFormatedErrString(mHttpFull.getErrDes()));
        } else {
            mLinearLayoutException.setVisibility(View.GONE);
        }
    }

    private void initListener() {

    }

}
