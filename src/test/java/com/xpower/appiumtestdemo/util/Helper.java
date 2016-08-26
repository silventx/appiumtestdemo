package com.xpower.appiumtestdemo.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.xpower.appiumtestdemo.model.ActivityIterator;
import io.appium.java_client.android.AndroidDriver;
import net.sourceforge.htmlunit.corejs.javascript.ast.Loop;
import org.json.simple.JSONObject;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by 4399-3040 on 2016/8/4.
 */
public class Helper {

    public static AndroidDriver driver;
    private static WebDriverWait driverWait;

    public static void init(AndroidDriver webDriver) {
        driver = webDriver;
        int timeoutInSec = 60;
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

    public static void waitFor(By locator, int sec) {
        new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * 获得屏幕高度
     */
    public static int getHeight() {
        return driver.manage().window().getSize().height;
    }

    /**
     * 获得屏幕高度
     */
    public static int getWidth() {
        return driver.manage().window().getSize().width;
    }

    public static void switchToWebView() {
        driver.context("WEBVIEW_com.m4399.gamecenter");
    }

    public static void switchToNative() {
        driver.context("NATIVE_APP");
    }

//    public static void clickAll() {
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

    public static void  execCMD(final String cmd) {
        String currentActivity = null;
        try {
            String line;
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject getConfig() {
        Gson gson = new Gson();
        JSONObject object = null;
        JsonParser jsonParser = new JsonParser();
        String content = null;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("F:\\st_work\\appcrawler\\myconfig.json")));
            while ((content = br.readLine()) != null) {
                System.out.println(content);
                sb.append(content);
            }
            br.close();
            JsonElement jsonElement = jsonParser.parse(sb.toString());
            JsonObject object1 = jsonElement.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public boolean hasChecked(String activityName) {
        for (int i = ActivityIterator.checkedList.size() - 1; i > 0; i--) {
            if (activityName.equals(ActivityIterator.checkedList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void writeLog(String str) {
        try {
            File file = new File(System.getProperty("user.dir") + "\\log\\stacktrace.txt");
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            br.write(str + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获得当前屏幕的截图
    public void snapShot(ExtentTest test, String description) {
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

    //判断是否弹出dialog
    public static boolean isDialogShown() {
        return elements(By.xpath("//android.widget.LinearLayout[contains(@resource-id, 'dialog')]")).size() != 0;
    }
}
