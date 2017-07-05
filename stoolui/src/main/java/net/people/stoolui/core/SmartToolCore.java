package net.people.stoolui.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.transfar.smarttda.core.TDA;

import net.people.stoolui.main.MainActivity;
import net.people.stoolui.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by wulei
 * Data: 2016/12/26.
 */

public class SmartToolCore {
    private SmartToolCore(){

    }

    private static class SmartToolCoreInstance{
        public static final SmartToolCore Instance = new SmartToolCore();
    }

    public static SmartToolCore getInstance(){
        return SmartToolCoreInstance.Instance;
    }

    public void init(Context c){
        TDA.getInstance().init(c);
        initNotification(c);

        //
//        Intent intent=new Intent(c, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        c.startActivity(intent);
    }

    public void init(Context c, boolean isDebug){
        TDA.getInstance().init(c, isDebug);
        initNotification(c);
//        Intent intent=new Intent(c, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        c.startActivity(intent);
    }
    public void initNotification(Context c){
        //通知栏提示
        NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        Intent intent = new Intent(c, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(c);
            builder.setContentIntent(pendingIntent);
            builder.setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(c, R.drawable.ic_ball)).getBitmap());
            builder.setSmallIcon(R.drawable.ic_ball);// 设置图标
            builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
            builder.setContentTitle(c.getResources().getString(R.string.stool_notification_title));// 设置通知的标题
            builder.setContentText(c.getResources().getString(R.string.stool_notification_content));// 设置通知的内容
            builder.setOngoing(true);
            notification = builder.build();

        } else {
            notification = new Notification(R.drawable.ic_ball, "smart", System.currentTimeMillis());
        }
        mNotificationManager.notify(0, notification);
    }

}
