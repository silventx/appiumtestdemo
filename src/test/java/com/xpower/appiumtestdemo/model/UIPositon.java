package com.xpower.appiumtestdemo.model;

import com.xpower.appiumtestdemo.Config;

/**
 * Created by 4399-3040 on 2016/8/9.
 */
public class UIPositon {
    private int type;
    private int index;


    public UIPositon(int type, int index) {
        this.type = type;
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
