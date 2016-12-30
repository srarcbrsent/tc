package com.ysu.zyw.tc.components.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TcHtmlProcessor {

    public static void process() throws IOException {
        File file = new File("C:\\Users\\a\\Desktop\\新建文本文档.txt");
        Document doc = Jsoup.parse(file, StandardCharsets.UTF_8.name());
        Elements p1 = doc.select(".p1 > li > a");
        p1.addAll(doc.select(".p2 > li > a"));
        p1.addAll(doc.select(".p3 > li > a"));
        p1.forEach(element -> System.out.println(element.text()));
    }

    public static void main(String[] args) throws IOException {
        process();
    }

}
