package com.example.todo.taskList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.todo.R;
import com.example.todo.data.GreenDaoManager;
import com.example.todo.util.ActivityUtils;

public class ListActivity extends AppCompatActivity {
   private ListPresenter mListPresenter;

    private GreenDaoManager greenDaoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_arc);


        greenDaoManager = GreenDaoManager.getInstance();
        ListFragment listFragment =
                (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_contextFrame);
        if(listFragment == null){
            listFragment = ListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),listFragment,R.id.list_contextFrame);
        };

        mListPresenter = new ListPresenter(greenDaoManager,listFragment);

    }
}
