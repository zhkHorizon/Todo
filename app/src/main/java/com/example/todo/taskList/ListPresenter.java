package com.example.todo.taskList;

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
    }

    @Override
    public List<Task> getList() {
        return greenDaoManager.getAllTasks();
    }


}
