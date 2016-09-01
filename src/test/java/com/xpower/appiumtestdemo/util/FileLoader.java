package com.xpower.appiumtestdemo.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xpower.appiumtestdemo.Config;
import org.apache.xalan.transformer.KeyIterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
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

    public static final String KEY_ITERATOR_LIST = "iteratorList";
    public static final String KEY_TAB_LIST = "tabList";
    public static final String KEY_BLACK_LIST = "blackList";
    public static final String KEY_STARTUP_ACTIONS = "startupActions";

    public FileLoader(String path) {
        this.path = path;
        this.content = null;
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
//        System.out.println(jsonArray2Strings(iteratorArray)[0] + "\n" + jsonArray2Strings(tabArray)[0]);
        if (object.has(KEY_STARTUP_ACTIONS)) {
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
                    if (element != null && !element.equals("")) {
                        int type = Helper.getLcatorType(element);
                        switch (type) {
                            case Config.LOCATOR_TYPE_ID:
                                webElement = Helper.element_id(element);
                                System.out.println("type: " + Config.LOCATOR_TYPE_ID);
                                break;
                            case Config.LOCATOR_TYPE_NAME:
                                webElement = Helper.element_name(element);
                                System.out.println("type: " + Config.LOCATOR_TYPE_NAME);
                                break;
                            case Config.LOCATOR_TYPE_XPATH:
                                webElement = Helper.element_xpath(element);
                                System.out.println("type: " + Config.LOCATOR_TYPE_XPATH);
                                break;
                        }
                    }

                    if (action.equals("click")) {
                        webElement.click();
                    } else if (action.equals("sendKeys")) {
                        String data = obj.get("data").getAsString();
                        webElement.sendKeys(data);
                    } else if (action.equals("switchToNative")) {
                        switchToNative();
                    } else if (action.equals("switchToWebView")) {
                        switchToWebView(); //切换到WebView Context
                        setWait(4);
                        System.out.println(driver.getPageSource());
                    } else if (action.equals("waitFor")) {
                        waitFor(webElement);
                    }

                    if (i < actions.size() - 1) {
                        String nextElement = actions.get(i + 1).getAsJsonObject().get("element").getAsString();
                        System.out.println("wait for:" + nextElement);
                        if (nextElement != null && !nextElement.equals("")) {
                            waitFor(nextElement); //等待下一个element加载出来后再进行点击
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
