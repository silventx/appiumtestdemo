package com.xpower.appiumtestdemo.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xpower.appiumtestdemo.Config;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

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
                BufferedReader br = new BufferedReader(new FileReader(file));
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

        System.out.println("get iterator");
        JsonArray iteratorArray = object.getAsJsonArray(KEY_ITERATOR_LIST);
        Config.ITERATOR_LIST = jsonArray2Strings(iteratorArray);
        System.out.println("got it");

        System.out.println("get tab");
        JsonArray tabArray = object.getAsJsonArray(KEY_TAB_LIST);
        Config.TAB_LIST = jsonArray2Strings(tabArray);
        System.out.println("got it");

        System.out.println(jsonArray2Strings(iteratorArray)[0] + "\n" + jsonArray2Strings(tabArray)[0]);


    }

    private String[] jsonArray2Strings(JsonArray array) {
        String[] strings = null;
        for (int i = 0; i < array.size(); i++) {
            strings[i] = array.get(i).getAsString();
        }
        return strings;
    }

}
