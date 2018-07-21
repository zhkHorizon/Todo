package com.example.todo.data;

import com.example.todo.BaseApplication;

import java.util.List;


public class GreenDaoManager {
    private static GreenDaoManager INSTANCE = null;
    private BaseApplication base;
    private TaskDao taskDao;
    private List<Task> tasksList;

    private GreenDaoManager(){
        base = BaseApplication.getInstances();
        taskDao = base.getDaoSession().getTaskDao();
        tasksList = getAllTasks();
    }

   /* public List<Task> getTasksList() {
        return tasksList;
    }*/

    public static GreenDaoManager getInstance(){
        if(INSTANCE == null){
            INSTANCE  = new GreenDaoManager();
        }

        return INSTANCE;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = taskDao.queryBuilder()
                .where(TaskDao.Properties.Id.notEq(999))
                .limit(5).build().list();
        return taskList;
    }


    public Task getTaskById(long id) {
        Task task = taskDao.queryBuilder()
                .where(TaskDao.Properties.Id.eq(id))
                .unique();
        return task;
    }


    public long addTasks(Task task) {
        return taskDao.insert(task);
    }


    public int updateTasks(Task task) {
        return 0;
    }
}
