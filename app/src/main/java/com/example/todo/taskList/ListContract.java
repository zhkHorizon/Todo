package com.example.todo.taskList;


import com.example.todo.BasePresenter;
import com.example.todo.BaseView;
import com.example.todo.data.Task;


import java.util.List;

public interface ListContract {
    interface View extends BaseView<Presenter>{
        void showList(List<Task> tasks);
    }
    interface Presenter extends BasePresenter{
        List<Task> getList();

    }
}
