package com.xpower.appiumtestdemo.model;

import com.relevantcodes.extentreports.ExtentReports;
import com.xpower.appiumtestdemo.GameBoxTest;
import com.xpower.appiumtestdemo.util.BaseTest;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 对每个Tab页的遍历
 * Created by 4399-3040 on 2016/8/9.
 */
public class UITab extends Helper{

    private String activityName; //当前Activity的名称
    private ElementSet elementSet;
    private UIActivity parent;
    private BaseTest testHost;

//    private UIPositon positon;


    private static boolean shutdown = false;

    public UITab(UIActivity parent, ElementSet elementSet, BaseTest testHost) {
        super(testHost.getDriver());
        this.parent = parent;
        this.activityName = this.parent.getActivityName();
        this.testHost = testHost;
        if (elementSet != null) {
            this.elementSet = elementSet;
            elementSet.updatePosition(0, 0);
        } else {
            this.elementSet = new ElementSet(driver);
            this.elementSet.init();
        }

        if(parent != null) {
            System.err.println("new uitab created!" + " parent=" + this.parent.getActivityName());
        }

        snapShot(parent.getExtentTest(), null);
    }

    public void performClick() {
        if (elementSet.getElementsMap().size() != 0) {
            for (int i = elementSet.getPositon().getType(); i < elementSet.getElementsMap().size(); i++) {
                UIElements elements = elementSet.getElementsMap().get(i);
                int currentIndex = elementSet.getPositon().getIndex();
                if (elements.size() != 0) {
                    for (int j = currentIndex; j < elements.size(); j++) {
                        System.err.println("Want to click:" + elements.getRule() + "[elementSet: " + i + ", tab element: " + j + "]");
                        try {
                            elements.get(j).click();
                        } catch (NoSuchElementException e) {
                            System.err.println("Find NoSuchElementException!!!!");
                            back();
                            try {
                                elements.get(j).click();
                            } catch (NoSuchElementException exception) {
                                System.err.println("Still Find NoSuchElementException!!!!");
                                back();
                                elements.get(j).click();
                            }

                        }
//                        snapShot();
                        elementSet.updatePosition(i, j + 1);
                        System.err.println(this.activityName + "- update position: elementSet " + i + ", tab element: " + j);
                        clickOver();
                    }
                    elementSet.updatePosition(i + 1, 0);
                }
            }
        }
//        if (ActivityIterator.stack.size() > 1) {
//            back();
//        }

    }

    private void clickOver() throws TimeoutException {
        if (!getCurrentActivity().equals(this.getActivityName())) { //点击进入新的Activity
            if (hasChecked(getCurrentActivity())) { //若当前页面已被遍历过则直接返回，不再遍历
                snapShot(parent.getExtentTest(), "This activity has checked");
                goBack();
                System.out.println("has checked!Checked Activities");
//                for (int i = 0; i < ActivityIterator.checkedList.size(); i++) {
//                    System.out.println(ActivityIterator.checkedList.get(i));
//                }
            } else {
                if (isDialogShown()) {
                    closeDialog();
                }
                UIActivity activity = new UIActivity(this.parent, testHost);
                ActivityIterator.stack.push(activity);
                System.out.println("push: " + activity.getActivityName() + " current stack size: " + ActivityIterator.stack.size());
                ActivityIterator.checkedList.add(activity.getActivityName());
                activity.performClick();

            }
        } else if (isDialogShown()) { //仍在同一个Activity但是出现弹窗
            snapShot(parent.getExtentTest(), null);
            closeDialog();
        } else if (activityName.equals("com.m4399.libs.plugins.PluginProxyActivity") && getCurrentActivity().equals("com.m4399.libs.plugins.PluginProxyActivity")) { //针对PluginProxyActivity的特殊情况
            snapShot(parent.getExtentTest(), null);
            closeDialog();
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


//    private boolean isDialogShown() {
//        System.out.println("is dialog shown??");
//        System.out.println(elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() != 0 ? "yes" : "no");
//        return elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() != 0;
//    }

//    private void clickOver() throws TimeoutException {
//        if (!getCurrentActivity().equals(this.getActivityName())) { //点击进入新的Activity
//            if (isDialogShown()) {
//                back();
//                if (isDialogShown()) {
//                    back();
//                }
//            }
//            UITab activity = new UITab(this);
//            activity.performClick();
//            ActivityIterator.stack.push(activity);
//        } else if (isDialogShown()) { //仍在同一个Activity但是出现弹窗
//            back();
//        } else if (activityName.equals("com.m4399.libs.plugins.PluginProxyActivity") && getCurrentActivity().equals("com.m4399.libs.plugins.PluginProxyActivity")) { //针对PluginProxyActivity的特殊情况
//            setWait(3);
//            if (isDialogShown()) {
//                back();
//
//            } else {
//                back();
//            }
//        }
//    }


//    //点击除了tab和底部item以外的所有控件
//    private void clickElements() {
//
////        elementSet.refresh(); //重新获得当前页面布局
//
////        int i;
////        switch (elementSet.getPositon().getType()) {
////            case Config.TYPE_AD:
////                if (!shutdown) {
////                    if (elementSet.getAdImages().size() > 0) {
////                        for (i = (elementSet.getPositon().getIndex() + 1); i < 1; i++) {
////                            elementSet.getAdImages().get(i).click();
////                            elementSet.updatePosition(Config.TYPE_AD, i); //更新当前遍历位置
////                            System.out.println(this.activityName + " update position: " + Config.TYPE_AD + " " + i);
////                            System.out.println(activityName + "-----" + getCurrentActivity());
////                            clickOver();
////                        }
////                        elementSet.updatePosition(Config.TYPE_TAG, -1);
////                    }
////                }
////            case Config.TYPE_TAG:
////                if (!shutdown) {
////                    if (elementSet.getTags().size() > 0) {
////                        for (i = (elementSet.getPositon().getIndex() + 1); i < 1; i++) {
////                            elementSet.getTags().get(i).click();
////                            elementSet.updatePosition(Config.TYPE_TAG, i); //更新当前遍历位置
////                            System.out.println(this.activityName + " update position: " + Config.TYPE_TAG + " " + i);
////                            System.out.println(activityName + "-----" + getCurrentActivity());
////                            clickOver();
////                        }
////                        elementSet.updatePosition(Config.TYPE_TITLE, -1);
////                    }
////                }
////            case Config.TYPE_TITLE:
////                if (!shutdown) {
////                    if (elementSet.getTitles().size() > 0) {
////                        for (i = elementSet.getPositon().getIndex() + 1; i < 1; i++) {
////                            if (elementSet.getTitles().get(i) != null) {
////                                elementSet.getTitles().get(i).click();
////                                elementSet.updatePosition(Config.TYPE_TITLE, i); //更新当前遍历位置
////                                System.out.println("update position: " + Config.TYPE_TITLE + " " + i);
////                                System.out.println(activityName + "-----" + getCurrentActivity());
////                                clickOver();
////                            }
////                        }
////                        elementSet.updatePosition(Config.TYPE_GAMENAME, -1);
////                    }
////                }
////                break;
////        }
//    }

}
