package com.reptile.util;


import com.heavenlake.wordapi.Document;

/**
 * @author Qingquan.huang@accenture.com
 * @date 3/30/2018 10:38 AM
 */
public class WordTool {
    private Document document = new Document();

    public WordTool() throws Exception {
    }

    public void init(String path) throws Exception {
        document.newDoc(path);

    }

    public void writDocument(String text) throws Exception {
        document.insertln(text);
    }

    public void close() throws Exception {
        document.close(true);
    }


}
