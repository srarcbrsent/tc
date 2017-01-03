package com.ysu.zyw.tc.components.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcCrawlerItem {

    private String id;

    private String shopId;

    private String title;

    private String description;

    private long price;

    private int stock;


}
