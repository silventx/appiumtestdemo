package com.xpower.appiumtestdemo.model;

import com.xpower.appiumtestdemo.util.ExtentReporterNGListener;
import com.xpower.appiumtestdemo.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 同一类控件的抽象类
 * Created by 4399-3040 on 2016/8/18.
 */
public class UIElements {

    private String rule; //该组控件拥有的统一规则
    private List<WebElement> elements;
    private Helper helper;

    public UIElements(String rule, Helper helper) {
        this.rule = rule;
        this.helper = helper;
        elements = new ArrayList<WebElement>();
        if (isId(rule)) {
            elements = helper.elements(By.id(rule));
        } else {
            elements = helper.elements(By.xpath(rule));
        }
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<WebElement> getElements() {
        return elements;
    }

    public void setElements(List<WebElement> elements) {
        this.elements = elements;
    }

    public int size() {
        return this.elements.size();
    }

    public WebElement get(int index) {
        return this.elements.get(index);
    }

    public void refresh() {
        setElements(helper.elements(By.xpath(this.rule)));
    }

    private boolean isId(String rule) { //判断所给的rule是xpath还是id
        return rule.contains(":");
    }
}
