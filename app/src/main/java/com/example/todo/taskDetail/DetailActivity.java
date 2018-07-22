package com.example.todo.taskDetail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.todo.R;
import com.example.todo.data.GreenDaoManager;
import com.example.todo.util.ActivityUtils;

public class DetailActivity extends AppCompatActivity {
    private DetailPresenter mPresenter;
    private GreenDaoManager greenDaoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_arc);
        greenDaoManager = GreenDaoManager.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolBar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long ID = intent.getLongExtra("TYPE",0);
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_contentFrame);
        if(detailFragment == null){
            detailFragment = DetailFragment.newInstance(ID);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
            ,detailFragment,R.id.detail_contentFrame);
        }
        mPresenter = new DetailPresenter(ID,greenDaoManager,detailFragment);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_menu_edit:
                Toast.makeText(this,"edit",Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_menu_delete:
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
