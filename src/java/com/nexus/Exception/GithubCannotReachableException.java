package com.nexus.Exception;

/**
 * github网络访问异常 Created by neusone on 2016/10/25.
 */
public class GithubCannotReachableException extends Exception {

  public GithubCannotReachableException() {
    super("Google 访问异常");
  }
}
