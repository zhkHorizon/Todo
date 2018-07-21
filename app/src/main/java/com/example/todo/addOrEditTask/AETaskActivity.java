package com.example.todo.addOrEditTask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.todo.R;
import com.example.todo.data.GreenDaoManager;
import com.example.todo.taskList.ListFragment;
import com.example.todo.util.ActivityUtils;

public class AETaskActivity extends AppCompatActivity {
    private AETaskPresenter mAETaskPresenter;
    private GreenDaoManager greenDaoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_arc);
        greenDaoManager = GreenDaoManager.getInstance();
        AETaskFragment aeTaskFragment =
                (AETaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(aeTaskFragment == null){
            aeTaskFragment = AETaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    aeTaskFragment,R.id.contentFrame);
        }
        mAETaskPresenter = new AETaskPresenter(greenDaoManager,aeTaskFragment);
        Intent intent = getIntent();
        long num = intent.getLongExtra("TYPE",0);
        if(num != ListFragment.ADD)
            aeTaskFragment.setTask(new Long(num));//在详情处点击编辑打开此活动
    }
}
