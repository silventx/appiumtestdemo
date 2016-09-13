package com.xpower.appiumtestdemo.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xpower.appiumtestdemo.Config;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.*;

import static com.xpower.appiumtestdemo.util.Helper.*;

/**
 * 用来读取外部配置文件
 * Created by 4399-3040 on 2016/8/30.
 */
public class FileLoader {

    private String path = null;
    private String content = null;
    private Helper helper;

    public static final String KEY_ITERATOR_LIST = "iteratorList";
    public static final String KEY_TAB_LIST = "tabList";
    public static final String KEY_BLACK_LIST = "blackList";
    public static final String KEY_STARTUP_ACTIONS = "startupActions";
//    public static final String KEY_SCROLL_UP = "scrollUp";
//    public static final String KEY_SCROLL_DOWM = "scrollDown";
//    public static final String KEY_SCROLL_LEFT = "scrollLeft";
//    public static final String KEY_SCROLL_RIGHT = "scrollRight";

    public FileLoader(String path, Helper helper) {
        this.path = path;
        this.content = null;
        this.helper = helper;
    }

    public JsonObject getContent() {
        StringBuilder sb = new StringBuilder();
        JsonObject jsonObject = null;

        System.out.println("before file");
        File file = new File(path);
        System.out.println("after file");
        if (!file.exists()) {
            System.out.println("配置文件路径错误");
        } else {
            System.out.println("读取文件:" + path);
            try {
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8")); //若不指定字符集，将会使用系统默认字符集，导致中文乱码
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                this.content = sb.toString();
                jsonObject = new JsonParser().parse(content).getAsJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 获得外部配置信息
     */
    public void loadConfig() {
        JsonObject object = getContent();

        if (object.has(KEY_ITERATOR_LIST)) {
            System.out.println("get iterator");
            JsonArray iteratorArray = object.getAsJsonArray(KEY_ITERATOR_LIST);
            Config.ITERATOR_LIST = jsonArray2Strings(iteratorArray);
            System.out.println("got it");
        }
        if (object.has(KEY_TAB_LIST)) {
            System.out.println("get tab");
            JsonArray tabArray = object.getAsJsonArray(KEY_TAB_LIST);
            Config.TAB_LIST = jsonArray2Strings(tabArray);
            System.out.println("got it");
        }
//        if (object.has(KEY_BLACK_LIST)) {
//            System.out.println("get black");
//            JsonArray blackArray = object.getAsJsonArray(KEY_BLACK_LIST);
//            Config.BLACK_LIST = jsonArray2Strings(blackArray);
//            constructList(blackArray);
//            System.out.println("got it");
//        }

//        System.out.println(jsonArray2Strings(iteratorArray)[0] + "\n" + jsonArray2Strings(tabArray)[0]);
        if (object.has(KEY_STARTUP_ACTIONS)) { //读取遍历前自定义操作，并进行解析
            System.out.println("get startupacitons");
            JsonArray actions = object.getAsJsonArray(KEY_STARTUP_ACTIONS);
            System.out.println("got it size:" + actions.size());

            JsonObject obj;
            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
                    obj = actions.get(i).getAsJsonObject();
                    String action = obj.get("action").getAsString();
                    String element = obj.get("element").getAsString();

                    System.out.println("action:" + action + " element:" + element);

                    WebElement webElement = null;
                    try {
                        if (element != null && !element.equals("")) {
                            webElement = helper.element(element);
                        }
//                            int type = Helper.getLcatorType(element);
//                            switch (type) {
//                                case Config.LOCATOR_TYPE_ID:
//                                    webElement = Helper.element_id(element);
//                                    System.out.println("type: " + Config.LOCATOR_TYPE_ID);
//                                    break;
//                                case Config.LOCATOR_TYPE_NAME:
//                                    webElement = Helper.element_name(element);
//                                    System.out.println("type: " + Config.LOCATOR_TYPE_NAME);
//                                    break;
//                                case Config.LOCATOR_TYPE_XPATH:
//                                    webElement = Helper.element_xpath(element);
//                                    System.out.println("type: " + Config.LOCATOR_TYPE_XPATH);
//                                    break;
//                                case Config.LOCATOR_TYPE_CLASS:
//                                    webElement = Helper.element_classname(element);
//                                    System.out.println("type: " + Config.LOCATOR_TYPE_CLASS);
//                                    break;
//                            }
//                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("find NoSuchElementException");
                        helper.back();
                        try {
                            webElement = helper.element(element);
                        } catch (NoSuchElementException ee) {
                            ee.printStackTrace();
                        }
                    }


                    if (action.equals("click")) {
                        try {
                            webElement.click();
                        } catch (NoSuchElementException e) {
                            System.out.println("find NoSuchElementException");
                            helper.back();
                            try {
                                webElement.click();
                            } catch (NoSuchElementException ee) {
                                ee.printStackTrace();
                                System.out.println("still find NoSuchElementException");
                            }
                        }

                    } else if (action.equals("sendKeys")) {
                        String data = obj.get("data").getAsString();
                        webElement.sendKeys(data);
                    } else if (action.equals("switchToNative")) {
                        helper.switchToNative();
                    } else if (action.equals("switchToWebView")) {
                        helper.switchToWebView(); //切换到WebView Context
                        helper.setWait(4);
                        System.out.println(helper.getDriver().getPageSource());
                    } else if (action.equals("waitFor")) {
                        helper.waitFor(webElement);
                    } else if (action.equals("scrollUp")) {
                        helper.swipe(helper.getHeight() * 3 / 4, helper.getWidth() / 2, helper.getHeight() / 4, helper.getWidth() / 2, 500);
                    } else if (action.equals("scrollDown")) {
                        helper.swipe(helper.getHeight() / 4, helper.getWidth() / 2, helper.getHeight() * 3 / 4, helper.getWidth() / 2, 500);
                    } else if (action.equals("scrollLeft")) {
                        helper.swipe(helper.getHeight() / 2 , helper.getWidth() * 3 / 4, helper.getHeight() / 2, helper.getWidth() / 4, 500);
                    } else if (action.equals("scrollRight")) {
                        helper.swipe(helper.getHeight() / 2, helper.getWidth() / 4, helper.getHeight() / 2, helper.getWidth() * 3 / 4, 500);
                    }

                    if (i < actions.size() - 1) {
                        String nextElement = actions.get(i + 1).getAsJsonObject().get("element").getAsString();
                        if (nextElement != null && !nextElement.equals("")) {
                            try {
                                helper.waitFor(nextElement); //等待下一个element加载出来后再进行点击
                            } catch (Exception e) {
                                helper.back();
                                helper.waitFor(nextElement);
                            }
                            System.out.println("wait for:" + nextElement);
                        }
                    }
                }
            }
        }
    }

    private String[] jsonArray2Strings(JsonArray array) {
        String[] strings = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            strings[i] = array.get(i).getAsString();
        }
        return strings;
    }


}
