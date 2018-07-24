package com.example.todo.taskList;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.OnItemClickListener;
import com.example.todo.R;
import com.example.todo.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener{
    private List<Task> mData;
    private OnItemClickListener mOnItemClickListener;
    public ListAdapter(List<Task> data){
        this.mData = data;
    }
    public void updateData(List<Task> data){
        this.mData = data;
        //Log.d("adapter_len", String.valueOf(data.size()));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mData.get(position);

        String str ="";
        if(task.getState()==0)
            str = "进行中";
        else if(task.getState()==1)
            str = "已完成";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(task.getStartTime())+ "到" + sdf.format(task.getFinishTime());

        holder.noTitle.setText(task.getTitle());
        holder.noState.setText(str);
        holder.noTime.setText(date);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView noTitle;
        TextView noState;
        TextView noTime;

        public ViewHolder(View itemView) {
            super(itemView);
            noTitle = (TextView) itemView.findViewById(R.id.item_noTitle);
            noState = (TextView) itemView.findViewById(R.id.item_noState);
            noTime = (TextView) itemView.findViewById(R.id.item_noTime);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public Task getTask(int position){
        return mData.get(position);
    }
}
