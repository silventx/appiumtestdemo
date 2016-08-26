package com.xpower.appiumtestdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4399-3040 on 2016/8/4.
 */
public class Config {

    public static final String LOGIN_WRONG_USERNAME = "123";
    public static final String LOGIN_WRONG_PASSWORD = "123";

    public static final String LOGIN_USERNAME = "autotest4399";
    public static final String LOGIN_PASSWORD = "autotest4399";

    public static final String WEIBO_USERNAME = "798094223@qq.com";
    public static final String WEIBO_PASSWORD = "autotest4399";

    public static final String QQ_USERNAME = "3310379635";
    public static final String QQ_PASSWORD = "autotest";

//    public static final String ITERATOR_LIST = "\"iteratorList\" : [\"//android.widget.LinearLayout[@clickable='true']\", \"//android.widget.FrameLayout[@clickable='true']\", \"//android.widget.ImageView[@clickable='true']\", \"//android.widget.RelativeLayout[@clickable='true']\",\"//android.widget.TextView[@clickable='true']\",]";

//    //控件类型分类
//    public static final int TYPE_AD = 0;
//    public static final int TYPE_TAG = 1;
//    public static final int TYPE_TITLE = 2;
//    public static final int TYPE_GAMENAME = 3;
//    public static final int TYPE_TAB = 4;
//
//    public static final String RULE_TAG = "//android.widget.TextView[contains(@resource-id, 'tag') and not(contains(@resource-id, 'game_category'))]";
//    public static final String RULE_TITLE = "//android.widget.TextView[contains(@resource-id, 'title') and not(contains(@resource-id, 'actionbar'))]";
//    public static final String RULE_TAB = "//android.widget.TextView[contains(@resource-id, 'tab')]";
//    public static final String RULE_GAMENAME = "//android.widget.TextView[contains(@resource-id, 'gameNameTextView')]";
//
//    public static final String RULE_ADIMAGE = "com.m4399.gamecenter:id/adImageView";
//    public static final String RULE_ITEM = "com.m4399.gamecenter:id/itemTextView";

    public static final String[] ITERATOR_LIST = {
            "//android.widget.LinearLayout[@clickable='true' and not(contains(@resource-id, 'Download')) and not(contains(@resource-id, 'hotWordText')) and not(contains(@resource-id, 'Comment')) and not(contains(@resource-id, 'Like')) and not(contains(@resource-id, 'Forward'))]",
            "//android.widget.FrameLayout[@clickable='true' and not(contains(@resource-id, 'Download')) and not(contains(@resource-id, 'btn_game_action'))]",
            "//android.widget.ImageView[@clickable='true' and not(contains(@resource-id, 'screenshot'))]",
            "//android.widget.RelativeLayout[@clickable='true'  and not(contains(@resource-id, 'parentView'))]//android.widget.TextView[not(contains(@resource-id, 'tab')) and not(contains(@resource-id, 'item')) and not(contains(@resource-id, 'actionbar')) and not(contains(@resource-id, 'back')) and not(contains(@resource-id, 'Like'))]/.."
    };

    public static final String[] TAB_LIST = {
            "//android.widget.RelativeLayout[@clickable='true']//android.widget.TextView[contains(@resource-id, 'tab')]/..",
            "com.m4399.gamecenter:id/itemTextView"
    };

}
