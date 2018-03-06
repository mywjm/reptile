package com.reptile;

import com.reptile.bean.MyCrawler;

public class Main {

    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[] {"http://www.lietu.com"});
    }
}
