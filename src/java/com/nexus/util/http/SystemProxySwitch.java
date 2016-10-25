package com.nexus.util.http;

import com.nexus.Exception.SystemProxyCannotReachableException;
import com.nexus.util.Const;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by root on 2016/10/22.
 */
public class SystemProxySwitch {

	public void switchProxy() throws SystemProxyCannotReachableException {

		Set<String> usedHost = new HashSet<String>();
		while (true) {

			Object[] proxyArray = Const.proxyMap.keySet().toArray();
			String proxyHost = (String) proxyArray[(int) (Math.random()
					* proxyArray.length)];
			String proxyPort = Const.proxyMap.get(proxyHost);

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

			usedHost.add(proxyHost);
			try {
				ValidateHost.validation();
			}
			catch (SystemProxyCannotReachableException e) {
				e.printStackTrace();

				// 此时已经遍历了所有的主机列表,则所有代理均不可用,执行睡眠策略
				if (usedHost.size() == Const.proxyMap.size()) {
					throw e;
				}
			}
		}

	}

}
