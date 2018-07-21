package com.example.todo.util;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId){
        //在活动中添加Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId,fragment);
        transaction.commit();
    }
}
