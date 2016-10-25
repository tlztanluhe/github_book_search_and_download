package com.nexus.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 代理服务器初始化的方法 Created by neusone on 2016/10/25.
 */
public class PropertiesInit {

  private static String google_hosts_properties_name = "Google_hosts.propeties";
  private static String system_proxy_hosts_properties_name = "System_proxy_hosts.properties";


  public static void initHostValue() throws IOException {

    Properties googleHostProperties = loadProperties(google_hosts_properties_name);
    while (googleHostProperties.propertyNames().hasMoreElements()) {
      String propertiesName = (String) googleHostProperties.propertyNames().nextElement();
      Const.Google_hosts.add(propertiesName);
    }

    Properties systemProxyHostProperties = loadProperties(system_proxy_hosts_properties_name);
    while (systemProxyHostProperties.propertyNames().hasMoreElements()) {
      String proxy = (String) systemProxyHostProperties.propertyNames().nextElement();
      String host = proxy.split(":")[0];
      String port = proxy.split(":")[1];
      Const.proxyMap.put(host, port);
    }


  }

  public static Properties loadProperties(String fileName) throws IOException {

    InputStream inputStream = PropertiesInit.class.getResourceAsStream(fileName);
    Properties properties = new Properties();
    properties.load(inputStream);
    return properties;
  }
}
