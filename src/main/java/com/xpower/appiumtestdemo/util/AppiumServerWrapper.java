package com.xpower.appiumtestdemo.util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import org.testng.Assert;

import java.io.File;


public class AppiumServerWrapper {

    String appiumInstallationDir_Win = "C:/Program Files (x86)";// e.g. in Windows
    String appiumInstallationDir_Mac = "/Applications";// e.g. for Mac
    AppiumDriverLocalService service = null;
    int serverPort;
    String deviceName;

    public AppiumServerWrapper(String port, String udid) {
        serverPort = Integer.parseInt(port);
        deviceName = udid;
        File classPathRoot = new File(System.getProperty("user.dir"));
        String osName = System.getProperty("os.name");

        if (osName.contains("Windows")) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File(appiumInstallationDir_Win + File.separator + "Appium" + File.separator + "node.exe"))
                    .withAppiumJS(new File(appiumInstallationDir_Win + File.separator + "Appium" + File.separator
                            + "node_modules" + File.separator + "appium" + File.separator + "bin" + File.separator + "appium.js"))
                    .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, "" + (serverPort + 64))
            .usingPort(serverPort));

        } else if (osName.contains("Mac")) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File(appiumInstallationDir_Mac + "/Appium.app/Contents/Resources/node/bin/node"))
                    .withAppiumJS(new File(
                            appiumInstallationDir_Mac + "/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js"))
                    .withLogFile(new File(new File(classPathRoot, File.separator + "log"), "androidLog.txt")));

        } else {
            // you can add for other OS, just to track added a fail message
            Assert.fail("Starting appium is not supporting the current OS.");
        }
    }

    /**
     * Starts appium server
     */
    public void startAppiumServer() {
        service.start();
    }

    /**
     * Stops appium server
     */
    public void stopAppiumServer() {
        service.stop();
    }
}
