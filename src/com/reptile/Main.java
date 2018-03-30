package com.reptile;

import com.reptile.bean.MyCrawler;
import com.reptile.util.DownLoadFile;
import com.reptile.util.HtmlParserTool;
import com.reptile.util.LinkFilter;
import com.reptile.util.WordTool;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[] {"https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order"});
        //https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order
//        DownLoadFile downLoadFile = new DownLoadFile();
//        downLoadFile.saveAsText("C:\\D_Disk\\job.txt","https://www.lagou.com/jobs/4284939.html");
//        String html = downLoadFile.getHtml("https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order");
//        System.out.println(html+"----getHtml");
        /*
        String url = "https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order";
        Runtime runtime =  Runtime.getRuntime();
        InputStream inputStream = runtime.exec("C:\\software\\phantomjs.exe C:\\temp\\parser.js "+url).getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }

        br.close();
        */


//        HtmlParserTool.getContent("https://www.lagou.com/jobs/4284939.html");
    }
}
