package com.reptile.util;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class DownLoadFile {

    /**
     * 根据URL和网页类型生成需要保存的网页的文件名， 去除URL中的非中文名字符
     */
    public String getFileNameByUrl (String url, String contentType) {
        //移除http
        url = url.substring(7);
        //text/html 类型
        if (contentType.contains("html")) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        }else {
            return url.replace("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }

    /**
     * 保存网页字节数组到本地文件， filePath为保存的文件的相对地址
     */
    private void saveToLocal (InputStream in, String filePath, String fileName) {
        try {

            File tempDir = new File(filePath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            File tempFile = new File(tempDir, fileName);
            tempFile.createNewFile();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));
            int len;
            byte[] b = new byte[1024*5];

            while ((len = in.read(b,0,1024)) != -1){
                out.write(b,0,len);
            }
            out.flush();
            out.close();
            in.close();
            System.out.println("----------"+"保存成功"+"----------");
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
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
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
            //根据网页URL生成保存时的文件名
            filePath = "temp\\";
            String fileName = getFileNameByUrl(url, httpResponse.getEntity().getContentType().getValue());
            System.out.println("-------------------------------download from "+url+"  ------------------------------------");
            saveToLocal(content, filePath, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return filePath;
    }

    public void saveAsWord(String path, String url) {
        WordTool wordTool = null;
        try {
            wordTool = new WordTool();
            wordTool.init(path);
            wordTool.writDocument(HtmlParserTool.getContent(url));
            wordTool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveAsText(String path, String url) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path), true);
            FileChannel channel = fos.getChannel();
            String content = HtmlParserTool.getContent(url);
            if (!content.isEmpty()) {
                ByteBuffer byteBuffer = Charset.forName("utf8").encode(content);
                int length = 0;
                while ((length = channel.write(byteBuffer)) != 0) {
                    System.out.println("-------------------------------write  "+url+"  successfully-----and length="+length+"-------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getHtml(String url) {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try{
            process = runtime.exec("C:\\software\\phantomjs.exe C:\\temp\\parser.js "+url);
            InputStream in = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            return sb.toString();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
