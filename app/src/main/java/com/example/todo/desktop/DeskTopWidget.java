package com.example.todo.desktop;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.todo.R;

public class DeskTopWidget extends AppWidgetProvider{
    private static final String TAG = "DeskTopWidget";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        ComponentName thisWidget = new ComponentName(context, DeskTopWidget.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desk_layout);

        Intent intent = new Intent(context,UpdateService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(R.id.desktop_list_view, intent);

         //单个item的点击事件
        /*Intent clickIntent = new Intent(context, DeskTopWidget.class);
        clickIntent.setAction("clickAction");
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.desktop_list_view,pendingIntentTemplate);*/


        final Intent refreshInten = new Intent(context,DeskTopWidget.class);
        refreshInten.setAction("com.example.todo.action.REFRESH");
        //发送广播让本接收器接收，让本接收器修改数据
        final PendingIntent refreshPI = PendingIntent.getBroadcast(context,0,
                refreshInten,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.desktop_list_button,refreshPI);
        //刷新组件
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        //super.onEnabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action.equals("com.example.todo.action.REFRESH")){
            Log.d(TAG, "onReceive: ");
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, DeskTopWidget.class);
            //DeskTopRemoteViewsFactory.mList.add("test" );
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.desktop_list_view);
        }else if(action.equals("clickAction")){
            //Toast.makeText(context, intent.getStringExtra("content"),Toast.LENGTH_SHORT).show();
        }
    }
}
