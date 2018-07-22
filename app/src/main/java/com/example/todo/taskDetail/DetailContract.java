package com.example.todo.taskDetail;


import com.example.todo.BasePresenter;
import com.example.todo.BaseView;
import com.example.todo.data.Task;

public class DetailContract {
    interface Presenter extends BasePresenter{
        //void editTask();
        void deleteTask();
    }
    interface View extends BaseView<Presenter>{
        void showTaskDetail(Task task);
        void editTask();
        void closePage();
        void showMsg(String msg);
    }
}
