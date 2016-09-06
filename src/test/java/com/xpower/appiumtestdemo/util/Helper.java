package com.xpower.appiumtestdemo.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.xpower.appiumtestdemo.Config;
import com.xpower.appiumtestdemo.model.ActivityIterator;
import io.appium.java_client.android.AndroidDriver;
import net.sourceforge.htmlunit.corejs.javascript.ast.Loop;
import org.jsoup.select.Evaluator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.NamedNodeMap;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Appium帮助类
 * Created by 4399-3040 on 2016/8/4.
 */
public class Helper {

    public static AndroidDriver driver;
    private static WebDriverWait driverWait;

    public static void init(AndroidDriver webDriver) {
        driver = webDriver;
        int timeoutInSec = 10; //等待控件加载的Timeout
        driverWait = new WebDriverWait(webDriver, timeoutInSec);
    }

    public static void setWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }


    public static WebElement element(By locator) {
        return driver.findElement(locator);
    }

    public static WebElement element_id(String id) {
        return element(By.id(id));
    }

    public static WebElement element_name(String name) {
        return element(By.name(name));
    }

    public static WebElement element_classname(String classname) {
        return element(By.className(classname));
    }

    public static WebElement element_xpath(String xpath) {
        return element(By.xpath(xpath));
    }

    public static List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    public static List<WebElement> elements_id(String id) {
        return driver.findElements(By.id(id));
    }

    public static void back() {
        driver.navigate().back();
    }

    public static void backTo(String activityName) {
        while (true) {
            back();
            if (getCurrentActivity().equals(activityName)) {
                System.out.println("back to: " + activityName + "success");
                break;
            }
        }
    }

    public static void goBack() {
        while (true) {
            back();
            if (elements(By.xpath("//android.widget.LinearLayout[@resource-id='com.m4399.gamecenter:id/ll_dialog_content']")).size() != 0) { //返回时遇到dialog则点击确定
                element_name("确定").click();
            }
            if (ActivityIterator.stack.size() != 0 && getCurrentActivity().equals(ActivityIterator.stack.getTop().getActivityName())) {
                System.out.println("back to: " + ActivityIterator.stack.getTop().getActivityName() + "success");
                break;
            } else {
                break;
            }
        }
    }

    public static void closeDialog() {
        while (true) {
            back();
            if (elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() == 0) {
                System.out.println("closing dialog.......");
                break;
            }
        }
    }

    public static AndroidDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getDriverWait() {
        return driverWait;
    }

    public static void swipe(int startx, int starty, int endx, int endy, int duration) {
        driver.swipe(startx, starty, endx, endy, duration);
    }

    public static void scroll(String text) {
        driver.scrollTo(text);
    }

    public static void waitFor(By locator) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitFor(WebElement element) {
        driverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitFor(String element) {
        switch (getLcatorType(element)) {
            case Config.LOCATOR_TYPE_ID:
                waitFor(By.id(element));
                break;
            case Config.LOCATOR_TYPE_NAME:
                waitFor(By.name(element));
                break;
            case Config.LOCATOR_TYPE_XPATH:
                waitFor(By.xpath(element));
                break;
            case Config.LOCATOR_TYPE_CLASS:
                waitFor(By.className(element));
                break;
        }
    }

    public static void waitFor(By locator, int sec) {
        new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * 获得屏幕高度
     */
    public static  int getHeight() {
        return driver.manage().window().getSize().height;
    }

    /**
     * 获得屏幕高度
     */
    public static int getWidth() {
        return driver.manage().window().getSize().width;
    }

    public static void switchToWebView() {
        String context = null;
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
           if (contextName.contains("WEBVIEW")) { //Context中包含WEBVIEW字段的则为WebView的Context
               System.out.println("find WebView context:" + contextName);
               context = contextName;
           }
        }
        if (context == null) {
            System.out.println("Can't find WebView context");
        }
        driver.context(context);
    }

    public static void switchToNative() {
        driver.context("NATIVE_APP");
    }

//    public  void clickAll() {
//        //获得当前页面所有可点击的Tag
//        clickElements("//android.widget.TextView[contains(@resource-id, 'tag')]", "tag");
//        clickElements("//android.widget.TextView[contains(@resource-id, 'title')]", "title");
//        clickElements("//android.widget.TextView[contains(@resource-id, 'gameNameTextView')]", "gameNameTextView");
//        clickElements("//android.widget.TextView[contains(@resource-id, 'tab')]", "tab");
//
//    }

    public static void clickElements(String xpath, int keyword) {
        List<WebElement> elements = elements(By.xpath(xpath));
        WebElement element;
        int size = elements.size();
        System.out.println(keyword + " size:" + size);
        switch (keyword) {
            case 1:
            case 2:
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        element = elements.get(i);
                        element.click();
                        back();
                    }
                }
                break;
            case 3:
                if (size > 0) {
                    elements.get(0).click();
                    back();
                }
                break;
            case 4:
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        element = elements.get(i);
                        element.click();
                    }
                }
                elements.get(1).click();
        }




    }

    public static String getCurrentActivity() {
        String currentActivity = null;
        try {
            String line;
            Process process = Runtime.getRuntime().exec("adb shell dumpsys activity | grep mFocusedActivity");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if ((line = reader.readLine()) != null) {
//                System.out.println(line);
            }
            currentActivity = line.split("/")[1].split(" ")[0];
//            System.out.println(currentActivity);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentActivity;
    }

    public static boolean hasChecked(String activityName) {
        for (int i = ActivityIterator.checkedList.size() - 1; i > 0; i--) {
            if (activityName.equals(ActivityIterator.checkedList.get(i))) {
                return true;
            }
        }
        return false;
    }


    //获得当前屏幕的截图
    public static void snapShot(ExtentTest test, String description) {
        String filename = Long.toString(System.currentTimeMillis());
        ScreenSrc.getScreen(driver, Long.toString(System.currentTimeMillis()));
        if (test != null) {
            String img = test.addScreenCapture(System.getProperty("user.dir") + "\\img\\"+filename+".png");
            if (description != null) {
                test.log(LogStatus.INFO, description, "SnapShot:" + img);
            } else {
                test.log(LogStatus.INFO, "Snapshot", "SnapShot:" + img);
            }
        }
    }

    //判断用来定位控件的locator是根据name id还是xpath
    public static int getLcatorType(String locator) {
        if (locator.contains(":")) {
            return Config.LOCATOR_TYPE_ID;
        } else if (locator.contains("//")) {
            return Config.LOCATOR_TYPE_XPATH;
        } else if (locator.equals("btnP")) {
            return Config.LOCATOR_TYPE_CLASS;
        } else {
            return Config.LOCATOR_TYPE_NAME;
        }
    }

    //判断是否弹出dialog
    public static boolean isDialogShown() {
        return elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() != 0;
    }

    public static void detectDialog() {
        if (elements(By.xpath("//*[contains(@resource-id, 'dialog')]")).size() != 0) {
            back();
        }
    }
}
