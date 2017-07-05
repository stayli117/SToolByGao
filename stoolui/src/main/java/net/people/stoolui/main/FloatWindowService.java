package net.people.stoolui.main;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowService extends Service {

	public  final String pakageName = "com.transfar.smarttoolui";
	/**
	 * 用于在线程中创建或移除悬浮窗。
	 */
	private Handler handler = new Handler();

	/**
	 * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
	 */
	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {

		// 开启定时器，每隔0.5秒刷新一次
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		stopWindows();
	}


	//把悬浮按钮删除
	private void stopWindows() {
		timer.cancel();
		timer = null;
		SmartToolWindowManager.removeSmallWindow(getMContext());
		SmartToolWindowManager.removeBigWindow(getMContext());
	}


	/**
	 * 判断当前界面是否是桌面
	 */
	private boolean isHome() {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return getHomes().contains(rti.get(0).topActivity.getPackageName());
	}
	/**
	 * 判断当前界面是否是smarttool界面
	 */
	private boolean isSmartToolUI() {

		return getClassName(getMContext()).contains(pakageName);
	}

	/**
	 * 获得属于桌面的应用的应用包名称
	 *
	 * @return 返回包含所有包名的字符串列表
	 */
	private List<String> getHomes() {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}

	class RefreshTask extends TimerTask {

		@Override
		public void run() {
			//		没有悬浮窗显示，则创建悬浮窗。
			if (!SmartToolWindowManager.isWindowShowing()&&!isHome()&&isForeground(getMContext())&&!isSmartToolUI()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						SmartToolWindowManager.createSmallWindow(getMContext());
					}
				});
			}
			// 有悬浮窗显示，则更新内存数据。
			else if (SmartToolWindowManager.isWindowShowing()&&!isHome()&&isForeground(getMContext())&&!isSmartToolUI()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						SmartToolWindowManager.updateUsedPercent(getMContext());
					}
				});
			}else {
				SmartToolWindowManager.removeSmallWindow(getMContext());
			}
		}

	}

	/**
	 * 当前应用是否处于前台
	 * @param context
	 * @return
     */
	private boolean isForeground(Context context) {
		if (context != null) {
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
			for (ActivityManager.RunningAppProcessInfo processInfo: processes) {
				if (processInfo.processName.equals(context.getPackageName())) {
					if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前应用的名称
	 * @param context
	 * @return
     */
	public static String getAppProcessName(Context context) {
		//当前应用pid
		int pid = android.os.Process.myPid();
		//任务管理类
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//遍历所有应用
		List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : infos) {
			if (info.pid == pid)//得到当前应用
				return info.processName;//返回包名
		}
		return "";
	}

	/**
	 * 获取当前栈顶activity的名称
	 * @param context
	 * @return
     */
	private String getClassName(Context context){
		ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return rti.get(0).topActivity.getClassName();
	}

	private Context getMContext(){

		return getApplicationContext();
	}
}
