package com.nexus;

import com.nexus.util.http.HttpUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by neusone on 2016/10/18.
 */
public class GoogleSearchWork extends Work {
    private static int pageNum = 340;
    private static WebDriver driver;

    /**@!
     * 获取当前google搜索页码
     *
     * @return
     */
    public int getPageNum() {
        int returnPage = 0;
        returnPage = pageNum;
        pageNum += 10;
        return returnPage;

    }

    /**
     * 执行搜索结果
     *
     * @param word
     * @return
     */
    public String searchWork(String word) {

        System.out.println("打开新的google搜索 == ");

        String requestUrl = "https://julianhuang.cc//search?q=${keyword}&start=${pageNum}";
        requestUrl = requestUrl.replace("${pageNum}", "" + this.getPageNum()).replace("${keyword}", word);
//        System.out.println(requestUrl);
//        return new HttpUtil().send(requestUrl);
        System.setProperty("webdriver.firefox.bin", "c:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\code\\idea\\github_book_search_and_download\\src\\resource\\firefoxDriver\\geckodriver.exe");

        System.out.println("打开新的google搜索 == " + requestUrl);

        if (driver == null) {
            driver = new FirefoxDriver();
        }
        driver.get(requestUrl);

        List<WebElement> elements = driver.findElements(new By.ByCssSelector(".rc .r a"));
        List<String> urlList = new ArrayList();
        for (WebElement ele : elements) {
            String href = ele.getAttribute("href");
            if ((href.startsWith("https://julianhuang.cc") && href.contains("related:"))) {
                String targetUrl = href.split("related:")[1];
                urlList.add(targetUrl.startsWith("http") ? targetUrl : "https://" + targetUrl);
            }
            if (href.startsWith("http://github.com") || (href.startsWith("https://github.com"))) {
                urlList.add(href);
            }

        }

//        driver.close();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String url : urlList) {
            final String _url = url;

            try {
                new GithubSearch(_url);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try{
//                executorService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            new GithubSearch(_url);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                System.out.println("开始请求新的仓库 == "+url );
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }

//        try {
//            Thread.sleep(1000 * 60 * 7);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            while (!executorService.awaitTermination(10, TimeUnit.SECONDS));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();
        return null;
    }

    public void githubWork(String github_visit_url) {

    }


    public static void main(String args[]) throws MalformedURLException {

//        System.out.println(work.searchWork("site:github.com pdf"));



        while (true) {

//            new ProxySwitch().switchProxy();
            try {
                GoogleSearchWork work = new GoogleSearchWork();
                work.searchWork("site:github.com book \"*.pdf\"");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
