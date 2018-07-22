package com.example.todo.addOrEditTask;


import com.example.todo.BasePresenter;
import com.example.todo.BaseView;
import com.example.todo.data.Task;

public class AETaskContract {
    interface Presenter extends BasePresenter{
        void saveTaskForNew(String title,String content);
        void saveTaskForEdit(String title,String content,int state);
        Task findTask(Long id);
    }
    interface View extends BaseView<Presenter>{
        void showMsg(String msg);
        void setTask(Long id);
        void setTitle(String title);
        void setContent(String content);
        void closePage();
    }
}
