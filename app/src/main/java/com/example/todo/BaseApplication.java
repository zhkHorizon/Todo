package com.example.todo;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.DaoMaster;
import com.example.todo.data.DaoSession;

public class BaseApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMater;
    private DaoSession mDaoSession;

    public static BaseApplication instances;//静态实例
    //静态实例启动时创建，运行onCreate方法，已经存有唯一实例
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
    }

    public static BaseApplication getInstances(){
        return instances;
    }

    private void setDatabase(){
        mHelper = new DaoMaster.DevOpenHelper(this, "task-db",null);
        db = mHelper.getWritableDatabase();
        mDaoMater = new DaoMaster(db);
        mDaoSession = mDaoMater.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
}
