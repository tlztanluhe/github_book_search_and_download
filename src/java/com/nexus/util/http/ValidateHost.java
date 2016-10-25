package com.nexus.util.http;

import com.nexus.Exception.GoogleCannotReachableException;
import com.nexus.Exception.SystemProxyCannotReachableException;
import com.nexus.util.Const;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by neusone on 2016/10/25.
 */
public class ValidateHost{

    public static void validation() throws SystemProxyCannotReachableException {

        try {
            validation_google_search();
            validation_github();
        } catch (IOException | GoogleCannotReachableException e) {
            e.printStackTrace();
            throw new SystemProxyCannotReachableException();
        }

    }

    /**
     * 验证google 搜索的可用性
     */
    private static void validation_google_search() throws IOException, GoogleCannotReachableException {
        Document doc = Jsoup.connect(Const.google_host).get();
        String title = doc.title();
        if(title.indexOf("404") > 0){
            throw new GoogleCannotReachableException();
        }
    }

    /**
     * 验证github的可用性
     */
    private static void validation_github() throws IOException {
        Document doc = (Document) Jsoup.connect("https://www.github.com").get();

    }
}
