package com.ysu.zyw.tc.components.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcCrawlerItemDetail {

    private String id;

    private String itemId;

    private String detail;

    private int sequence;

}
