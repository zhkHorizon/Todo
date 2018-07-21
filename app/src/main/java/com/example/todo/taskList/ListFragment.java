package com.example.todo.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todo.OnItemClickListener;
import com.example.todo.R;
import com.example.todo.addOrEditTask.AETaskActivity;
import com.example.todo.data.Task;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ListContract.View{
    public static final int ADD = 0;
    private ListContract.Presenter mPresenter;
    private ListAdapter mListAdapter;

    public ListFragment(){
    }

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ListAdapter(new ArrayList<Task>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();//此处刷新数据
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_frag,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list_reView);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击子项后跳转
                if(mListAdapter.getTask(position).getId()!=0){
                    Toast.makeText(getActivity(),"跳转",Toast.LENGTH_SHORT).show();
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.list_faddTask);
        FloatingActionButton frefresh = (FloatingActionButton) getActivity().findViewById(R.id.list_frefreshTask);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });
        frefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTask();
            }
        });
        return root;
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        mPresenter = presenter;
    }
    @Override
    public void showList(List<Task> tasks) {
        //由presenter调用
        //Toast.makeText(getActivity(),tasks.size(),Toast.LENGTH_SHORT);
        mListAdapter.updateData(tasks);
    }

    public void addNewTask(){
        //跳转活动
        Intent intent = new Intent(getContext(), AETaskActivity.class);

        intent.putExtra("TYPE",ADD);
        startActivity(intent);
    }
    public void refreshTask(){
        showList(mPresenter.getList());
    }
}
