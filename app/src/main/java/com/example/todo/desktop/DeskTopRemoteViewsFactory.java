package com.example.todo.desktop;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.todo.R;
import com.example.todo.data.GreenDaoManager;

import java.util.ArrayList;
import java.util.List;

public class DeskTopRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private GreenDaoManager greenDaoManager;
    private final Context mContext;
    private static final String TAG = "DeskTopRemoteViewsFacto";
    public static List<String> mList = new ArrayList<>();

    public DeskTopRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
    }

    @Override
    public void onCreate() {
        greenDaoManager = GreenDaoManager.getInstance();
       mList = greenDaoManager.getAllTasksTitle();

    }

    @Override
    public void onDataSetChanged() {
        //刷新数据
        Log.d(TAG, "onDataSetChanged: ");
        mList = greenDaoManager.getAllTasksTitle();
    }

    @Override
    public void onDestroy() {
        mList.clear();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: ");
        if (position < 0 || position >= mList.size())
            return null;

        String content = mList.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.desk_list_item);
        rv.setTextViewText(R.id.desktop_list_item_tv,content);
        Intent intent = new Intent();
        // 传入点击行的数据
        intent.putExtra("content", content);
        rv.setOnClickFillInIntent(R.id.desktop_list_item_tv, intent);
        Log.d(TAG, "getViewAt:"+content);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        //不同view定义的数量
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
