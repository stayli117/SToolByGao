package net.people.stoolui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;
import net.people.stoolui.modules.base.BaseInfoActivity;
import net.people.stoolui.modules.cpu.CpuInfoActivity;
import net.people.stoolui.modules.exception.ExceptionInfoActivity;
import net.people.stoolui.modules.network.NetWorkInfoActivity;
import net.people.stoolui.modules.ui.UIInfoActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLlayout_baseinfo;
    private RelativeLayout mRlayout_exception_info;
    private LinearLayout mLlayout_electric;
    private RelativeLayout mRlayout_network_request;
    private RelativeLayout mRlayout_ui;
    private LinearLayout mLlayout_cpu_memory;
    private static boolean permissionflag = false, firstflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.stool_activity_main);
//        //通知栏提示
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification;
//        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                MainActivity.this, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            Notification.Builder builder = new Notification.Builder(MainActivity.this);
//            builder.setContentIntent(pendingIntent);
//            builder.setLargeIcon(((BitmapDrawable)ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_ball)).getBitmap());
//            builder.setSmallIcon(R.drawable.ic_ball);// 设置图标
//            builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
//            builder.setContentTitle(getResources().getString(R.string.stool_notification_title));// 设置通知的标题
//            builder.setContentText(getResources().getString(R.string.stool_notification_content));// 设置通知的内容
//            builder.setOngoing(true);
//            notification = builder.build();
//
//        } else {
//            notification = new Notification(R.drawable.ic_ball, "smart", System.currentTimeMillis());
//        }
//        mNotificationManager.notify(0, notification);
//        //
        initView();
        initListener();
        getSupportActionBar().hide();
        Toast.makeText(this, "enter " + intent.toString(), Toast.LENGTH_SHORT).show();

    }

    private void initView() {
        String string = getResources().getString(R.string.stool_app_name);
        setCustomTitle(string);
        mLlayout_baseinfo = (LinearLayout) findViewById(R.id.llayout_baseinfo);
        mRlayout_exception_info = (RelativeLayout) findViewById(R.id.rlayout_exception_info);
        mLlayout_electric = (LinearLayout) findViewById(R.id.llayout_electric);
        mRlayout_network_request = (RelativeLayout) findViewById(R.id.rlayout_network_request);
        mRlayout_ui = (RelativeLayout) findViewById(R.id.rlayout_ui);
        mLlayout_cpu_memory = (LinearLayout) findViewById(R.id.llayout_cpu_memory);
        requestPremission();
    }

    private void initListener() {
        mRlayout_network_request.setOnClickListener(this);
        mRlayout_ui.setOnClickListener(this);
        mLlayout_baseinfo.setOnClickListener(this);
        mRlayout_exception_info.setOnClickListener(this);
        mLlayout_cpu_memory.setOnClickListener(this);
        mLlayout_electric.setOnClickListener(this);
    }


    /**
     * 请求权限
     */
    void requestPremission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    99);
            firstflag = false;
        } else {
            permissionflag = true;
//            if (firstflag) {
//                finish();
//            }
            firstflag = false;
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int itemId = v.getId();
        if (itemId == R.id.rlayout_network_request) {
            intent = new Intent(MainActivity.this, NetWorkInfoActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.rlayout_ui) {
            intent = new Intent(MainActivity.this, UIInfoActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.llayout_baseinfo) {
            intent = new Intent(MainActivity.this, BaseInfoActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.rlayout_exception_info) {
            intent = new Intent(MainActivity.this, ExceptionInfoActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.llayout_cpu_memory) {
            intent = new Intent(MainActivity.this, CpuInfoActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.llayout_electric) {
            dialog();
        }
    }

    /**
     * 弹窗提示用户在有可能不存在电量统计的rom
     */

    private void dialog() {

        new AlertDialog.Builder(this)
                .setTitle("确认")
                .setMessage(getResources().getString(R.string.stool_electric_tip_message))
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppInfos();
                    }
                })
                .setNegativeButton("否", null)
                .show();


    }


    /**
     * 打开应用信息列表
     */
    public void startAppInfos() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
        startActivity(intent);
    }
}
