package com.example.innovation.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 简单的Notification
* @author Ubi.Innovation
* Learning In   http://www.cnblogs.com/travellife/p/Android-Notification-xiang-jie.html
* @since 1.0*/
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_normal)
    Button buttonNormal;

    NotificationManager notifyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @OnClick(R.id.button_normal)
    public void OnClick(){
        sendNotification();
    }

    @OnClick(R.id.button_action)
    public void OnClickAction(){
        sendSimplestNotificationWithAction();
    }

    @OnClick(R.id.button_ring)
    public void OnClickRing(){
        showNotifyWithRing();
    }

    @OnClick(R.id.button_vibrate)
    public void OnClickVibrate(){
        showNotifyWithVibrate();
    }

    @OnClick(R.id.button_all)
    public void OnClickAll(){
        showNotifyWithAll();
    }

    @OnClick(R.id.button_hang)
    public void OnClickHang(){
        showNotifyWithHang();
    }

    @OnClick(R.id.button_cancel)
    public void OnClickCancel(){
        clearNotify();
    }


    //创建普通Notification 只含有小图标，标题，内容
    private void sendNotification() {
        //获取NotificationManager实例
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容");
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());
    }
    //带Action的Notification
    private void sendSimplestNotificationWithAction(){
        //获取PendingIntent
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setAutoCancel(true)
                //set Intent
                .setContentIntent(mainPendingIntent);

        notifyManager.notify(2, builder.build());
    }

    private void showNotifyWithRing() {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有铃声效果的通知")
                .setContentText("美妙么?安静听~")
                //调用系统默认响铃,设置此属性后setSound()会无效
                .setDefaults(Notification.DEFAULT_SOUND);
                //调用系统多媒体裤内的铃声
//                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));
                //调用自己提供的铃声，位于 /res/values/raw 目录下
                // .setSound(Uri.parse("android.resource://com.littlejie.notification/" + R.raw.sound));

                //另一种设置铃声的方法
                //Notification notify = builder.build();
                //调用系统默认铃声
                //notify.defaults = Notification.DEFAULT_SOUND;
                //调用自己提供的铃声
                //notify.sound = Uri.parse("android.resource://com.littlejie.notification/"+R.raw.sound);
                //调用系统自带的铃声
                //notify.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2");
                //mManager.notify(2,notify);
        notifyManager.notify(3, builder.build());
    }

    /**
     * 展示有震动效果的通知,需要在AndroidManifest.xml中申请震动权限
     * <uses-permission android:name="android.permission.VIBRATE" />
     * 补充:测试震动的时候,手机的模式一定要调成铃声+震动模式,否则你是感受不到震动的
     */
    private void showNotifyWithVibrate() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有震动效果的通知")
                .setContentText("颤抖吧,凡人~")
                //使用系统默认的震动参数,会与自定义的冲突
                .setDefaults(Notification.DEFAULT_VIBRATE);
                //自定义震动效果
//                .setVibrate(vibrate);

        //另一种设置震动的方法
        //Notification notify = builder.build();
        //调用系统默认震动
        //notify.defaults = Notification.DEFAULT_VIBRATE;
        //调用自己设置的震动
        //notify.vibrate = vibrate;
        //mManager.notify(3,notify);
        notifyManager.notify(4, builder.build());
    }

    /**
     * 显示带有默认铃声、震动、呼吸灯效果的通知
     * 如需实现自定义效果,请参考前面三个例子
     */
    private void showNotifyWithAll() {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是有铃声+震动+呼吸灯效果的通知")
                .setContentText("我是最棒的~")
                //等价于setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                .setDefaults(Notification.DEFAULT_ALL);
        notifyManager.notify(6, builder.build());
    }

    private void showNotifyWithHang() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentText("Heads-Up Notification on Android 5.0");
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");

        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, MainActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        notifyManager.notify(7, builder.build());
    }

    /**
     * 清除所有通知
     */
    private void clearNotify() {
        notifyManager.cancelAll();
    }



}
