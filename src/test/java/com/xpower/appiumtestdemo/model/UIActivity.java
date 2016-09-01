package com.xpower.appiumtestdemo.model;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.jna.platform.win32.WTypes;
import com.xpower.appiumtestdemo.Config;
import com.xpower.appiumtestdemo.GameBoxTest;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 对遍历Activity的了逻辑操作，不负责具体的遍历流程
 * Created by 4399-3040 on 2016/8/9.
 */
public class UIActivity extends Helper{

    private String activityName; //当前Activity的名称
    private String url; //唯一标识一个页面，由activity名称与各控件个数组成
    private ElementSet elementSet;
    private TabSet tabSet;
    private UIActivity parent;

    private ExtentReports extent;
    private ExtentTest extentTest;

    private static boolean shutdown = false;

    public UIActivity(UIActivity parent) {
        this.activityName = getCurrentActivity();
        this.url = activityName;
        this.parent = parent;

        this.elementSet = new ElementSet();
        this.tabSet = new TabSet();
        elementSet.init();
        tabSet.init();

        if(parent != null) {
            System.out.println("new uiactivity created! name=" + this.activityName + " parent=" + this.parent.getActivityName());
        }

        this.extent = GameBoxTest.getExtent();
        this.extentTest = extent.startTest(this.activityName);

//        snapShot(extentTest);
    }

    @Test
    public void performClick() {
//        ExtentTest test = extent.startTest(this.activityName); //输出以当前Activity为名臣改的test分支

        if (elementSet.getTotalElementsSize() == 0 && tabSet.getTotalSize() == 0) {
            ActivityIterator.stack.pop();
            goBack();
            ActivityIterator.checkedList.add(this.activityName);
        } else {
            if (tabSet.getTotalSize() != 0) {
                System.out.println("tabset totalsize: " + tabSet.getTotalSize());
                for (int i = tabSet.getPositon().getType(); i < tabSet.getTabMap().size(); i++) {
                    System.out.println("tabsize: " + tabSet.getTabMap().size() + " current i = " + i);
                    UIElements elements = tabSet.getTabMap().get(i);
                    int currentIndex = tabSet.getPositon().getIndex();
                    if (elements.size() != 0) {
                        for (int j = currentIndex; j < elements.size(); j++) {
                            System.out.println("Want to click:" + elements.getRule() + "[" + i + "," + j + "]");
                            try {
                                elements.get(j).click();
                            } catch (NoSuchElementException e) {
                                System.out.println("Find NoSuchElementException!!!!");
                                back();
                                elements.get(j).click();
                            }

//                            snapShot();
                            System.out.println("click tab " + i + " " + j);
                            tabSet.updatePosition(i, j + 1);
                            System.out.println(this.activityName + "- update tab position: " + i + " " + j);
                            new UITab(this, null).performClick();
                        }
                        tabSet.updatePosition(i + 1, 0);
                    }
                }
            } else {
                new UITab(this, elementSet).performClick();
//                if (elementSet.getElementsMap().size() != 0) {
//                    for (int i = elementSet.getPositon().getType(); i < elementSet.getElementsMap().size(); i++) {
//                        UIElements elements = elementSet.getElementsMap().get(i);
//                        int currentIndex = elementSet.getPositon().getIndex();
//                        if (elements.size() != 0) {
//                            for (int j = currentIndex; j < elements.size(); j++) {
//                                elements.get(j).click();
//                                elementSet.updatePosition(i, j + 1);
//                                System.out.println(this.activityName + "- update position: " + i + " " + j);
//                                clickOver();
//                            }
//                            elementSet.updatePosition(i + 1, 0);
//                        }
//                    }
//                }
            }
//            back();
            System.out.println("pop: "  + ActivityIterator.stack.getTop().getActivityName() + " current stack size: " + ActivityIterator.stack.size());
            ActivityIterator.stack.pop();

            extentTest.log(LogStatus.PASS, "Pass");
            extent.endTest(extentTest);
            extent.flush();

            goBack();
        }

    }
//        System.out.println("Running Activity:" + this.activityName + " Current Position:" +elementSet.getPositon().getType() + " " + elementSet.getPositon().getIndex());
//        if (elementSet.getTotalElementsSize() == 0) {
//            back();
//            ActivityIterator.stack.pop();
////            parent.performClick();
//        } else if (elementSet.getTabs().size() != 0) { //判断当前页面是否包含tab
//            if (elementSet.getItems().size() != 0) { //判断当前页面是否为应用首页
//                for (int m = 0; m < elementSet.getItems().size(); m++) {
//                    elementSet.getItems().get(m).click();
//                    System.out.println("item clicked");
//                    if (m == 0) { //只有在第一个item内嵌tab
//                        for (int n = 0; n < elementSet.getTabs().size(); n++ ) {
//                            elementSet.getTabs().get(n).click();
//                            System.out.println("tab clicked");
//                            elementSet.updatePosition(0, -1);
//                            clickElements();
//                        }
//                    } else {
//                        clickElements();
//                    }
//
//                }
//            } else { //普通包含tab的界面
//                for (int j = 0; j < elementSet.getTabs().size(); j++) {
//                    elementSet.getTabs().get(j).click();
//                    elementSet.updatePosition(0, -1);
//                    clickElements();
//                }
//                back();
//                ActivityIterator.stack.pop();
//            }
//        } else {
//            clickElements();
//            back();
//            ActivityIterator.stack.pop();
//        }






//
//    public void updatePosition(int type, int index) {
//        this.positon.setType(type);
//        this.positon.setIndex(index);
//    }

//    public UIPositon getPositon() {
//        return this.positon;
//    }

