package com.example.todo.addOrEditTask;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.data.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AETaskFragment extends Fragment implements AETaskContract.View, View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private AETaskContract.Presenter mPresenter;
    private EditText mTitle;
    private EditText mContent;

    private TextView mStartDate;
    private TextView mFinishDate;
    private TextView mStartTime;
    private TextView mFinishTime;
    private Switch mAlarmState;
    private TextView mAlarmDate;
    private TextView mAlarmTime;
    private LinearLayout mAlarmLayout;

    private Calendar c;
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date start=null,finish=null,alarm=null;
                try {
                    start = sdf.parse(mStartDate.getText().toString()+" "+mStartTime.getText().toString());
                    finish = sdf.parse(mFinishDate.getText().toString()+" "+mFinishTime.getText().toString()) ;
                    alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(mAlarmState.isChecked())
                    alarmSet();
                if(taskType == null){
                    //新建存储
                    Log.d(TAG, "newTask");
                    mPresenter.saveTaskForNew(mTitle.getText().toString(),mContent.getText().toString(),
                            start,finish,mAlarmState.isChecked(),alarm);
                }
                else{
                    //编辑存储
                    Log.d(TAG, "editTask");
                    Log.d(TAG, "state:"+String.valueOf(state.getSelectedItemPosition()));
                    mPresenter.saveTaskForEdit(mTitle.getText().toString(),mContent.getText().toString()
                            ,state.getSelectedItemPosition(),start,finish,mAlarmState.isChecked(),alarm);
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
        mAlarmState = (Switch) root.findViewById(R.id.add_task_alarm_switch) ;
        mAlarmDate = (TextView) root.findViewById(R.id.add_task_alarm_date);
        mAlarmTime = (TextView) root.findViewById(R.id.add_task_alarm_time);
        mAlarmLayout = (LinearLayout) root.findViewById(R.id.add_task_liner_alarm);

        createSpinnerAdapter();
        state.setAdapter(adapter);
        stateTextView.setVisibility(View.GONE);
        state.setVisibility(View.GONE);

        c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(now);

        mStartDate = (TextView) root.findViewById(R.id.add_task_start_date);
        mFinishDate = (TextView) root.findViewById(R.id.add_task_finish_date);
        mStartDate.setText(date);
        mFinishDate.setText(date);
        mStartDate.setOnClickListener(this);
        mFinishDate.setOnClickListener(this);

        sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(now);
        mStartTime = (TextView)root.findViewById(R.id.add_task_start_time);
        mFinishTime = (TextView) root.findViewById(R.id.add_task_finish_time);
        mStartTime.setText(time);
        mFinishTime.setText(time);
        mStartTime.setOnClickListener(this);
        mFinishTime.setOnClickListener(this);

        mAlarmState.setChecked(false);
        mAlarmDate.setVisibility(View.GONE);
        mAlarmTime.setVisibility(View.GONE);
        mAlarmLayout.setVisibility(View.GONE);
        mAlarmDate.setOnClickListener(this);
        mAlarmDate.setText(date);
        mAlarmTime.setOnClickListener(this);
        mAlarmTime.setText(time);
        mAlarmState.setOnCheckedChangeListener(this);
        return root;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            //需要提醒
            mAlarmDate.setVisibility(View.VISIBLE);
            mAlarmTime.setVisibility(View.VISIBLE);
            mAlarmLayout.setVisibility(View.VISIBLE);

        }else{
            mAlarmDate.setVisibility(View.GONE);
            mAlarmTime.setVisibility(View.GONE);
            mAlarmLayout.setVisibility(View.GONE);
            alarmCannel();
        }
    }

    @Override
    public void onClick(final View text) {
        if(taskType!=null){
            Task task  = mPresenter.findTask(taskType);
            switch (text.getId()){
                case R.id.add_task_start_date:
                    c.setTime(task.getStartTime());
                    showDatePicker(text);
                    break;
                case R.id.add_task_finish_date:
                    c.setTime(task.getFinishTime());
                    showDatePicker(text);
                    break;
                case R.id.add_task_alarm_date:
                    c.setTime(task.getAlarmTime());
                    showDatePicker(text);
                    break;
                case R.id.add_task_start_time:
                    c.setTime(task.getStartTime());
                    showTimePiker(text);
                    break;
                case R.id.add_task_finish_time:
                    c.setTime(task.getFinishTime());
                    showTimePiker(text);
                    break;
                case R.id.add_task_alarm_time:
                    c.setTime(task.getAlarmTime());
                    showTimePiker(text);

                    break;
            }
        }else{
            switch (text.getId()){
                case R.id.add_task_start_date:
                case R.id.add_task_finish_date:
                case R.id.add_task_alarm_date:
                    showDatePicker(text);
                    break;
                case R.id.add_task_start_time:
                case R.id.add_task_finish_time:
                    showTimePiker(text);
                    break;
                case R.id.add_task_alarm_time:
                    showTimePiker(text);
                    break;
            }
        }

    }

    private void alarmSet(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent;
        Calendar c = Calendar.getInstance();
        Date alarm = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(alarm);
        long time = c.getTimeInMillis();
        Intent intent = new Intent(getContext(),AlarmAction.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pendingIntent);
    }
    private void alarmCannel(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent;
        Calendar c = Calendar.getInstance();
        Date alarm = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            alarm = sdf.parse(mAlarmDate.getText().toString()+" "+mAlarmTime.getText().toString()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(alarm);
        long time = c.getTimeInMillis();
        Intent intent = new Intent(getContext(),AlarmAction.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.cancel(pendingIntent);
    }
    private void showDatePicker(final View text){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView t = (TextView) text;
                t.setText(year+"-"+(++month)+"-"+dayOfMonth);
            }
        };
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getContext(),listener,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    private void showTimePiker(final View text){
        Log.d(TAG, "showTimePiker: ");
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TextView t = (TextView) text;
                t.setText(hourOfDay+":"+minute);
            }
        };
        TimePickerDialog dialog;
        dialog = new TimePickerDialog(getContext(),listener,
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
        dialog.show();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mStartDate.setText(sdf.format(task.getStartTime()));
        mFinishDate.setText(sdf.format(task.getFinishTime()));
        sdf = new SimpleDateFormat("HH:mm");
        mStartTime.setText(sdf.format(task.getStartTime()));
        mFinishTime.setText(sdf.format(task.getFinishTime()));
        if(task.getIsAlarm()){
            mAlarmState.setChecked(true);
            mAlarmDate.setVisibility(View.VISIBLE);
            mAlarmTime.setVisibility(View.VISIBLE);
            mAlarmLayout.setVisibility(View.VISIBLE);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            mAlarmDate.setText(sdf.format(task.getAlarmTime()));
            sdf = new SimpleDateFormat("HH:mm");
            mAlarmTime.setText(sdf.format(task.getAlarmTime()));
        }
    }

}
