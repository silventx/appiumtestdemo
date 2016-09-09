package com.xpower.appiumtestdemo.util;

import com.xpower.appiumtestdemo.GameBoxTest;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestNGListener extends TestListenerAdapter {
    private Logger logger = Logger.getLogger(TestNGListener.class);
    protected ExtentReports extent;
    protected ExtentTest test;

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
/*
        logger.info("【" + tr.getName() + " Start】");
        extent= GameBoxTest.getExtent();
        test= extent.startTest(tr.getName());
*/
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
/*
        takeScreenShot(tr);

        logger.info("【" + tr.getName() + " Failure】");
        test.log(LogStatus.FAIL, tr.getThrowable());
        extent.endTest(test);
*/

    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
/*

        takeScreenShot(tr);
        logger.info("【" + tr.getName() + " Skipped】");
        test.log(LogStatus.SKIP, "SKIP");
        extent.endTest(test);
*/
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
/*
        logger.info("【" + tr.getName() + " Success】");
        test.log(LogStatus.PASS, "Pass");
        extent.endTest(test);
*/
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }


    public void takeScreenShot(ITestResult tr){
        BaseTest baseTestcase=(BaseTest) tr.getInstance();
        baseTestcase.takescreen(tr.getName());
    }
}