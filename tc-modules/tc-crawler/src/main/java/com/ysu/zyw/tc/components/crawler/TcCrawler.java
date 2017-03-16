package com.ysu.zyw.tc.components.crawler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import com.ysu.zyw.tc.base.utils.TcUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TcCrawler implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void process(Page page) {
        if (isTaobaoItemPage(page)) {
            log.info("hit taobao item page");
            TcUtils.doQuietly(() -> {
                String shopName = extractShopName(page);
                int shopDescRate = extractShopDescRate(page);
                int shopServiceRate = extractShopServiceRate(page);
                int shopDeliveryRate = extractShopDeliveryRate(page);
                int shopRate = extractShopRate(page);

                String itemTitle = extractItemTitle(page);
                String itemDesc = extractItemDesc(page);
                long itemPrice = extractItemPrice(page);
                int itemStock = extractItemStock(page);
                List<String> itemAttr = extractItemAttrs(page);
                List<String> itemCovers = extractItemPics(page);
                List<String> itemDetails = extractItemDetails(page);

                TcSaver.doInsert(
                        shopName,
                        shopDescRate,
                        shopServiceRate,
                        shopDeliveryRate,
                        shopRate,
                        itemTitle,
                        itemDesc,
                        itemPrice,
                        itemStock,
                        itemAttr,
                        itemCovers,
                        itemDetails);
            });
        } else if (isTaobaoSearchPage(page)) {
            log.info("hit taobao search page");
            addLoops(page);
        }
    }

    private List<String> extractItemDetails(Page page) {
        String detailUrl = page.getHtml().regex("//tds.alicdn.com/json/item_imgs.htm.+?,").get();
        detailUrl = detailUrl.substring(0, detailUrl.length() - 2);
        detailUrl = "https:" + detailUrl;
        return restTemplate.execute(
                detailUrl, HttpMethod.GET, null, clientHttpResponse -> {
                    String text = IOUtils.toString(clientHttpResponse.getBody(), StandardCharsets.UTF_8);
                    String response = text.replace("$callback(", "").replace("})", "}");
                    HashMap<String, Object> hashMap = TcSerializationUtils.readJson(response,
                            new TypeReference<HashMap<String, Object>>() {
                            });
                    List<String> pics1 = Lists.newArrayList();
                    hashMap.entrySet().forEach(entry -> {
                        if (!(entry.getKey().equals("success")
                                || entry.getKey().equals("size")
                                || entry.getKey().equals("conflict")
                                || entry.getKey().equals("req"))) {
                            pics1.add("https://img.alicdn.com/imgextra/i3/2928049528/" + entry.getKey());
                        }
                    });
                    return pics1;
                });
    }

    private List<String> extractItemPics(Page page) {
        return page.getHtml()
                .css("#J_UlThumb > li > div > a > img", "data-src")
                .all()
                .stream()
                .map(url -> "https:" + url.replace("_50x50", "").trim())
                .collect(Collectors.toList());
    }

    private List<String> extractItemAttrs(Page page) {
        return page.getHtml()
                .css("#attributes > ul")
                .xpath("//ul/li/text()")
                .all()
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private int extractShopRate(Page page) {
        int rate = page.getHtml()
                .css("#J_ShopInfo div.tb-shop-info-hd > div.tb-shop-rank > dl > dd > a")
                .xpath("//a/i").all().size();
        boolean crown = page.getHtml()
                .css("#J_ShopInfo div.tb-shop-info-hd > div.tb-shop-rank.tb-rank-crown").all().size() == 1;
        boolean cap = page.getHtml()
                .css("#J_ShopInfo div.tb-shop-info-hd > div.tb-shop-rank.tb-rank-cap").all().size() == 1;
        if (cap) {
            rate = rate * 5;
        } else if (crown) {
            rate = rate * 25;
        }
        return rate;
    }

    private int extractShopDeliveryRate(Page page) {
        String sRate = page.getHtml()
                .css("#J_ShopInfo > div > div.tb-shop-info-bd > div > dl:nth-child(3) > dd > a")
                .xpath("//a/text()")
                .get()
                .trim();
        return Integer.parseInt(sRate.replace(".", ""));
    }

    private int extractShopServiceRate(Page page) {
        String sRate = page.getHtml()
                .css("#J_ShopInfo > div > div.tb-shop-info-bd > div > dl:nth-child(2) > dd > a")
                .xpath("//a/text()")
                .get()
                .trim();
        return Integer.parseInt(sRate.replace(".", ""));
    }

    private int extractShopDescRate(Page page) {
        String sRate = page.getHtml()
                .css("#J_ShopInfo > div > div.tb-shop-info-bd > div > dl:nth-child(1) > dd > a")
                .xpath("//a/text()")
                .get()
                .trim();
        return Integer.parseInt(sRate.replace(".", ""));
    }

    private String extractShopName(Page page) {
        return page.getHtml()
                .css("#J_ShopInfo > div > div.tb-shop-info-hd > div.tb-shop-name > dl > dd > strong > a")
                .xpath("//a/text()")
                .get()
                .trim();
    }

    private int extractItemStock(Page page) {
        return RandomUtils.nextInt(0, 9999);
    }

    private String extractItemDesc(Page page) {
        return page.getHtml().css("#J_Title > p").xpath("//p/text()").get().trim();
    }

    private String extractItemTitle(Page page) {
        return page.getHtml().css("#J_Title > h3").xpath("//h3/text()").get().trim();
    }

    private boolean isTaobaoItemPage(Page page) {
        return page.getRequest().getUrl().contains("item.taobao.com")
                || page.getRequest().getUrl().contains("click.simba.taobao.com");
    }

    private boolean isTaobaoSearchPage(Page page) {
        return page.getRequest().getUrl().contains("s.taobao.com");
    }

    private long extractItemPrice(Page page) {
        String price = page.getHtml().css("#J_PromoPriceNum").xpath("//em/text()").get();
        if (StringUtils.isEmpty(price)) {
            price = page.getHtml().css("#J_StrPrice > em.tb-rmb-num").xpath("//em/text()").get();
        }
        if (price.contains("-")) {
            price = price.split("-")[0].trim();
        }
        return Long.parseLong(price.replace(".", ""));
    }

    private void addLoops(Page page) {
        try {
            List<String> auctions = page.getJson()
                    .removePadding("jsonp1887")
                    .jsonPath("$.mods.itemlist.data.auctions")
                    .all();

            auctions.forEach(auction -> {
                try {
                    HashMap<String, Object> tcItem = TcSerializationUtils.readJson(auction,
                            new TypeReference<HashMap<String, Object>>() {
                            });
                    if (tcItem.get("title").toString().length() > 5) {
                        String nextKeyword = tcItem.get("title").toString().substring(0, 3);
                        // add next chain
                        page.addTargetRequest(buildNextRequest(nextKeyword));
                    }
                    // add detail page
                    String detailUrl = tcItem.get("detail_url").toString();
                    page.addTargetRequest(detailUrl);
                } catch (Exception e) {
                    // ignore
                }
            });
        } catch (Exception e) {
            log.debug("", e);
        }
    }

    private static String buildNextRequest(String keyword) {
        return "https://s.taobao.com/search?data-key=s&data-value=88&ajax=true&_ksTS=1483104243103_1886&"
                + "callback=jsonp1887&q=" + keyword.replace("<", "").replace(">", "")
                + "&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&spm=a1z10.1.1996643285.d"
                + "4916901&initiative_id=shopz_20161230&bcoffset=-7"
                + "&ntoffset=-7&p4ppushleft=2,48&s=132";
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws InterruptedException {
        Spider.create(new TcCrawler())
                .addUrl(buildNextRequest("乐视"))
                .addUrl(buildNextRequest("西瓜"))
                .addUrl(buildNextRequest("cosplay"))
                .thread(15)
                .run();
    }

}
