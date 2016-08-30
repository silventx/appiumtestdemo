package com.xpower.appiumtestdemo;

//import com.xpower.appiumtestdemo.util.ExtentReporterNGListener;
//import com.xpower.appiumtestdemo.util.TestNGListener;
//import com.xpower.appiumtestdemo.util.ExtentReporterNGListener;
//import com.xpower.appiumtestdemo.util.TestNGListener;
import jdk.internal.org.xml.sax.SAXException;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.commons.cli.*;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{



    public static void main( String[] args )
    {
//        TestNG testNG = new TestNG();
////        String xmlFileName = "testng.xml";
////
////        XmlSuite suite = new XmlSuite();
////        suite.setName("MyTestSuite");
////
////        List<XmlClass> classes = new ArrayList<XmlClass>();
////        classes.add(new XmlClass("com.xpower.appiumtestdemo.GameBoxTest"));
////
////        XmlTest test = new XmlTest(suite);
////        test.setName("MyTests");
////        test.setXmlClasses(classes);
////
////        List<XmlSuite> suites = new ArrayList<XmlSuite>();
////        suites.add(suite);
//
//
////        testNG.setXmlSuites(suites);
//        testNG.setTestClasses(new Class[] {
//             GameBoxTest.class
//        });
//        testNG.run();

        TestNG testng = new TestNG();
//        List<String> suites = new ArrayList<String>();

//        String path = "./testng.xml";
//        System.out.println(path);
//        suites.add(path);

//        suites.add("C:\\Users\\4399-3040\\IntellijProjects\\appiumtestdemo\\testng.xml");//path to xml..
//        suites.add(new Environment().getPath());


        Map<String, String> params = initCLI(args);

        XmlSuite suite = new XmlSuite();
        suite.setName("MyTestSuite");
        suite.setListeners(Arrays.asList("com.xpower.appiumtestdemo.util.TestNGListener", "com.xpower.appiumtestdemo.util.ExtentReporterNGListener"));
        List<XmlClass> classes = new ArrayList<XmlClass>();
        classes.add(new XmlClass("com.xpower.appiumtestdemo.GameBoxTest"));
        XmlTest test = new XmlTest(suite);
        test.setName("MyTests");
        test.setXmlClasses(classes);

        suite.setParameters(params);

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        testng.setXmlSuites(suites);


        testng.run();


        System.out.println(new App().sayHello());
    }

    /**
     * 初始化命令行参数相关操作
     */
    public static Map<String, String> initCLI(String[] args) {

        Map<String, String> parameters = new HashMap<String, String>();

        Options options = new Options();
        Option option = new Option("a", true, "指定测试应用的路径");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("r", true, "指定测试报告的输出路径");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("c", true, "指定配置文件的路径");
        option.setRequired(false);
        options.addOption(option);

        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(110);
        CommandLine cmd = null;
        CommandLineParser parser = new PosixParser();
        try {
            cmd = parser.parse(options, args);
            String appPath = cmd.getOptionValue("a");
            String reportPath = cmd.getOptionValue("r");
            String configPath = cmd.getOptionValue("c");
            parameters.put("appPath", appPath);
            parameters.put("reportPath", reportPath);
            parameters.put("configPath", configPath);

        } catch (ParseException e) {
            formatter.printHelp("ERROR", options, true);
        }
        return parameters;
    }

    public String sayHello() {
        return "Hello World!";
    }

}

