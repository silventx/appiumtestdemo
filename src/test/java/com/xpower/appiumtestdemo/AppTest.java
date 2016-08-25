package com.xpower.appiumtestdemo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by 4399-3040 on 2016/8/3.
 */
public class AppTest {

    private App app;

    @BeforeMethod
    public void init() {
        app = new App();
    }

    @Test
    public void testSayHello() {
        Assert.assertEquals(app.sayHello(), "Hello World!");
    }
}
