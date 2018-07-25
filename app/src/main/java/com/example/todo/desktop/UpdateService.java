package com.example.todo.desktop;


import android.content.Intent;
import android.widget.RemoteViewsService;

public class UpdateService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DeskTopRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}
