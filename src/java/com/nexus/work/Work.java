package com.nexus.work;

import com.nexus.Exception.GithubCannotReachableException;
import com.nexus.Exception.GoogleCannotReachableException;
import com.nexus.util.Const;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 程序执行主方法 Created by neusone on 2016/10/25.
 */
public class Work {

  public static Logger logger = Logger.getLogger(Work.class);

  public static boolean isBreak = false;

  public static void main(String[] args) {
    while (true) {

      // new ProxySwitch().switchProxy();
      try {
        GoogleSearchWork work = new GoogleSearchWork();
        List<String> gitUrlList = work.searchWork(Const.search_words);


        for (String url : gitUrlList) {
          final String _url = url;

          try {
            new GithubSearchWork(_url);

            // 如果github不可访问,则跳出循环,进行休眠
          } catch (GithubCannotReachableException e) {
            throw e;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } catch (GoogleCannotReachableException | GithubCannotReachableException e) {

        loopOutputError(e);
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

  /**
   * 循环输出错误
   */
  public static void loopOutputError(Exception e) {
    while (true) {
      logger.error(e.getMessage());
      e.printStackTrace();

      try {
        Thread.sleep(1000 * 120);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }

      if (isBreak) {

        // 重新加载代理 以及 google 主机列表
        break;
      }
    }
  }
}
