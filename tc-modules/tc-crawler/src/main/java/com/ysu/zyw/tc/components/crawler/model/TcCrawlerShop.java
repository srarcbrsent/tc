package com.ysu.zyw.tc.components.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcCrawlerShop {

    private String id;

    private String name;

    private int describingRate;

    private int serviceRate;

    private int deliveryRate;

    private int comprehensiveRate;

}
