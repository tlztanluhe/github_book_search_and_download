package com.nexus.Exception;

/**
 * 在此异常发生之后主程序需要一直等待，知道认为修复 Created by neusone on 2016/10/25.
 */
public class GoogleCannotReachableException extends Exception {

  public GoogleCannotReachableException() {
    super("Google 访问异常");
  }

}
