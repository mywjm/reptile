package com.reptile.util;


import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;

import java.io.*;

public class DownLoadFile {

    /**
     * 根据URL和网页类型生成需要保存的网页的文件名， 去除URL中的非中文名字符
     */
    public String getFileNameByUrl (String url, String contentType) {
        //移除http
        url = url.substring(7);
        //text/html 类型
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        }else {
            return url.replace("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }

    /**
     * 保存网页字节数组到本地文件， filePath为保存的文件的相对地址
     */
    private void saveToLocal (byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++) {
                out.write(data[i]);
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载URL指向的网页
     *
     */
    public String downloadFile (String url) {
        String filePath = null;
        //1.生成HttpClient 对象并设置参数
        HttpClient httpClient = HttpClients.createDefault();
        //设置HTTP连接超时5s
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        //生成GetMethod对象并设置参数
        HttpGet httpGet = new HttpGet(url);
        //设置请求重试处理

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //判断访问的状态码
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                System.err.println("Method failed : " + httpResponse.getStatusLine());
                filePath = null;
            }
            //处理HTTP响应的内容
            InputStream content = httpResponse.getEntity().getContent();
            byte[] response = content.toString().getBytes();
            //根据网页URL生成保存时的文件名
            filePath = "temp\\" + getFileNameByUrl(url, httpResponse.getEntity().getContentType().getValue());
            System.out.println("-------------------------------"+url+"------------------------------------");
            saveToLocal(response, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return filePath;
    }
}
