package com.nexus.codingTest;

import com.nexus.util.http.BZX509TrustManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ListIterator;

/**
 * Created by neusone on 2016/10/18.
 */
public class CodingTest {

    @Test
    public void test1() {
        String targetURL = "https://github.com/qyuhen/book";
        HttpsURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("       Content-Type",
//                    "application/x-www-form-urlencoded");


            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
            connection.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
            connection.setConnectTimeout(60 * 3);
            connection.setUseCaches(true);
            connection.setDoOutput(true);
            connection.setDoInput(true);


            //Get Response
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024 * 100];
            is.read(b);
            System.out.println(new String(b));
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println(response.toString());
//            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Test
    public void test2() throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, IOException {
        // 请求结果
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl;
        HttpsURLConnection conn;
        //查询地址
        String queryString = "https://github.com/qyuhen/book";

        SSLSocketFactory ssf = BZX509TrustManager.getSSFactory();
        try {
            realUrl = new URL(queryString);
            conn = (HttpsURLConnection) realUrl.openConnection();
            conn.setSSLSocketFactory(ssf);

            /**
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
             */
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(60 * 3);
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Test
    public void jsoupTest1() throws IOException {
        Document doc = Jsoup.connect("https://www.google.com.hk/?gws_rd=cr&start=0#q=java+%E5%8C%B9%E9%85%8D+html+%E7%BD%91%E9%A1%B5%E6%8A%93%E5%8F%96")
                .userAgent("Mozilla")
                .timeout(3000)
                .get();
        Elements ele = doc.select(".rc");
        ListIterator<Element> listIterator = ele.listIterator();
        while(listIterator.hasNext()){
            System.out.println(listIterator.next().toString());
        }

    }
}
