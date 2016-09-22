package com.xpower.appiumtestdemo;

import com.xpower.appiumtestdemo.model.ActivityIterator;
import com.xpower.appiumtestdemo.model.UIActivity;
import com.xpower.appiumtestdemo.util.BaseTest;
import com.xpower.appiumtestdemo.util.FileLoader;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.interactions.internal.TouchAction;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.xpower.appiumtestdemo.util.Helper.*;

public class GameBoxTest extends BaseTest {

//    @BeforeSuite
//    @Parameters({"appPath", "configPath", "reportPath"})
//    @Override
//    public void setUp(String appPath, String configPath, String reportPath) throws Exception {
//
//        System.out.println("game box test");
//
//        System.out.println(appPath + configPath + reportPath);
//        if (!appPath.equals("default")) {
//            Config.APP_PATH = appPath;
//            System.out.println("get a:" +appPath);
//        }
//        if (!configPath .equals("default")) {
//            Config.CONFIG_PATH = configPath;
//            System.out.println("get c:" +configPath);
//        }
//        if (!reportPath.equals("default")) {
//            Config.REPORT_PATH = reportPath;
//            System.out.println("get r:" + reportPath);
//        }
//        super.setUp(appPath, configPath, reportPath);
//    }

//    @BeforeSuite
//    @Parameters({"appPath", "configPath", "reportPath"})
//    public void init(String appPath, String configPath, String reportPath) {
//        System.out.println(appPath + configPath + reportPath);
//        if (!appPath.equals("default")) {
//            Config.APP_PATH = appPath;
//            System.out.println("get a:" +appPath);
//        }
//        if (!configPath .equals("default")) {
//            Config.CONFIG_PATH = configPath;
//            System.out.println("get c:" +configPath);
//        }
//        if (!reportPath.equals("default")) {
//            Config.REPORT_PATH = reportPath;
//            System.out.println("get r:" + reportPath);
//        }
//
//    }

    @Test
    public void testAll() {
//        FileLoader fileLoader = new FileLoader(Config.CONFIG_PATH, helper);
//        fileLoader.loadConfig();

//        doPrevious();
//        helper.element_id("com.m4399.gamecenter:id/tv_skip").click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //for 3.0
        helper.element_id("com.m4399.gamecenter.plugin.main:id/tv_skip").click();
        helper.detectDialog(); //判断是否弹出更新窗口。
        doPrevious();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        new ActivityIterator(new UIActivity(null, this)).run();
    }

    private void doPrevious() {
//        helper.element_id("com.m4399.gamecenter:id/tv_skip").click();
        helper.element_name("我").click();
        helper.element_id("com.m4399.gamecenter.plugin.main:id/btn_login").click();
        Assert.assertTrue(helper.element_id("com.m4399.gamecenter.plugin.main:id/ll_login_weibo").isDisplayed());
        helper.element_id("com.m4399.gamecenter.plugin.main:id/ll_login_weibo").click();
        helper.getCurrentActivity();
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextNames); //prints out something like [NATIVE_APP, WEBVIEW_<APP_PKG_NAME>]
        }
        helper.switchToWebView(); //切换到WebView Context
        helper.setWait(4);
        System.out.println(driver.getPageSource());

        helper.element_id("userId").sendKeys(Config.WEIBO_USERNAME);
        helper.element_id("passwd").sendKeys(Config.WEIBO_PASSWORD);
        helper.element_classname("btnP").click();
        helper.switchToNative();

        helper.waitFor(By.name("找游戏"));
        helper.element_name("找游戏").click();
    }

//        WebElement skipButton = element_id("com.m4399.gamecenter:id/tv_skip");
//        skipButton.click();
//        Assert.assertTrue(element_name("推荐").isDisplayed());
//
////        clickAll();
//        getCurrentActivity();
//
//        WebElement meTab = element_name("我");
//        meTab.click();
//
//        element_id("com.m4399.gamecenter:id/btn_login").click();
//        getCurrentActivity();


//    @Test(dependsOnMethods = {"testAll"})
//    public void loginWeibo() {
//        loginByWeibo();
//    }
//
//    @Test(dependsOnMethods = {"loginWeibo"})
//    public void loginQQ() {
//        loginByQQ();
//    }
//
//    @Test(dependsOnMethods = {"loginQQ"})
//    public void login(){
////
////        element_id("com.m4399.gamecenter:id/actions_item_settings").click();
////        element_name("退出登录").click();
////        element_name("登录").click();
//
//        waitFor(By.id("com.m4399.gamecenter:id/actions_item_settings"));
//        element_id("com.m4399.gamecenter:id/actions_item_settings").click();
//        element_name("退出登录").click();
//        element_name("登录").click();
//
//        WebElement etUsername = element_id("com.m4399.gamecenter:id/username_edittext");
//        WebElement etPassword = element_id("com.m4399.gamecenter:id/password_edittext");
//        WebElement btnLogin = element_id("com.m4399.gamecenter:id/login_button");
//
//        //测试登录失败
////        etUsername.sendKeys(Config.LOGIN_WRONG_USERNAME);
////        etPassword.sendKeys(Config.LOGIN_WRONG_PASSWORD);
//
//        if (isCaptchaDisplayed()) {
//            loginByWeibo();
//        } else {
//            //测试登录成功
//            etUsername.sendKeys(Config.LOGIN_USERNAME);
//            etPassword.sendKeys(Config.LOGIN_PASSWORD);
//            btnLogin.click();
//        }
//
//        Assert.assertTrue(element_name("退出登录").isDisplayed());
//
//
//    }
//
//
//
//    @Test(dependsOnMethods = {"login"})
//    public void basicClick() {
//        back();
//        element_name("找游戏").click();
//        element_id("com.m4399.gamecenter:id/siv_tag_icon").click();
//        element_id("com.m4399.gamecenter:id/actionbar_home_is_back").click();
//        Assert.assertTrue(element_name("推荐").isDisplayed());
//    }
//
//    @Test(dependsOnMethods = {"basicClick"})
//    public void basicSwipe() {
//        swipe(getWidth() * 3 / 4, getHeight() / 2, getWidth() / 4, getHeight() / 2, 500);
//        setWait(2);
//        scroll("儿童教育");
//        element_name("儿童教育").click();
//        Assert.assertTrue(element_name("亲子游戏000").isDisplayed());
//    }
//
//    private boolean isCaptchaDisplayed() {
//        //若登录出现验证码输入，等待5秒，手动输入测试结果
//        List<WebElement> captchas = elements_name("输入验证码");
//        System.out.println(captchas.size());
//        if (captchas.size() <= 0) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private void loginByWeibo() {
//        element_id("com.m4399.gamecenter:id/ll_login_weibo").click();
//        getCurrentActivity();
//        Set<String> contextNames = driver.getContextHandles();
//        for (String contextName : contextNames) {
//            System.out.println(contextNames); //prints out something like [NATIVE_APP, WEBVIEW_<APP_PKG_NAME>]
//        }
//        switchToWebView(); //切换到WebView Context
//        setWait(4);
//        System.out.println(driver.getPageSource());
////	do web testing
//        element_id("userId").sendKeys(Config.WEIBO_USERNAME);
//        element_id("passwd").sendKeys(Config.WEIBO_PASSWORD);
//        element_classname("btnP").click();
//        switchToNative();
//    }
//
//    private void loginByQQ() {
//
//    }
}