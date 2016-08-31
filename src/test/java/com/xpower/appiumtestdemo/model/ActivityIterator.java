package com.xpower.appiumtestdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责整个遍历流程
 * Created by 4399-3040 on 2016/8/9.
 */
public class ActivityIterator {

    private UIActivity rootActivity;
    public static ActivityStack stack;
    public static List<String> checkedList;


    public ActivityIterator(UIActivity rootActivity) {
        this.rootActivity = rootActivity;
        stack = new ActivityStack(rootActivity); //将根节点入栈
        checkedList = new ArrayList<String>();
    }

    public void run() {
        stack.getTop().performClick();
    }
}
