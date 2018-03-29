package com.reptile.util;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HtmlParserTool {
    //获取一个网站上的链接，filter 用来过滤链接
    public static Set<String> extractLinks (String url, LinkFilter filter) {
        Set<String> links = new HashSet<>();
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("UTF-8");
            //过滤<frame>标签的filter， 用来提取frame标签里的src属性
            NodeFilter frameFilter = (node -> {
                if (node.getText().contains("frame src=")) {
                    return true;
                } else {
                    return false;
                }});
            //OrFilter 来设置过滤<a>标签和<frame>标签
            OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
            //得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag) {
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();
                    if (filter.accept(linkUrl)) {
                        //<a>标签
                        links.add(linkUrl);
                    }
                }else {
                    //提取<frame>标签里的src属性的链接，如<frame src="test.html"/>
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1) {
                        end = frame.indexOf(">");
                    }
                    String frameUrl = frame.substring(5,end -1);
                    if (filter.accept(frameUrl)) {
                        links.add(frameUrl);
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }
    //将HTML页面的某些信息提取出来
    public static void getContent(String url) {
        String company1 = "";
        String name = "";
        String salary = "";
        String experience = "";
        String edu = "";
        String type = "";
        List<String> requirementAndBenefits = new ArrayList<>();

        try {
            Parser parser = new Parser(url);
            parser.setEncoding("UTF-8");
            NodeFilter divFilter = (node -> {
                if ((node instanceof Div && "job-name".equals(((Div) node).getAttribute("class") ))) {
                    return true;
                } else {
                    return false;
                }
            });
            NodeFilter ddFilter = (node -> {
                if ((node instanceof DefinitionListBullet && "job_request".equals(((DefinitionListBullet) node).getAttribute("class")))
                        || (node instanceof DefinitionListBullet && "job_bt".equals(((DefinitionListBullet) node).getAttribute("class")))) {
                    return true;
                } else {
                    return false;
                }
            });
            NodeFilter company = (node -> {
                if ((node instanceof Div && "company".equals(((Div) node).getAttribute("class")))) {
                    return true;
                } else {
                    return false;
                }
            });
            NodeFilter span = (node -> {
                if (node instanceof Span ) {
                    return true;
                } else {
                    return false;
                }
            });
            NodeFilter p = (node -> {
                if (node instanceof Span ) {
                    return true;
                } else {
                    return false;
                }
            });
            OrFilter filter = new OrFilter(divFilter, ddFilter);

            //得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(filter);

            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.elementAt(i).toPlainTextString().replace(" ","").trim());
            }

            } catch (ParserException e) {
                 e.printStackTrace();
             }

    }
}
