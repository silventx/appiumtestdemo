package com.xpower.appiumtestdemo.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ReporterType;
import com.xpower.appiumtestdemo.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by 4399-3040 on 2016/8/4.
 */
public class BaseTest {

    protected AndroidDriver driver;

//    private String REPORT_LOCATION = "report/ExtentReports.html";
    protected ExtentReports reports;

    private AppiumServerWrapper wrapper;
    private ExtentTest extentTest;
    public Helper helper;

    @BeforeTest
    @Parameters({"appPath", "configPath", "reportPath", "port", "udid"})
    public void setUp(@Optional("default") String appPath, @Optional("default") String configPath, @Optional("default") String reportPath, @Optional("4723") String port, @Optional("default") String udid)throws Exception {

        System.out.println("base test");

        initAppiumServer(port, udid);
        initExtentReports(udid);
        setUpAppium(appPath, port, udid);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("xxxxxxxxxxxxxx" + method.getName());
        extentTest = reports.startTest(method.getName());
//        extentTest = reports.startTest("test_test");
    }

    @AfterMethod
    public void AfterMethod(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            extentTest.log(LogStatus.PASS, "Test passed");
        }
//        reports.flush();
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        wrapper.stopAppiumServer();
        reports.endTest(extentTest);
        closeExtentReports();
    }

    private void initAppiumServer(String port, String udid) {
        wrapper = new AppiumServerWrapper(port, udid);
        wrapper.startAppiumServer();
    }

    private String formateUdid(String udid) {
        return udid.replace(":", "_");
    }

    private void setUpAppium(String appPath, String port, String udid) throws Exception{
        // set up appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android");
        capabilities.setCapability("platformVersion", "4.4");
        if (!udid.equalsIgnoreCase("default")) {
            capabilities.setCapability("udid",udid);
        }
        //if no need install don't add this
        if (appPath != null && !appPath.equals("default")) {
            capabilities.setCapability("app", appPath);
        } else {
            capabilities.setCapability("app", Config.APP_PATH);
        }
//        capabilities.setCapability("appPackage", "com.m4399.gamecenter");
        //support Chinese
        capabilities.setCapability("unicodeKeyboard" ,"True");
        capabilities.setCapability("resetKeyboard", "True");
        //no need sign
        capabilities.setCapability("noSign", "True");

//        capabilities.setCapability("appActivity", ".ui.activity.GuideActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);

        System.out.println("driver created!");

        helper = new Helper(driver);
    }

    public void initExtentReports(String udid) {
        String formatedUdid = formateUdid(udid);
        String location = Config.REPORT_PATH + "/" + formatedUdid + "_Report.html";
        reports = new ExtentReports(location, true);
//        reports.startReporter(ReporterType.DB, Config.REPORT_PATH + "//ExtentReports.html");
        reports.addSystemInfo("Host Name", "mobi4399");
        reports.addSystemInfo("UDID", udid);
    }


    public void closeExtentReports() {
//        reports.close();
        reports.flush();
    }

    public ExtentReports getExtent() {
        return reports;
    }

    public Helper getHelper() {
        return helper;
    }

    public AndroidDriver getDriver() {
        return driver;
    }

    public  void takescreen(String filename){
        ScreenSrc.getScreen(driver, filename);
    }

}
