package com.example.todo.taskDetail;


import com.example.todo.data.GreenDaoManager;

public class DetailPresenter implements  DetailContract.Presenter {
    private long mtaskID;
    private GreenDaoManager greenDaoManager;
    private DetailContract.View mDetailView;
    public DetailPresenter(long id, GreenDaoManager greenDaoManager,
                           DetailContract.View view){
        this.mtaskID = id;
        this.greenDaoManager = greenDaoManager;
        this.mDetailView = view;
    }
    @Override
    public void start() {
        //开启任务
    }



    @Override
    public void deleteTask() {
        greenDaoManager.deleteTaskById(new Long(mtaskID));
        mDetailView.showMsg("已删除");
        mDetailView.closePage();
    }
}
