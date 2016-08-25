package com.xpower.appiumtestdemo.model;

import com.xpower.appiumtestdemo.Config;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4399-3040 on 2016/8/11.
 */
public class TabSet extends Helper {

    private List<UIElements> tabMap; //需要遍历的所有tab
//    private static int totalElementsSize = 0; //当前所有控件的总数
    private int totalSize = 0;
    private UIPositon positon;
//    private UIPositon tabPosition;

    public TabSet() {
        tabMap = new ArrayList<UIElements>();
        positon = new UIPositon(0, 0);
//        tabPosition = new UIPositon(0, 0);

    }

    public void init() {
        System.out.println("detecting tab......");
        if (elements(By.xpath("//android.webkit.WebView")).size() != 0) { //若界面中包含WebView则跳过检测，直接将所有控件数置为0，避免长时间检测
//            totalElementsSize = 0;
            System.out.println("find web view!!");
        } else {
            for (int i = 0; i < Config.TAB_LIST.length; i++) {
                UIElements uiElements = new UIElements(Config.TAB_LIST[i]);
//                uiElements.setElements(elements(By.id(uiElements.getRule())));
                tabMap.add(uiElements);
                System.out.println(uiElements.getRule() + ":" + uiElements.getElements().size());
//                totalElementsSize += uiElements.getElements().size();
                totalSize += uiElements.getElements().size();
            }
        }
    }


    public int getTotalSize() {
        return this.totalSize;
    }


    public UIPositon getPositon() {
        return this.positon;
    }

    public void updatePosition(int type, int index) {
        this.positon.setType(type);
        this.positon.setIndex(index);
    }


    public List<UIElements> getTabMap() {
        return this.tabMap;
    }

}
