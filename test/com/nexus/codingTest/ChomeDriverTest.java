package com.nexus.codingTest;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by neusone on 2016/10/21.
 */
public class ChomeDriverTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\code\\idea\\github_book_search_and_download\\src\\resource\\chromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testChromeSelenium() {
        driver.get("http://www.baidu.com/");
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}