    public String getActivityName() {
        return this.activityName;
    }

    public ExtentTest getExtentTest() {
        return this.extentTest;
    }

//    public void detectUI() {
//        if (elements(By.xpath("//android.webkit.WebView")).size() != 0) { //若界面中包含WebView则跳过检测，直接将所有控件数置为0，避免长时间检测
//            totalElementsSize = 0;
//            System.out.println("find web view!!");
//        } else {
//            initElements(tagElements, elements(By.xpath("//android.widget.TextView[contains(@resource-id, 'tag')]")));
//            initElements(titleElements, elements(By.xpath("//android.widget.TextView[contains(@resource-id, 'title') and not(contains(@resource-id, 'actionbar'))]")));
//            initElements(tabElements, elements(By.xpath("//android.widget.TextView[contains(@resource-id, 'tab')]")));
//            initElements(gameNameElemennts, elements(By.xpath("//android.widget.TextView[contains(@resource-id, 'gameNameTextView')]")));
//            totalElementsSize = tagElements.size() + titleElements.size() + tabElements.size() + gameNameElemennts.size();
//            System.out.println("totalElemenntsSize:" + totalElementsSize);
////        System.out.println(titleElements.get(0).getElement().getTagName() + " " + titleElements.get(0).getElement().getSize() + " " + titleElements.get(0).getElement().getLocation());
//        }
//    }

    private void initElements(List<WebElement> uiElements, List<WebElement> elements) {
        for (int i = 0; i < elements.size(); i++) {
            WebElement uiElement = elements.get(i);
            uiElements.add(uiElement);
        }
        System.out.println(uiElements.size());
    }

//    private boolean isDialogShown() {
//        return elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() != 0;
//    }

    private void clickOver() throws TimeoutException {
        if (!getCurrentActivity().equals(this.getActivityName())) { //点击进入新的Activity
            if (isDialogShown()) {
                back();
                if (isDialogShown()) {
                    back();
                }
            }
            UIActivity activity = new UIActivity(this);
            activity.performClick();
            ActivityIterator.stack.push(activity);
        } else if (isDialogShown()) { //仍在同一个Activity但是出现弹窗
            back();
        } else if (activityName.equals("com.m4399.libs.plugins.PluginProxyActivity") && getCurrentActivity().equals("com.m4399.libs.plugins.PluginProxyActivity")) { //针对PluginProxyActivity的特殊情况
            setWait(3);
            if (isDialogShown()) {
                back();
            } else {
                back();
            }
        }
    }


    //点击除了tab和底部item以外的所有控件
    private void clickElements() {

//        elementSet.refresh(); //重新获得当前页面布局

//        int i;
//        switch (elementSet.getPositon().getType()) {
//            case Config.TYPE_AD:
//                if (!shutdown) {
//                    if (elementSet.getAdImages().size() > 0) {
//                        for (i = (elementSet.getPositon().getIndex() + 1); i < 1; i++) {
//                            elementSet.getAdImages().get(i).click();
//                            elementSet.updatePosition(Config.TYPE_AD, i); //更新当前遍历位置
//                            System.out.println(this.activityName + " update position: " + Config.TYPE_AD + " " + i);
//                            System.out.println(activityName + "-----" + getCurrentActivity());
//                            clickOver();
//                        }
//                        elementSet.updatePosition(Config.TYPE_TAG, -1);
//                    }
//                }
//            case Config.TYPE_TAG:
//                if (!shutdown) {
//                    if (elementSet.getTags().size() > 0) {
//                        for (i = (elementSet.getPositon().getIndex() + 1); i < 1; i++) {
//                            elementSet.getTags().get(i).click();
//                            elementSet.updatePosition(Config.TYPE_TAG, i); //更新当前遍历位置
//                            System.out.println(this.activityName + " update position: " + Config.TYPE_TAG + " " + i);
//                            System.out.println(activityName + "-----" + getCurrentActivity());
//                            clickOver();
//                        }
//                        elementSet.updatePosition(Config.TYPE_TITLE, -1);
//                    }
//                }
//            case Config.TYPE_TITLE:
//                if (!shutdown) {
//                    if (elementSet.getTitles().size() > 0) {
//                        for (i = elementSet.getPositon().getIndex() + 1; i < 1; i++) {
//                            if (elementSet.getTitles().get(i) != null) {
//                                elementSet.getTitles().get(i).click();
//                                elementSet.updatePosition(Config.TYPE_TITLE, i); //更新当前遍历位置
//                                System.out.println("update position: " + Config.TYPE_TITLE + " " + i);
//                                System.out.println(activityName + "-----" + getCurrentActivity());
//                                clickOver();
//                            }
//                        }
//                        elementSet.updatePosition(Config.TYPE_GAMENAME, -1);
//                    }
//                }
//                break;
//        }
    }

}
