package com.example.todo.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;


@Entity
public class Task {
    @Id
    private Long id;

    @NotNull
    private String title;
    private String context;
    private int state;
    private Date startTime;
    private Date finishTime;
    private boolean isAlarm;
    private Date AlarmTime;
    @Generated(hash = 169521491)
    public Task(Long id, @NotNull String title, String context, int state,
            Date startTime, Date finishTime, boolean isAlarm, Date AlarmTime) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.state = state;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.isAlarm = isAlarm;
        this.AlarmTime = AlarmTime;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Date getStartTime() {
        return this.startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getFinishTime() {
        return this.finishTime;
    }
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    public boolean getIsAlarm() {
        return this.isAlarm;
    }
    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }
    public Date getAlarmTime() {
        return this.AlarmTime;
    }
    public void setAlarmTime(Date AlarmTime) {
        this.AlarmTime = AlarmTime;
    }
    
}
