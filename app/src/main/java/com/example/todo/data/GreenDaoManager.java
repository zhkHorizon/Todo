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
        String title = task.getTitle();
        return task;
    }


    public long addTasks(Task task) {
        return taskDao.insert(task);
    }

    public int deleteTaskById(Long id){
        if(id != null){
            taskDao.deleteByKey(id);
            return 1;
        }
        return 0;
    }

    public int updateTasks(Task task) {
        String title = task.getTitle();
        long id = task.getId().longValue();
        Task dbTask = taskDao.queryBuilder()
                .where(TaskDao.Properties.Id.eq(task.getId()))
                .unique();
        if(dbTask != null){
            dbTask.setTitle(task.getTitle());
            dbTask.setContext(task.getContext());
            dbTask.setState(task.getState());
            taskDao.update(dbTask);
            return 1;
        }
        return 0;
    }
}
