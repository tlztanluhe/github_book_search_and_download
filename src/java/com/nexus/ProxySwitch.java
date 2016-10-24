package com.nexus;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by root on 2016/10/22.
 */
public class ProxySwitch {

    private static Map<String, String> proxyMap ;
    {
         proxyMap = new HashMap<String, String>() {{
            put("218.90.174.167", "3128");
            put("183.61.236.53", "3128");
            put("61.132.241.103", "808");
            put("101.200.141.114", "80");
            put("202.106.16.36", "3128");
            put("111.23.4.138", "80");
            put("106.75.128.89", "80");
            put("220.248.230.217","3128");
            put("220.248.229.45","3128");
            put("122.96.59.99","843");
            put("122.96.59.107","843");
            put("115.159.185.186","8088");
            put("115.28.101.22","3128");
        }};
    }
    public  void switchProxy(){

        Object[] proxyArray = proxyMap.keySet().toArray();
        String proxyHost = (String) proxyArray[(int) (Math.random() * proxyArray.length)];
        String proxyPort = proxyMap.get(proxyHost);
        Properties prop = System.getProperties();
        // 设置http访问要使用的代理服务器的地址
        prop.setProperty("http.proxyHost", proxyHost);
        // 设置http访问要使用的代理服务器的端口
        prop.setProperty("http.proxyPort", proxyPort);
        // 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
        prop.setProperty("https.proxyHost", proxyHost);
        prop.setProperty("https.proxyPort", proxyPort);
        // 使用ftp代理服务器的主机、端口以及不需要使用ftp代理服务器的主机
        // socks代理服务器的地址与端口
        prop.setProperty("socksProxyHost", proxyHost);
        prop.setProperty("socksProxyPort", proxyPort);

        boolean canUse = true;

        try {
            URL realUrl = new URL("http://www.baidu.com");
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(1000 * 30);
            conn.setDoInput(true);
            byte[] b = new byte[1024 * 1024];
            conn.getInputStream().read(b);
            conn.getResponseMessage();
            if(conn.getResponseCode() != 200){
                canUse = false;
            }
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            return;
        }
    }
}
