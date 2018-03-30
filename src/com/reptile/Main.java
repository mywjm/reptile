package com.reptile;

import com.reptile.bean.MyCrawler;
import com.reptile.util.DownLoadFile;
import com.reptile.util.HtmlParserTool;
import com.reptile.util.LinkFilter;
import com.reptile.util.WordTool;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;

public class Main {

    public static void main(String[] args) throws Exception {
//        MyCrawler crawler = new MyCrawler();
//        crawler.crawling(new String[] {"https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order"});
        //https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order
        DownLoadFile downLoadFile = new DownLoadFile();
//        downLoadFile.saveAsText("C:\\D_Disk\\job.txt","https://www.lagou.com/jobs/4284939.html");
        downLoadFile.downloadFile("https://www.lagou.com/jobs/list_Java?px=new&city=%E5%B9%BF%E5%B7%9E&district=%E5%A4%A9%E6%B2%B3%E5%8C%BA#order");

//        HtmlParserTool.getContent("https://www.lagou.com/jobs/4284939.html");
    }
}
