package net.people.stoolui.modules.base;

import android.os.Bundle;

import com.transfar.smarttda.core.DataAgent;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;
import net.people.stoolui.datas.impl.DataStore;


public class BaseInfoActivity extends BaseActivity {


    BaseInfoItemView viewBaseInfoAppName;
    BaseInfoItemView viewBaseInfoAppVersion;
    BaseInfoItemView viewBaseInfoDeviceName;
    BaseInfoItemView viewBaseInfoDeviceNumber;
    BaseInfoItemView viewBaseInfoDeviceOS;
    BaseInfoItemView viewBaseInfoDeviceOSSpecial;
    BaseInfoItemView viewBaseInfoAgent;
    BaseInfoItemView viewBaseInfoAgentVersion;
    BaseInfoItemView viewBaseInfoDeviceIMEI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_base_info);
        setCustomTitle("基本信息");
        initView();
        initData();
    }

    private void initView() {
        viewBaseInfoAppName = (BaseInfoItemView) findViewById(R.id.view_base_info_app_name);
        viewBaseInfoAppVersion = (BaseInfoItemView) findViewById(R.id.view_base_info_app_version);
        viewBaseInfoDeviceName = (BaseInfoItemView) findViewById(R.id.view_base_info_device_name);
        viewBaseInfoDeviceNumber = (BaseInfoItemView) findViewById(R.id.view_base_info_device_number);

        viewBaseInfoDeviceOS = (BaseInfoItemView) findViewById(R.id.view_base_info_device_os);
        viewBaseInfoDeviceOSSpecial = (BaseInfoItemView) findViewById(R.id.view_base_info_device_os_special);
        viewBaseInfoAgent = (BaseInfoItemView) findViewById(R.id.view_base_info_agent);
        viewBaseInfoAgentVersion = (BaseInfoItemView) findViewById(R.id.view_base_info_agent_version);
        viewBaseInfoDeviceIMEI = (BaseInfoItemView) findViewById(R.id.view_base_info_device_imei);
    }

    private void initData() {
        viewBaseInfoAppName.setBaseInfoText(getDataProxy().getAppName());
        viewBaseInfoAppVersion.setBaseInfoText(getDataProxy().getAppVersion());
        viewBaseInfoDeviceName.setBaseInfoText(getDataProxy().getPhoneName());
        viewBaseInfoDeviceNumber.setBaseInfoText(getDataProxy().getPhoneModel());

        viewBaseInfoDeviceOS.setBaseInfoText(getDataProxy().getOSType());
        viewBaseInfoDeviceOSSpecial.setBaseInfoText(getDataProxy().getCusOsType());
        viewBaseInfoAgent.setBaseInfoText(getDataProxy().getAgentType());
        viewBaseInfoAgentVersion.setBaseInfoText(getDataProxy().getAgentVersion());
        viewBaseInfoDeviceIMEI.setBaseInfoText(getDataProxy().getIMEI());
    }


    private DataAgent getDataProxy(){
        return DataStore.getProxy();
    }

}
