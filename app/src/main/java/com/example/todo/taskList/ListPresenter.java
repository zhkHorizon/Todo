package com.example.todo.taskList;

import android.util.Log;

import com.example.todo.data.GreenDaoManager;
import com.example.todo.data.Task;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter implements ListContract.Presenter{
    private GreenDaoManager greenDaoManager;
    private ListContract.View mListView;

    public ListPresenter(GreenDaoManager manager,ListContract.View view){
        //magager实例在活动中获取传入，View同理
        greenDaoManager = manager;
        mListView = view;
        mListView.setPresenter(this);
    }

    @Override
    public void start() {
       mListView.showList(getList());
        Log.d("adapter_len", "start: ");
    }

    @Override
    public List<Task> getList() {
        return greenDaoManager.getAllTasks();
    }


}
