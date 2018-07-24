package com.example.todo.addOrEditTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.todo.R;

public class AlarmAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"定时任务提醒",Toast.LENGTH_SHORT).show();
        //显示通知栏
        NotificationCompat.Builder no=new NotificationCompat.Builder(context);
        //设置参数
        no.setDefaults(NotificationCompat.DEFAULT_ALL);
        no.setContentTitle("任务提醒");
        no.setContentText("您有待完成任务");
        no.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        Notification notification=no.build();

        //通知管理器
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //发送通知
        notificationManager.notify(0x101,notification);
    }

}
