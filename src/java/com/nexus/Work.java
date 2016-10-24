package com.nexus;

/**
 * Created by neusone on 2016/10/18.
 */
public abstract class Work {

    /**
     * 搜索部分，传入关键词
     * @param word
     */
    public abstract String searchWork(String word);

    /**
     * github部分，主要是检索，遍历目录，匹配，并且下载
     * @param github_visit_url
     */
    public abstract void githubWork(String github_visit_url);
}
