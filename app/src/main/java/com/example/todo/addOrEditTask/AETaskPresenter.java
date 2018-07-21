package com.example.todo.addOrEditTask;


import android.support.v4.app.Fragment;

import com.example.todo.data.GreenDaoManager;
import com.example.todo.data.Task;

public class AETaskPresenter implements AETaskContract.Presenter{

    GreenDaoManager greenDaoManager;
    private AETaskContract.View mAETaskView;

    public AETaskPresenter(GreenDaoManager manager,AETaskContract.View view ){
        this.greenDaoManager = manager;
        this.mAETaskView = view;
        mAETaskView.setPresenter(this);
    }
    @Override
    public void start() {
        //用于检测重复任务
    }

    @Override
    public void saveTask(String title, String content,int state) {
        String a = title;
        String b = content;
        Task task = new Task(null,title,content,state);
        greenDaoManager.addTasks(task);
        mAETaskView.showMsg("新建成功");
        mAETaskView.closePage();
    }

    @Override
    public Task findTask(Long id) {

        return greenDaoManager.getTaskById(id);
    }
}
