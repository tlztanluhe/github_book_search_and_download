package com.nexus.work;

import com.nexus.Exception.GithubCannotReachableException;
import com.nexus.util.Const;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;

/**
 * Created by neusone on 2016/10/21.
 */
public class GithubSearchWork {

  Logger logger = Logger.getLogger(GithubSearchWork.class);

  private static ExecutorService executorService =
      Executors.newFixedThreadPool(Const.download_thread_num);

  private int max_search_deep = 3; // 最大搜索路径深度

  private int max_search_folr = 30; // 最大搜索文件夹数量

  private int obtain_folr_count = 0; // 已经获取的文件夹数量

  private int obtain_search_deep = 0; // 已经搜寻的路径深度

  private String github_server = "https://github.com";

  private String mastr_end_path = "/tree/master";

  private String user_folr;

  private String project_folr;

  private boolean if_seached = false;

  private String searching_folr_name;

  private String basePath;

  private String base_save_dir = "D:/something";

  public GithubSearchWork(String init_url) throws IOException, GithubCannotReachableException {
    String searchUrl = init_url.replace("https://github.com", "").replace("http://github.com", "");
    String[] paths = searchUrl.split("/");
    user_folr = "/" + paths[1];
    project_folr = "/" + paths[2];

    basePath = user_folr + project_folr;
    startGithubSearch(github_server + basePath + mastr_end_path);
  }

  public void startGithubSearch(String increasePath)
      throws IOException, GithubCannotReachableException {

    Document doc = null;

    try {
      doc = Jsoup.connect(increasePath).get();
    } catch (Exception e) {
      throw new GithubCannotReachableException();
    }

    obtain_folr_count++;

    // 限制遍历的目录数量
    if (obtain_folr_count >= max_search_folr) {
      return;
    }

    // 限制遍历的目录深度
    if (increasePath.replace(github_server + basePath + mastr_end_path, "")
        .split("/").length > max_search_deep) {
      return;
    }

    if (obtain_folr_count >= 5 && if_seached == false) {
      return;
    }

    // 判断当前页面是出于项目列表页
    Elements ele = doc.select(".repository-content");
    if (ele == null || ele.isEmpty()) {
      return;
    }

    List<String> currentFileList = addFiles(doc);
    if (!currentFileList.isEmpty()) {
      if_seached = true;
    }

    // 开启下载任务
    /*
     * FutureTask<String> future = new FutureTask<String>(new Callable<String>() { public String
     * call() { try { for (int i = 0; i < 5; i++) { Thread.sleep(1000);
     * System.out.println("------------------------"+i); } } catch (InterruptedException e) {
     * System.out.println("InterruptedException1111111"); e.printStackTrace(); } return
     * "call result"; }});
     */
    for (String downLoadUrl : currentFileList) {
      final String _downloadUrl = downLoadUrl;
      final String _increasePath = increasePath;

      executorService.execute(new Runnable() {
        @Override
        public void run() {
          String[] paths = _downloadUrl.split("/");

          try {
            String path = base_save_dir + getCurrentSeachingFolr(_increasePath) + "/";
            File pathFile = new File(path);
            if (!pathFile.exists()) {
              pathFile.mkdirs();
            }


            String fileName = path + java.net.URLDecoder.decode(paths[paths.length - 1], "UTF-8");
            logger.info(String.format("开始下载 =========== %s", fileName));
            FileUtils.copyURLToFile(new URL(_downloadUrl
                .replace(github_server, "https://raw.githubusercontent.com").replace("/blob", "")),
                new File(fileName));
            logger.info(String.format("下载完成 =========== %s", fileName));

          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

      // String[] paths = _downloadUrl.split("/");
      ////
      // String path = base_save_dir +
      // getCurrentSeachingFolr(_increasePath) + "/";
      // File pathFile = new File(path);
      // if (!pathFile.exists()) {
      // pathFile.mkdirs();
      // }
      //
      // String file_name = java.net.URLDecoder.decode(paths[paths.length
      // - 1], "UTF-8");
      //
      //
      // new MultiThread().download(_downloadUrl.replace(github_server,
      // "https://raw.githubusercontent.com").replace("/blob", ""), path,
      // 5);
      //
      // try {
      // Thread.sleep(1000 * 3);
      // } catch (InterruptedException e) {
      // e.printStackTrace();
      // }

    }
    // try
    // {
    // //
    // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
    // while (!executorService.awaitTermination(10, TimeUnit.SECONDS));
    // }
    // catch (InterruptedException e)
    // {
    // e.printStackTrace();
    // }
    // executorService.shutdown();

    // 开始遍历任务
    List<String> currentFolrList = addFolrs(doc);

    for (String folrPath : currentFolrList) {

      startGithubSearch(folrPath);
      System.out.println("遍历目录 == " + folrPath);
    }

  }

  private List<String> addFiles(Document doc) {
    List<String> filesList = new ArrayList();
    ListIterator<Element> folrs = doc.select(".js-navigation-open").listIterator();
    while (folrs.hasNext()) {
      Element ele = folrs.next();
      String name = ele.text();
      if (StringUtils.isNotEmpty(name)
          && (name.endsWith(".pdf") || name.endsWith(".doc") || name.endsWith(".epub")
              || name.endsWith(".mobi") || name.endsWith(".ppt") || name.endsWith(".md"))) {
        filesList.add(github_server + ele.attr("href"));
      }
    }
    return filesList;
  }

  private String getCurrentSeachingFolr(String path) throws UnsupportedEncodingException {
    if (!StringUtils.isNotEmpty(path.replace(github_server + basePath + mastr_end_path, ""))) {
      // 当前是根目录
      return project_folr;
    }
    return project_folr + java.net.URLDecoder
        .decode(path.replace(github_server + basePath + mastr_end_path, ""), "UTF-8");

  }

  private List<String> addFolrs(Document doc) {
    List<String> folrList = new ArrayList();
    ListIterator<Element> folrs =
        doc.select(".js-navigation-item .octicon-file-directory").listIterator();
    while (folrs.hasNext()) {
      Element ele = folrs.next();
      Elements el2 = ele.parent().parent().select(".js-navigation-open");
        if (el2 != null && !el2.isEmpty()) {folrList.add(github_server + el2.attr("href"));
      }
    }
    return folrList;
  }

  public static void main(String args[]) throws IOException, GithubCannotReachableException {
    new GithubSearchWork(
        "https://github.com/shootthepoets/information-technology-ebooks/blob/master/%5BJAVA%5D%5BSCJP%206%20StudyGuide%5D/01.pdf");
  }
}
