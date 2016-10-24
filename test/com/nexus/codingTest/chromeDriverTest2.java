package com.nexus.codingTest;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/**
 * Created by neusone on 2016/10/21.
 */
public class chromeDriverTest2 {

    private static ChromeDriverService service;
    private WebDriver driver;

    @Test
    public void test(){
        System.setProperty("webdriver.chrome.driver", "C:\\code\\idea\\github_book_search_and_download\\src\\resource\\chromeDriver\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://selenium2.ru/");
        driver.quit();
    }

    @Test
    public void test2(){
        // 设置 chrome 的路径
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\code\\idea\\github_book_search_and_download\\src\\resource\\chromeDriver\\chromedriver.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome
        // 创建一个 Chrome 的浏览器实例
        WebDriver driver = new ChromeDriver();

        // 让浏览器访问 Baidu
        driver.get("http://www.baidu.com");
        // 用下面代码也可以实现
        // driver.navigate().to("http://www.baidu.com");

        // 获取 网页的 title
        System.out.println("1 Page title is: " + driver.getTitle());

        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.id("kw"));

        // 输入关键字
        element.sendKeys("zTree");

        // 提交 input 所在的 form
        element.submit();

        // 通过判断 title 内容等待搜索页面加载完毕，Timeout 设置10秒
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().endsWith("ztree");
            }
        });

        // 显示搜索结果页面的 title
        System.out.println("2 Page title is: " + driver.getTitle());

        // 关闭浏览器
        driver.quit();

        // element = driver.findElement(By.id("kw"));
        // // element.clear();
        // element.click();
        // element.clear();
        // element.sendKeys("zTree");
        // element.submit();
    }

    @Test
    public void forfoxTest(){
        // 如果你的 FireFox 没有安装在默认目录，那么必须在程序中设置
//      System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        // 创建一个 FireFox 的浏览器实例


        System.setProperty("webdriver.gecko.driver","C:\\code\\idea\\github_book_search_and_download\\src\\resource\\firefoxDriver\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();

        // 让浏览器访问 Baidu
        driver.get("http://www.baidu.com");
        // 用下面代码也可以实现
        // driver.navigate().to("http://www.baidu.com");

        // 获取 网页的 title
        System.out.println("1 Page title is: " + driver.getTitle());

        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.id("kw"));


        // 输入关键字
        element.sendKeys("zTree");

        // 提交 input 所在的  form
        element.submit();

        // 通过判断 title 内容等待搜索页面加载完毕，Timeout 设置10秒
//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().endsWith("ztree");
//            }
//        });


        // 显示搜索结果页面的 title
        System.out.println("2 Page title is: " + driver.getTitle());

        //关闭浏览器
//        driver.quit();
    }
}
