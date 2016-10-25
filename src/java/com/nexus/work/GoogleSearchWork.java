package com.nexus.work;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nexus.Exception.GithubCannotReachableException;
import com.nexus.Exception.GoogleCannotReachableException;
import com.nexus.util.Const;

/**
 * Created by neusone on 2016/10/18.
 */
public class GoogleSearchWork {

  private static WebDriver driver;

  private static Logger logger = Logger.getLogger(GoogleSearchWork.class);

  /**
   * 执行搜索结果
   *
   * @param word
   * @return
   */
  public List<String> searchWork(String word)
      throws GoogleCannotReachableException, GithubCannotReachableException {

    String requestUrl = Const.google_host + "/" + "search?q=${keyword}&start=${pageNum}";
    requestUrl =
        requestUrl.replace("${pageNum}", "" + Const.getPageNum()).replace("${keyword}", word);

    try {
      // 设置firefox执行程序
      System.setProperty("webdriver.firefox.bin",
          "c:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
      // 设置firefox驱动
      System.setProperty("webdriver.gecko.driver",
          "C:\\code\\idea\\github_book_search_and_download\\src\\resource\\firefoxDriver\\geckodriver.exe");

      logger.info(String.format("打开新的google搜索 == %s", requestUrl));

      if (driver == null) {
        driver = new FirefoxDriver();
      }
      driver.get(requestUrl);

      String title = driver.getTitle();
      if (title.indexOf("404") > 0) {
        throw new GoogleCannotReachableException();
      }

    } catch (Exception e) {
      throw new GoogleCannotReachableException();
    }

    // 获取搜索结果页的github目录
    List<WebElement> elements = driver.findElements(new By.ByCssSelector(".rc .r a"));
    List<String> urlList = new ArrayList();
    for (WebElement ele : elements) {
      String href = ele.getAttribute("href");

      // 如果使用vpn访问,不同国家的vpn,google会跳转到不同的域名上
      if ((href.startsWith(Const.google_host.replace("com", "")) && href.contains("related:"))) {
        String targetUrl = href.split("related:")[1];
        urlList.add(targetUrl.startsWith("http") ? targetUrl : "https://" + targetUrl);
      }
      if (href.startsWith("http://github.com") || (href.startsWith("https://github.com"))) {
        urlList.add(href);
      }

    }

    return urlList;
  }
}
