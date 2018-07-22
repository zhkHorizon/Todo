package com.example.todo.taskDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.addOrEditTask.AETaskActivity;
import com.example.todo.data.Task;

public class DetailFragment extends Fragment implements  DetailContract.View {
    private long TASK_ID;
    private DetailContract.Presenter mPresenter;
    private TextView mTitle;
    private TextView mContent;
    private TextView mState;

    public static DetailFragment newInstance(long taskID){
        DetailFragment fragment = new DetailFragment();
        fragment.setTASK_ID(taskID);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detail_frag,container,false);
        setHasOptionsMenu(true);
        mTitle = (TextView) root.findViewById(R.id.detail_title);
        mContent = (TextView) root.findViewById(R.id.detail_content);
        mState = (TextView) root.findViewById(R.id.detail_state);
        return root;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_menu_edit:{
                mPresenter.deleteTask();
                return true;
            }
            case R.id.detail_menu_delete:{
                editTask();
                return true;
            }
        }
        return false;
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closePage() {
        getActivity().finish();
    }
    @Override
    public void editTask() {
        Intent intent = new Intent(getContext(), AETaskActivity.class);
        intent.putExtra("TYPE",TASK_ID);
        startActivity(intent);
    }

    @Override
    public void showTaskDetail(Task task) {
        mTitle.setText(task.getTitle());
        mContent.setText(task.getContext());
        mState.setText(task.getState());
    }

    public void setTASK_ID(long TASK_ID) {
        this.TASK_ID = TASK_ID;
    }
}