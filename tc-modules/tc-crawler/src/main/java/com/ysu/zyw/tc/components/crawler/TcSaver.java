package com.ysu.zyw.tc.components.crawler;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.components.crawler.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

@Slf4j
public class TcSaver {

    private static final DruidDataSource DATA_SOURCE = new DruidDataSource();

    static {
        DATA_SOURCE.setUrl("jdbc:mysql://rdb.tc.com:3306/tc?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        DATA_SOURCE.setUsername("tc_develop");
        DATA_SOURCE.setPassword("123456");
    }

    private static final String[] PROVINCES = {
            "P82",
            "P81",
            "P71",
            "P65",
            "P64",
            "P63",
            "P62",
            "P61",
            "P54",
            "P53",
            "P52",
            "P51",
            "P50",
            "P46",
            "P45",
            "P44",
            "P43",
            "P42",
            "P41",
            "P37",
            "P36",
            "P35",
            "P34",
            "P33",
            "P32",
            "P31",
            "P23",
            "P22",
            "P21",
            "P15",
            "P14",
            "P13",
            "P12",
            "P11"
    };

    @SneakyThrows
    public static void doInsert(String shopName,
                                int shopDescRate,
                                int shopServiceRate,
                                int shopDeliveryRate,
                                int shopRate,
                                String itemTitle,
                                String itemDesc,
                                long itemPrice,
                                int itemStock,
                                List<String> itemAttr,
                                List<String> itemCovers,
                                List<String> itemDetails) {
        String shopId = findShopId(shopName);
        String itemId = TcIdGen.upperCaseUuid();
        boolean insertShop = Objects.isNull(shopId);
        shopId = TcIdGen.upperCaseUuid();
        TcCrawlerShop shop = null;
        if (insertShop) {
            shop = new TcCrawlerShop(shopId, shopName, shopDescRate, shopServiceRate,
                    shopDeliveryRate, shopRate);
        }
        TcCrawlerItem item =
                new TcCrawlerItem(itemId, shopId, itemTitle, itemDesc, itemPrice, itemStock);
        List<TcCrawlerItemAttr> attrs = Lists.newArrayList();
        for (int i = 0; i < itemAttr.size(); i++) {
            attrs.add(new TcCrawlerItemAttr(
                    TcIdGen.upperCaseUuid(),
                    itemId,
                    itemAttr.get(i).split(":")[0].trim(),
                    itemAttr.get(i).split(":")[1].trim(),
                    i));
        }
        List<TcCrawlerItemCover> covers = Lists.newArrayList();
        for (int i = 0; i < itemCovers.size(); i++) {
            covers.add(new TcCrawlerItemCover(
                    TcIdGen.upperCaseUuid(),
                    itemId,
                    itemCovers.get(i).trim(),
                    i));
        }
        List<TcCrawlerItemDetail> details = Lists.newArrayList();
        for (int i = 0; i < itemDetails.size(); i++) {
            details.add(new TcCrawlerItemDetail(
                    TcIdGen.upperCaseUuid(),
                    itemId,
                    itemDetails.get(i).trim(),
                    i));
        }

        DruidPooledConnection connection = DATA_SOURCE.getConnection();
        connection.setAutoCommit(false);
        try {
            if (!Objects.isNull(shop)) {
                insertShop(connection, shop);
            }
            insertItem(connection, item);
            insertItemAttr(connection, attrs);
            insertItemCover(connection, covers);
            insertItemDetail(connection, details);

            System.out.println("commit");
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            log.error("===> INSERT ERROR: {}", item.getId(), e);
        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    @SneakyThrows
    private static String findShopId(String shopName) {
        DruidPooledConnection connection = DATA_SOURCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM it_shop WHERE name = ?");
        preparedStatement.setString(1, shopName);
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
    }

    @SneakyThrows
    private static void insertShop(DruidPooledConnection connection, TcCrawlerShop shop) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO it_shop("
                        + "id, "
                        + "name, "
                        + "location, "
                        + "describing_rate, "
                        + "service_rate, "
                        + "delivery_rate, "
                        + "comprehensive_rate, "
                        + "support_cod, "
                        + "support_online_pay, "
                        + "updated_person, "
                        + "updated_timestamp, "
                        + "created_person, "
                        + "created_timestamp"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, shop.getId());
        preparedStatement.setString(2, shop.getName());
        preparedStatement.setString(3, PROVINCES[RandomUtils.nextInt(0, PROVINCES.length)]);
        preparedStatement.setInt(4, shop.getDescribingRate());
        preparedStatement.setInt(5, shop.getServiceRate());
        preparedStatement.setInt(6, shop.getDeliveryRate());
        preparedStatement.setInt(7, shop.getComprehensiveRate());
        preparedStatement.setString(8, RandomUtils.nextBoolean() ? "0" : "1");
        preparedStatement.setString(9, RandomUtils.nextBoolean() ? "0" : "1");
        preparedStatement.setString(10, TcConstant.Base.STR_32_0);
        preparedStatement.setString(11, "2016-12-31 12:12:12");
        preparedStatement.setString(12, TcConstant.Base.STR_32_0);
        preparedStatement.setString(13, "2016-12-31 12:12:12");
        preparedStatement.execute();
    }

    @SneakyThrows
    private static void insertItem(DruidPooledConnection connection, TcCrawlerItem item) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO it_item("
                        + "id, "
                        + "shop_id, "
                        + "title, "
                        + "description, "
                        + "price, "
                        + "stock, "
                        + "sales_volume, "
                        + "fav_volume, "
                        + "comments_volue, "
                        + "delected, "
                        + "updated_person, "
                        + "updated_timestamp, "
                        + "created_person, "
                        + "created_timestamp"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, item.getId());
        preparedStatement.setString(2, item.getShopId());
        preparedStatement.setString(3, item.getTitle());
        preparedStatement.setString(4, item.getDescription());
        preparedStatement.setLong(5, item.getPrice());
        preparedStatement.setInt(6, item.getStock());
        preparedStatement.setInt(7, RandomUtils.nextInt(10, 1500));
        preparedStatement.setInt(8, RandomUtils.nextInt(100, 15000));
        preparedStatement.setInt(9, RandomUtils.nextInt(5, 500));
        preparedStatement.setString(10, "0");
        preparedStatement.setString(11, TcConstant.Base.STR_32_0);
        preparedStatement.setString(12, "2016-12-31 12:12:12");
        preparedStatement.setString(13, TcConstant.Base.STR_32_0);
        preparedStatement.setString(14, "2016-12-31 12:12:12");
        preparedStatement.execute();
    }

    @SneakyThrows
    private static void insertItemAttr(DruidPooledConnection connection, List<TcCrawlerItemAttr> attrs) {
        for (int i = 0; i < attrs.size(); i++) {
            TcCrawlerItemAttr attr = attrs.get(i);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO it_item_attr("
                            + "id, "
                            + "item_id, "
                            + "name, "
                            + "value, "
                            + "sequence, "
                            + "updated_person, "
                            + "updated_timestamp, "
                            + "created_person, "
                            + "created_timestamp"
                            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, attr.getId());
            preparedStatement.setString(2, attr.getItemId());
            preparedStatement.setString(3, attr.getName());
            preparedStatement.setString(4, attr.getValue());
            preparedStatement.setLong(5, attr.getSequence());
            preparedStatement.setString(6, TcConstant.Base.STR_32_0);
            preparedStatement.setString(7, "2016-12-31 12:12:12");
            preparedStatement.setString(8, TcConstant.Base.STR_32_0);
            preparedStatement.setString(9, "2016-12-31 12:12:12");
            preparedStatement.execute();
        }
    }

    @SneakyThrows
    private static void insertItemCover(DruidPooledConnection connection, List<TcCrawlerItemCover> covers) {
        for (int i = 0; i < covers.size(); i++) {
            TcCrawlerItemCover cover = covers.get(i);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO it_item_cover("
                            + "id, "
                            + "item_id, "
                            + "cover, "
                            + "sequence, "
                            + "updated_person, "
                            + "updated_timestamp, "
                            + "created_person, "
                            + "created_timestamp"
                            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cover.getId());
            preparedStatement.setString(2, cover.getItemId());
            preparedStatement.setString(3, cover.getCover());
            preparedStatement.setLong(4, cover.getSequence());
            preparedStatement.setString(5, TcConstant.Base.STR_32_0);
            preparedStatement.setString(6, "2016-12-31 12:12:12");
            preparedStatement.setString(7, TcConstant.Base.STR_32_0);
            preparedStatement.setString(8, "2016-12-31 12:12:12");
            preparedStatement.execute();
        }
    }

    @SneakyThrows
    private static void insertItemDetail(DruidPooledConnection connection, List<TcCrawlerItemDetail> details) {
        for (int i = 0; i < details.size(); i++) {
            TcCrawlerItemDetail detail = details.get(i);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO it_item_detail("
                            + "id, "
                            + "item_id, "
                            + "detail, "
                            + "sequence, "
                            + "updated_person, "
                            + "updated_timestamp, "
                            + "created_person, "
                            + "created_timestamp"
                            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, detail.getId());
            preparedStatement.setString(2, detail.getItemId());
            preparedStatement.setString(3, detail.getDetail());
            preparedStatement.setLong(4, detail.getSequence());
            preparedStatement.setString(5, TcConstant.Base.STR_32_0);
            preparedStatement.setString(6, "2016-12-31 12:12:12");
            preparedStatement.setString(7, TcConstant.Base.STR_32_0);
            preparedStatement.setString(8, "2016-12-31 12:12:12");
            preparedStatement.execute();
        }
    }

}
