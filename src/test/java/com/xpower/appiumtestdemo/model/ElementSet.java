package com.xpower.appiumtestdemo.model;

import com.xpower.appiumtestdemo.Config;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通控件集合
 * Created by 4399-3040 on 2016/8/11.
 */
public class ElementSet extends Helper {

//    private List<UIElement> adImages, tags, titles, gameNames, tabs, items;
    private List<UIElements> elementsMap; //需要遍历的所有控件(不包含tab)
//    private List<UIElements> tabMap; //需要遍历的所有tab
    private static int totalElementsSize = 0; //当前所有控件的总数
//    private static int tabSize = 0; //当前所有tab的总数
    private UIPositon positon;
//    private UIPositon tabPosition;

    public ElementSet() {
        elementsMap = new ArrayList<UIElements>();
//        tabMap = new ArrayList<UIElements>();

        positon = new UIPositon(0, 0);
//        tabPosition = new UIPositon(0, 0);

    }

    public void init() {
        System.out.println("detecting UI......");
        if (elements(By.xpath("//android.webkit.WebView")).size() != 0) { //若界面中包含WebView则跳过检测，直接将所有控件数置为0，避免长时间检测
            totalElementsSize = 0;
            System.out.println("find web view!!");
        } else {
            for (int i = 0; i < Config.ITERATOR_LIST.length; i++) {
                UIElements uiElements = new UIElements(Config.ITERATOR_LIST[i]);
                elementsMap.add(uiElements);
//                uiElements.setElements(elements(By.xpath(uiElements.getRule())));
                System.out.println(uiElements.getRule() + ":" + uiElements.getElements().size());
                totalElementsSize += uiElements.getElements().size();
            }
        }
    }

    public int getTotalElementsSize() {
        return this.totalElementsSize;
    }

//    public UIPositon getTabPosition() {
//        return this.tabPosition;
//    }

    public UIPositon getPositon() {
        return this.positon;
    }

    public void updatePosition(int type, int index) {
        this.positon.setType(type);
        this.positon.setIndex(index);
    }

//    public void updateTabPosition(int type, int index) {
//        this.tabPosition.setType(type);
//        this.tabPosition.setIndex(index);
//    }

    public List<UIElements> getElementsMap() {
        return this.elementsMap;
    }

}
