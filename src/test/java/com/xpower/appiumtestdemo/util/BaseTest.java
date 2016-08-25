package com.xpower.appiumtestdemo.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ReporterType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.URL;

/**
 * Created by 4399-3040 on 2016/8/4.
 */
public class BaseTest {

    private  AndroidDriver  driver;

    private static String REPORT_LOCATION = "report/ExtentReports.html";
    protected static ExtentReports reports;

    @BeforeSuite
    public void setUp() throws Exception {

        initExtentReports();

        // set up appium
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "4399.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android");
        capabilities.setCapability("platformVersion", "4.4");
        //if no need install don't add this
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.m4399.gamecenter");
        //support Chinese
        capabilities.setCapability("unicodeKeyboard" ,"True");
        capabilities.setCapability("resetKeyboard", "True");
        //no need sign
        capabilities.setCapability("noSign", "True");

//        capabilities.setCapability("appActivity", ".ui.activity.GuideActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        Helper.init(driver);
    }

    @AfterSuite(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();

        closeExtentReports();
    }

    public void initExtentReports() {
        reports = new ExtentReports(REPORT_LOCATION, true);
        reports.startReporter(ReporterType.DB, REPORT_LOCATION);
        reports.addSystemInfo("Host Name", "xudiwen");
    }


    public void closeExtentReports() {
        reports.close();
    }

    public static ExtentReports getExtent() {
        return reports;
    }

    public  void takescreen(String filename){
        ScreenSrc.getScreen(driver, filename);
    }

}
