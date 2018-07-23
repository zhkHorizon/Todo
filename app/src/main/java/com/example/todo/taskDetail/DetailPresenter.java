package com.example.todo.taskDetail;


import com.example.todo.data.GreenDaoManager;
import com.example.todo.data.Task;

public class DetailPresenter implements  DetailContract.Presenter {
    private long mtaskID;
    private GreenDaoManager greenDaoManager;
    private DetailContract.View mDetailView;
    public DetailPresenter(long id, GreenDaoManager greenDaoManager,
                           DetailContract.View view){
        this.mtaskID = id;
        this.greenDaoManager = greenDaoManager;
        this.mDetailView = view;
        mDetailView.setPresenter(this);
    }
    @Override
    public void start() {
        //开启任务
        Task task = greenDaoManager.getTaskById(mtaskID);
        mDetailView.showTaskDetail(task);
    }



    @Override
    public void deleteTask() {
        greenDaoManager.deleteTaskById(new Long(mtaskID));
        mDetailView.showMsg("已删除");
        mDetailView.closePage();
    }
}
