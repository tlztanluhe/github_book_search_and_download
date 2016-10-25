package com.nexus.util.http;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by neusone on 2016/10/18.
 */
public class HttpUtil {

  /**
   * 发送https请求，并返回结果
   * 
   * @param requestUrl
   * @return
   */
  public String send(String requestUrl) {
    PrintWriter out = null;
    BufferedReader in = null;
    StringBuffer result = new StringBuffer();
    URL realUrl;
    HttpsURLConnection conn;

    try {
      SSLSocketFactory ssf = BZX509TrustManager.getSSFactory();
      realUrl = new URL(requestUrl);
      conn = (HttpsURLConnection) realUrl.openConnection();
      conn.setSSLSocketFactory(ssf);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("Accept",
          "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
      conn.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
      conn.setRequestProperty("User-Agent",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setConnectTimeout(60 * 3);
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }

      return result.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
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
}
