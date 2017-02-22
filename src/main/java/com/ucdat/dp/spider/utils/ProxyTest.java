package com.ucdat.dp.spider.utils;


import com.google.common.base.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.util.Random;

class ProxyAuthenticator extends Authenticator {
    private String user, password;

    public ProxyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}

/**
 * 注意：下面代码仅仅实现HTTP请求链接，每一次请求都是无状态保留的，仅仅是这次请求是更换IP的，如果下次请求的IP地址会改变
 * 如果是多线程访问的话，只要将下面的代码嵌入到你自己的业务逻辑里面，那么每次都会用新的IP进行访问，如果担心IP有重复，
 * 自己可以维护IP的使用情况，并做校验。
 */
public class ProxyTest {
    public static final String[] USER_AGENTS = new String[]{
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36"
            ,"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0"
            ,"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36"
            ,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.9.4.2000 Chrome/39.0.2146.0 Safari/537.36"
    };
    public static final Random RANDOM = new Random();
    public static void main(String args[]){
        // 要访问的目标页面
//        String targetUrl = "http://www.dianping.com/search/category/2318/10/g469r10182p1";
        String targetUrl = "http://www.dianping.com/search/category/2315/10/g117c2505p1";
//        String targetUrl = "http://www.baidu.com";
        boolean is404 = false;
            try {
                getPage(targetUrl, targetUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(is404);
    }

    public static Document getPage(String targetUrl, String referUrl) throws Exception {

        // 解析返回数据
        String ret = getRawPage(targetUrl, referUrl);
        if(ret != null) {
            return Jsoup.parse(ret);
        }
        return null;
    }

    public static String getRawPage(String targetUrl, String referUrl) throws Exception{
        // 代理服务器
        String proxyServer = "proxy.abuyun.com";
        int proxyPort = 9020;

        // 代理隧道验证信息
        //北京用
        String proxyUser = "HD30RL9N0333YO9D";
        String proxyPass = "CF08993D737CE96E";
        //测试代理隧道
        //日本用
        proxyUser = "HZ9636CROF33732D";
        proxyPass = "2E634EF01491D3EC";
        URL url = new URL(targetUrl);

        //Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPass));

        // 创建代理服务器地址对象
        //InetSocketAddress addr = new InetSocketAddress(proxyServer, proxyPort);
        // 创建HTTP类型代理对象
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

        // 设置通过代理访问目标页面
        //HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);//proxy);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//proxy);


        // 设置IP切换头
        connection.setRequestProperty("Proxy-Switch-Ip", "yes");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        connection.setRequestProperty("Referer", referUrl);
        connection.setRequestProperty("Host", "http://www.dianping.com");
//        connection.setRequestProperty("Cookie", "TALanguage=ALL");
        connection.setRequestProperty("User-Agent", USER_AGENTS[RANDOM.nextInt(USER_AGENTS.length)]);
        connection.setConnectTimeout(3000);
        System.out.println(connection.getResponseCode());
        byte[] response = readStream(connection.getInputStream());

        return new String(response, "UTF-8");
    }

    /**
     * 将输入流转换成字符串
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;

        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        return outSteam.toByteArray();
    }
}
