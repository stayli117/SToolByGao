package net.people.stoolui.modules.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.transfar.smarttda.bean.ActivityUIBean;
import com.transfar.smarttda.core.DataAgent;
import com.transfar.smarttda.tool.SPUtils;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIInfoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private UIRecycleViewAdapter adapter;
    private UIRecycleViewThresholdAdapter mUIRecycleViewThresholdAdapter;
    private List<Long> popwindow_list;
    private SPUtils mSpUtils;
    private long selected_threshold, threshold_min = 0L, threshold_max = 1000L;
    private int increment = 20;
    private List<ActivityUIBean> mActivityUIBeanList;
    private PopupWindow popupWindow;
    private TextView textViewLoadingthreshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_uiinfo);
        setCustomTitle(getResources().getString(R.string.stool_ui));
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    private void initData() {

        mSpUtils = new SPUtils(getApplicationContext(), getResources().getString(R.string.stool_app_name));
        /**初始化spinner列表数据**/
        popwindow_list = new ArrayList<>();
        long temp_threshold = threshold_min;
        while (temp_threshold <= threshold_max) {
            popwindow_list.add(temp_threshold);
            temp_threshold += increment;
        }
        /**初始化recycleView数据**/
        mActivityUIBeanList = new ArrayList<>();
        selected_threshold = mSpUtils.getLong("loading_threshold", popwindow_list.get(0));
        adapter = new UIRecycleViewAdapter(this, mActivityUIBeanList);
        mUIRecycleViewThresholdAdapter = new UIRecycleViewThresholdAdapter(this, popwindow_list);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_ui);
        textViewLoadingthreshold = (TextView) findViewById(R.id.text_ui_loading_threshold);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        textViewLoadingthreshold.setText(String.valueOf(selected_threshold));
        Drawable drawable = ContextCompat.getDrawable(UIInfoActivity.this, R.drawable.ic_down_arrow);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textViewLoadingthreshold.setCompoundDrawables(null, null, drawable, null); //分别对应 左上右下
    }

    private void initListener() {
        textViewLoadingthreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            }
        });

    }

    private void getPopupWindow() {
        if (null != popupWindow) {
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.alpha = 0.5f;
            this.getWindow().setAttributes(params);
        } else {
            initPopuptWindow();
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.alpha = 0.5f;
            this.getWindow().setAttributes(params);
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.stool_popwindow_ui_threshold, null, false);
        RecyclerView recyclerViewThreshold = (RecyclerView) popupWindow_view.findViewById(R.id.recycler_view_ui_threshold);
        recyclerViewThreshold.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewThreshold.setHasFixedSize(true);
        recyclerViewThreshold.setAdapter(mUIRecycleViewThresholdAdapter);
        recyclerViewThreshold.addItemDecoration(new SpaceItemDecoration(2));
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, 900, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = UIInfoActivity.this.getWindow().getAttributes();
                params.alpha = 1f;
                UIInfoActivity.this.getWindow().setAttributes(params);
            }
        });
    }

    private void refreshData() {
        for (int i = 0; i < popwindow_list.size(); i++) {
            if (selected_threshold == popwindow_list.get(i)) {
                selected_threshold = mSpUtils.getLong("loading_threshold", popwindow_list.get(i));
                break;
            }
        }
        mActivityUIBeanList.clear();
        List<ActivityUIBean> list = DataAgent.getInstance().getActivityUIBeanList();
        if (list != null && list.size() != 0) {
            for (ActivityUIBean activityUIBean : list) {
                try {
                    if (TextUtils.isDigitsOnly(activityUIBean.getShowUiPeriod()) && Long.parseLong(activityUIBean.getShowUiPeriod()) >= selected_threshold) {
                        //筛选完
                        mActivityUIBeanList.add(activityUIBean);
                    }
                } catch (Exception e) {
                    //activityUIBean.getShowUiPeriod()异常
                    e.printStackTrace();
                }
            }
            Collections.sort(mActivityUIBeanList);
        }
        adapter.notifyDataSetChanged();
    }

    public void setThreshold(String threshold) {
        textViewLoadingthreshold.setText(threshold);
        mSpUtils.putLong("loading_threshold", Long.parseLong(threshold));
        refreshData();
        popupWindow.dismiss();
    }
}
