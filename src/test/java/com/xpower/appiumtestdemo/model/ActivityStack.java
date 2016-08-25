package com.xpower.appiumtestdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by 4399-3040 on 2016/8/11.
 */
public class ActivityStack {

    private List<UIActivity> activities;

    public ActivityStack(UIActivity root) {
        activities = new ArrayList<UIActivity>() ;
        this.activities.add(root);
    }

    public UIActivity getTop() {
        return activities.get(activities.size() - 1);
    }

    public UIActivity getBottom() {
        if (activities.size() == 0) {
            return null;
        } else {
            return activities.get(0);
        }
    }

    public void push(UIActivity activity) {
        activities.add(activity);
    }

    public void pop() {
        activities.remove(activities.size() - 1);
    }

    public boolean isEmpty() {
        if (activities.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return this.activities.size();
    }
}
