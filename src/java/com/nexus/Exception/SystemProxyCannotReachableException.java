package com.nexus.Exception;

/**
 * 系统代理服务器不可用 Created by neusone on 2016/10/25.
 */
public class SystemProxyCannotReachableException extends Exception {

  public SystemProxyCannotReachableException() {
    super("系统代理服务器不可用");
  }
}
