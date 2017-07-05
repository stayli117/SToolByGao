package net.people.stoolui.modules.exception;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.transfar.smarttda.core.DataAgent;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;
import net.people.stoolui.datas.impl.DataStore;

import java.util.ArrayList;
import java.util.List;

public class ExceptionInfoActivity extends BaseActivity {


    RecyclerView recyclerView;

    ExceptionAdapter exceptionAdapter;
    private Button btn_del_all_exception;
    List<ExceptionBean> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_exception_info);
        setCustomTitle("异常信息");
        initView();

        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_del_all_exception= (Button) findViewById(R.id.btn_del_all_exception);

    }

    private void initData() {
        lists = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        exceptionAdapter = new ExceptionAdapter(lists);
        recyclerView.setAdapter(exceptionAdapter);
        btn_del_all_exception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataProxy().delAllException();
                getData();
                exceptionAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getData(){
        lists.clear();
        if (getDataProxy().getExceptionLists()==null||getDataProxy().getExceptionLists().size()==0){

        }
        int len = getDataProxy().getExceptionLists().size();
        //倒序取出数据
        for (int i = len - 1; i >= 0; i--) {
            ExceptionBean bean = new ExceptionBean();
            bean.setDetailMessage(getDataProxy().getExceptionLists().get(i).getDetailMessage());
            bean.setSimpleMessage(getDataProxy().getExceptionLists().get(i).getSimpleMessage());
            bean.setTime(getDataProxy().getExceptionLists().get(i).getTime());
            lists.add(bean);
        }
    }

    private DataAgent getDataProxy(){
        return DataStore.getProxy();
    }
}
