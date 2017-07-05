package net.people.stoolui.modules.cpu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import net.people.stoolui.R;
import net.people.stoolui.base.BaseActivity;
import net.people.stoolui.datas.impl.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import static net.people.stoolui.R.id.percent_app_used;
import static net.people.stoolui.R.id.percent_used;


public class CpuInfoActivity extends BaseActivity implements PercentLemon.OnHeartClickListener{

    private static final String TAG = "xxx";


    TextView txtAppName;
    PercentLemon percertUsed;
    PercentLemon percertAPPUsed;

    TextView txtTotalMemory;
    TextView txtUsedMemory;
    TextView txtUnusedMemory;
    TextView txtPercentUsedAndTotal;
    TextView txtAppMemory;
    TextView txtAppMemoryPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stool_activity_cpu_info);
        setCustomTitle("内存");
        initView();

        initData();

        
    }

    private void initData() {
        txtAppName.setCompoundDrawables(getAppIcon(), null, null, null); //设置左图标
        txtAppName.setText(getAppName());

        allMemory();
        appMemory();


    }

    /**
     * app占内存百分比统计
     */
    private void appMemory() {
        //当前app使用内存占总使用内存的百分比
        long freeMemory = getAvailableMemory(this);
        long totalMemory = getTotalMemory();
        long useMemory = totalMemory - freeMemory;
        long appUseMemory = getRunningAppProcessInfo();
        float useAppPercent = (appUseMemory/ useMemory)*100f;
        if (useAppPercent<0.1){
            useAppPercent = 0.1f;
        }
        percertAPPUsed.animatToPercent(useAppPercent);
        String percentappUsed = FormatPercent(useAppPercent);
        txtAppMemory.setText("app所占内存: "+FormatMemory(appUseMemory));
        txtAppMemoryPercent.setText("app使用内存占比: "+percentappUsed);
    }

    /**
     * 所用内存占百分比统计
     * @return
     */
    private void allMemory() {
        long freeMemory = getAvailableMemory(this);
        long totalMemory = getTotalMemory();
        long useMemory = totalMemory - freeMemory;

        //已使用内存占总内存的百分比
        float usePercent = (useMemory/(float)totalMemory)*100f;
        percertUsed.animatToPercent(usePercent);

        txtTotalMemory.setText("总内存: "+FormatMemory(totalMemory));
        txtUsedMemory.setText("已使用: "+FormatMemory(useMemory));
        txtUnusedMemory.setText("未使用: "+FormatMemory(freeMemory));
        String percentUsedAndTotal = FormatPercent(usePercent);
        txtPercentUsedAndTotal.setText("已使用占比: "+percentUsedAndTotal);
    }

    private String FormatPercent(float usePercent) {
        DecimalFormat decimalFormat=new DecimalFormat("##0.0%");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(usePercent/100);
    }


    private void initView() {
        txtAppName = (TextView) findViewById(R.id.txt_app_name);
        txtTotalMemory = (TextView) findViewById(R.id.txt_total_memory);
        txtUsedMemory = (TextView) findViewById(R.id.txt_used_memory);
        txtUnusedMemory = (TextView) findViewById(R.id.txt_unused_memory);
        txtPercentUsedAndTotal = (TextView) findViewById(R.id.txt_percent_used_and_total);

        txtAppMemory = (TextView) findViewById(R.id.txt_app_memory);
        txtAppMemoryPercent = (TextView) findViewById(R.id.txt_app_memory_percent);

        percertUsed = (PercentLemon) findViewById(percent_used);
        percertUsed.setOnHeartClickListener(this);
        percertAPPUsed = (PercentLemon) findViewById(percent_app_used);
        percertAPPUsed.setOnHeartClickListener(this);
    }

    private String FormatMemory(long memory){
        return Formatter.formatFileSize(getBaseContext(), memory);
    }


    /**
     * 获取app名称
     * @return
     */
    private String getAppName(){
        return DataStore.getProxy().getAppName();
    }


    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager =  (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem;
    }



    // 获得系统进程信息    
    private long getRunningAppProcessInfo() {
        return  Runtime.getRuntime().totalMemory();
    }


    /**
     *  获取程序 图标
     * @param packName
     * @return
     */
    public Drawable getAppIcon(String packName) {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app图标
     * @return
     */
    @NonNull
    private Drawable getAppIcon() {
        Drawable appIcon = getAppIcon(getPGName());
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        appIcon.setBounds(0, 0, appIcon.getMinimumWidth(), appIcon.getMinimumHeight());
        return appIcon;
    }

    private String getPGName(){
        return getPackageName();
    }


    /**
     * 获取可用内存
     * @return
     */
    private long getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return  mi.availMem;// 将获取的内存大小规格化
    }

    /**
     * 获取总的内存
     * @return
     */
    private long getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Long.valueOf(arrayOfString[1]).longValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return initial_memory;// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 点击百分比控件触发回调
     * @param lemon
     */
    @Override
    public void onHeartClick(PercentLemon lemon) {
        if(lemon.getId() ==percent_used){
            lemon.setPercent(0.0f);
            allMemory();

        }else if (lemon.getId() ==percent_app_used){
            lemon.setPercent(0.0f);
            appMemory();

        }
    }
}
