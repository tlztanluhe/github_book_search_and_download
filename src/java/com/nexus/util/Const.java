package com.nexus.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neusone on 2016/10/25.
 */
public class Const {


  public static String search_words = "site:github.com pdf";

  // 用于进行搜索的主机名字，如果是镜像服务器最好使用原版镜像
  public static String google_host = "https://www.google.com";

  // 下载线程数
  public static int download_thread_num = 10;

  // 初始化遍历目录 ,第一页从0开始
  private static int pageNum = 0;

  public static int getPageNum() {
    int returnPage = 0;
    returnPage = pageNum;
    pageNum += 10;
    return returnPage;

  }

  // google 代理服务器列表
  public static List<String> Google_hosts = new ArrayList<String>();

  // 系统代理服务器列表
  public static Map<String, String> proxyMap = new HashMap<String, String>();
}


