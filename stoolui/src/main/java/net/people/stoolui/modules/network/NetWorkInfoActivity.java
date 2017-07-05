package net.people.stoolui.modules.network;

import android.os.Bundle;
import android.widget.ListView;

import com.transfar.smarttda.bean.HttpFull;
import com.transfar.smarttda.core.DataAgent;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetWorkInfoActivity extends BaseActivity {

    private List<HttpFull> mData;
    private ListView mListView;
    private NetWorkViewHolderAdapter mNetWorkViewHolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_net_work_info);
        setCustomTitle(getResources().getString(R.string.stool_network_request));
        initData();
        initView();
        initListener();
    }

    private void initData() {
        mData = DataAgent.getInstance().getFullHttps();
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            Collections.sort(mData);
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mNetWorkViewHolderAdapter = new NetWorkViewHolderAdapter(this, mData);
        mListView.setAdapter(mNetWorkViewHolderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNetWorkViewHolderAdapter.notifyDataSetChanged();
    }

    private void initListener() {

    }

}
