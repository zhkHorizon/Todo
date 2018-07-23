package com.example.todo.addOrEditTask;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.data.Task;

import java.util.ArrayList;

public class AETaskFragment extends Fragment implements AETaskContract.View{

    private AETaskContract.Presenter mPresenter;
    private EditText mTitle;
    private EditText mContent;
    private Long taskType = null;
    private TextView stateTextView;
    private Spinner state;
    private ArrayAdapter<String> adapter;
    public static final String TAG = "AETaskFragment";

    public static AETaskFragment newInstance(){
        return new AETaskFragment();
    }

    public AETaskFragment(){
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(AETaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_task_done);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(taskType == null){
                    //新建存储
                    Log.d(TAG, "newTask");
                    mPresenter.saveTaskForNew(mTitle.getText().toString(),mContent.getText().toString());
                }
                else{
                    //编辑存储
                    Log.d(TAG, "editTask");
                    Log.d(TAG, "state:"+String.valueOf(state.getSelectedItemPosition()));
                    mPresenter.saveTaskForEdit(mTitle.getText().toString(),mContent.getText().toString(),state.getSelectedItemPosition());
                }

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_frag,container,false);
        mTitle = (EditText) root.findViewById(R.id.add_task_title);
        mContent = (EditText) root.findViewById(R.id.add_task_content);
        state = (Spinner) root.findViewById(R.id.add_task_state_spinner);
        stateTextView = (TextView) root.findViewById(R.id.add_task_state_textView);
        createSpinnerAdapter();
        state.setAdapter(adapter);
        stateTextView.setVisibility(View.INVISIBLE);
        state.setVisibility(View.INVISIBLE);
        return root;
    }

    private void createSpinnerAdapter(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("进行中");
        list.add("已完成");
        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,list);
        adapter.setDropDownViewResource(R.layout.spinner_drop_item);

    }
    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setContent(String content) {
        mContent.setText(content);
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
    public void setTask(Long id) {
        state.setVisibility(View.VISIBLE);
        stateTextView.setVisibility(View.VISIBLE);
        this.taskType = id;
        Task task  = mPresenter.findTask(id);
        setTitle(task.getTitle());
        setContent(task.getContext());
        state.setSelection(task.getState());
    }

}
