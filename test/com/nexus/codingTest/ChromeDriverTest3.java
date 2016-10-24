package com.nexus.codingTest;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

@RunWith(JUnit4.class)
public class ChromeDriverTest3 extends TestCase {

    private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        System.out.println("BeforeClass");
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:\\code\\idea\\github_book_search_and_download\\src\\resource\\chromeDriver\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterClass
    public static void createAndStopService() {
        System.out.println("AfterClass");
        service.stop();
    }

    @Before
    public void createDriver() {
        System.out.println("Before");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
    }

    @After
    public void quitDriver() {
        System.out.println("After");
        driver.quit();
    }

    @Test
    public void testGoogleSearch() {
        driver.get("http://www.baidu.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("webdriver");
//        searchBox.quitit();
        assertEquals("webdriver - Google Search", driver.getTitle());
    }
}