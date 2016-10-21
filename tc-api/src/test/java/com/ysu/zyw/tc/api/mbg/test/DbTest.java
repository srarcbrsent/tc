package com.ysu.zyw.tc.api.mbg.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.tools.TcTuple;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DbTest {

    private DruidDataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://rdb.tc.com:3306/tc_platform");
        Properties properties = new Properties();
        properties.put("useUnicode", Boolean.TRUE.toString());
        properties.put("characterEncoding", "UTF-8");
        properties.put("zeroDateTimeBehavior", "convertToNull");
        properties.put("useSSL", Boolean.FALSE.toString());
        dataSource.setConnectProperties(properties);
        dataSource.setUsername("tc_develop");
        dataSource.setPassword("123456");
    }

    @Test
    public void test1() throws IOException, BiffException, SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tmp_goods VALUES (?, ?)");

        InputStream stream = new FileInputStream("/home/zhangyaowu/桌面/22.xls");
        Workbook rwb = Workbook.getWorkbook(stream);
        Sheet sheet = rwb.getSheet(0);
        int rows = sheet.getRows();
        List<TcTuple<String, String>> l = new ArrayList<>(2600);
        for (int i = 0; i < rows; i++) {
            TcTuple<String, String> tcTuple = new TcTuple<>(sheet.getCell(0, i).getContents(), sheet
                    .getCell(1, i).getContents().toLowerCase().trim());
            l.add(tcTuple);
            System.out.println(tcTuple);
            preparedStatement.setString(1, tcTuple.getFirstValue());
            preparedStatement.setString(2, tcTuple.getSecondValue());
            // preparedStatement.execute();
        }
        System.out.println(l.size());
    }

    @Test
    public void test2() throws IOException, SQLException {
        InputStream stream = new FileInputStream("/home/zhangyaowu/桌面/1");
        List<String> strings = IOUtils.readLines(stream, "UTF-8");
        String id = null;
        String pid1 = null;
        String pid2;
        String name;
        String hierarchy;
        int level;
        int l1 = 0;
        int l2 = 0;
        int l3 = 0;
        for (int i = 0; i < strings.size(); i++) {
            String s1 = strings.get(i);
            if (s1.trim().length() < 6) {
                ++l1;
                id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                pid1 = id;
                name = s1.trim();
                hierarchy = String.valueOf(lr(l1));
                level = 1;
                System.out.println(id + " " + name + " " + hierarchy + " " + level + " " + null);
                insertClassify(id, name, null, hierarchy, level);
                l2 = 0;
            } else {
                String[] s2 = s1.trim().split("：");
                ++l2;
                id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                name = s2[0].trim();
                hierarchy = String.valueOf(lr(l1) + "-" + lr(l2));
                level = 2;
                System.out.println("\t" + id + " " + name + " " + hierarchy + " " + level + " " + pid1);
                insertClassify(id, name, pid1, hierarchy, level);
                l3 = 0;
                String[] s3 = s2[1].split("、");
                pid2 = id;
                for (int j = 0; j < s3.length; j++) {
                    ++l3;
                    id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                    name = s3[j].trim();
                    hierarchy = String.valueOf(lr(l1) + "-" + lr(l2) + "-" + lr(l3));
                    level = 3;
                    System.out.println("\t\t" + id + " " + name + " " + hierarchy + " " + level + " " + pid2);
                    insertClassify(id, name, pid2, hierarchy, level);
                }
            }
        }
    }

    @Test
    public void test3() {
        String source = "/home/zhangyaowu/下载/data/pp";
        String dest = "/home/zhangyaowu/下载/data/dest";
        File sourceFile = new File(source);
        File[] files = sourceFile.listFiles();
        Arrays.stream(files).forEach(file -> {
            String oName = file.getName().split("\\.")[0];
            System.out.println(oName);
            try {
                IOUtils.copy(
                        new FileInputStream(file),
                        new FileOutputStream(new File(dest + "/" + oName + ".jpg"))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void test4() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        }
    }

    @Test
    public void test5() throws SQLException {
        // find goods
        List<TcTuple<String, String>> itemList = findGoods();

        // leaf classify
        List<String> classifyList = findLeafClassify();

        // pic
        List<String> picList = findPic();

        // tag
        List<String> tagList = findTag();

        itemList.parallelStream().forEach(item -> {
            try {
                String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
                String name = item.getFirstValue();
                int price = (RandomUtils.nextInt(7000) + 1) * 100;
                int stock = (RandomUtils.nextInt(7000) + 100);
                String sellerId = "00000000000000000000000000000000";
                String state = "ONSALE";
                String classifyId = classifyList.get(RandomUtils.nextInt(classifyList.size()));

                String itemDetail = item.getSecondValue();

                DruidPooledConnection connection = dataSource.getConnection();

                // item
                PreparedStatement preparedStatement1 = connection.prepareStatement(
                        "INSERT INTO tm_item VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );

                Date now = new Date();
                preparedStatement1.setString(1, id);
                preparedStatement1.setString(2, name);
                preparedStatement1.setInt(3, price);
                preparedStatement1.setInt(4, stock);
                preparedStatement1.setString(5, sellerId);
                preparedStatement1.setString(6, state);
                preparedStatement1.setString(7, classifyId);
                preparedStatement1.setString(8, "00000000000000000000000000000000");
                preparedStatement1.setDate(9, new java.sql.Date(now.getTime()));
                preparedStatement1.setString(10, "00000000000000000000000000000000");
                preparedStatement1.setDate(11, new java.sql.Date(now.getTime()));
                preparedStatement1.execute();

                // item detail
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                        "INSERT INTO tm_item_detail VALUES(?, ?, ?, ?, ?, ?)"
                );

                preparedStatement2.setString(1, id);
                preparedStatement2.setString(2, itemDetail);
                preparedStatement2.setString(3, "00000000000000000000000000000000");
                preparedStatement2.setDate(4, new java.sql.Date(now.getTime()));
                preparedStatement2.setString(5, "00000000000000000000000000000000");
                preparedStatement2.setDate(6, new java.sql.Date(now.getTime()));
                preparedStatement2.execute();


                int c = RandomUtils.nextInt(6) + 5;
                for (int i = 0; i < c; i++) {
                    // item pic
                    PreparedStatement preparedStatement3 = connection.prepareStatement(
                            "INSERT INTO tm_item_pic VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
                    );

                    preparedStatement3.setString(1, UUID.randomUUID().toString().toUpperCase().replace("-", ""));
                    preparedStatement3.setString(2, id);
                    preparedStatement3.setString(3, "http://static.tc.com/resources/item/" + picList.get(RandomUtils.nextInt(picList.size())));
                    if (i == 0) {
                        preparedStatement3.setString(4, "1");
                    } else {
                        preparedStatement3.setString(4, "0");
                    }
                    preparedStatement3.setString(5, "00000000000000000000000000000000");
                    preparedStatement3.setDate(6, new java.sql.Date(now.getTime()));
                    preparedStatement3.setString(7, "00000000000000000000000000000000");
                    preparedStatement3.setDate(8, new java.sql.Date(now.getTime()));
                    preparedStatement3.execute();
                }

                // tag
                int d = RandomUtils.nextInt(3) + 1;
                for (int i = 0; i < d; i++) {
                    // item pic
                    PreparedStatement preparedStatement4 = connection.prepareStatement(
                            "INSERT INTO tm_item_icon_mapping VALUES(?, ?, ?, ?, ?, ?, ?)"
                    );

                    preparedStatement4.setString(1, UUID.randomUUID().toString().toUpperCase().replace("-", ""));
                    preparedStatement4.setString(2, id);
                    preparedStatement4.setString(3, tagList.get(RandomUtils.nextInt(tagList.size())));
                    preparedStatement4.setString(4, "00000000000000000000000000000000");
                    preparedStatement4.setDate(5, new java.sql.Date(now.getTime()));
                    preparedStatement4.setString(6, "00000000000000000000000000000000");
                    preparedStatement4.setDate(7, new java.sql.Date(now.getTime()));
                    preparedStatement4.execute();
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void test6() throws SQLException {
        String base = "curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' " +
                "--header 'X-ApiVersion: 1.0' -d '{\n" +
                "  \"account\": \"${account}\",\n" +
                "  \"email\": \"${email}\",\n" +
                "  \"emailActivated\": false,\n" +
                "  \"mobile\": \"${mobile}\",\n" +
                "  \"mobileActivated\": false,\n" +
                "  \"name\": \"${name}\",\n" +
                "  \"password\": \"1234567890123456789012345678901234567890123456789012345678901234\",\n" +
                "  \"selfDescribing\": \"${describing}\",\n" +
                "  \"signupPlatform\": \"PC_PLATFORM\",\n" +
                "  \"supCod\": false,\n" +
                "  \"supWeixin\": false,\n" +
                "  \"supZhifubao\": false,\n" +
                "  \"weixinAccount\": \"${weixinAccount}\",\n" +
                "  \"zhifubaoAccount\": \"${zhifubaoAccount}\"\n" +
                "}' 'http://api.tc.com/accounts/create_account'";

        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT name FROM tmp_company");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> nameList = Lists.newArrayList();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            nameList.add(id);
        }

        nameList.forEach(name -> {
            String s = base.replace("${account}", RandomStringUtils.randomAlphabetic(6 + RandomUtils.nextInt(6)))
                    .replace("${email}", RandomStringUtils.randomAlphabetic(6 + RandomUtils.nextInt(10)) + "@yeah.net")
                    .replace("${mobile}", "188" + RandomStringUtils.randomNumeric(8))
                    .replace("${name}", name)
                    .replace("${describing}", "Hello World, Every One.")
                    .replace("${weixinAccount}", "150" + RandomStringUtils.randomNumeric(8))
                    .replace("${zhifubaoAccount}", "138" + RandomStringUtils.randomNumeric(8));
            System.out.println(s);
        });
    }

    @Test
    public void test7() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM tm_item");
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        List<String> itemIds = Lists.newArrayList();
        while (resultSet1.next()) {
            String id = resultSet1.getString(1);
            itemIds.add(id);
        }
        System.out.println(itemIds.size());

        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "SELECT id FROM mb_account");
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        List<String> sellerIds = Lists.newArrayList();
        while (resultSet2.next()) {
            String id = resultSet2.getString(1);
            sellerIds.add(id);
        }
        System.out.println(sellerIds.size());


        for (String itemId : itemIds) {
            PreparedStatement preparedStatement3 = connection.prepareStatement(
                    "UPDATE tm_item SET seller_id = ? WHERE id = ?");
            preparedStatement3.setString(1, sellerIds.get(RandomUtils.nextInt(sellerIds.size())));
            preparedStatement3.setString(2, itemId);
            int resultSet3 = preparedStatement3.executeUpdate();
        }
    }

    private static final MultiValueMap<String, String> PROPERTIES = new LinkedMultiValueMap<>();

    @Test
    public void test8() throws SQLException {
        PROPERTIES.put("上市年份季节", Arrays.asList(
                "2012年春季",
                "2012年秋季",
                "2013年春季",
                "2013年秋季",
                "2014年春季",
                "2014年秋季",
                "2015年春季",
                "2015年秋季",
                "2016年春季",
                "2016年秋季")
        );
        PROPERTIES.put("品牌", Arrays.asList(
                "Moeyu/萌羽",
                "Xiaomi/小米",
                "AS/爱特斯",
                "赢虎/YOUTHOW",
                "boelginol/步津浓",
                "Philips/飞利浦",
                "Asus/华硕",
                "鑫福华",
                "干果",
                "奇美",
                "Haagen-Dazs/哈根达斯",
                "Onda/昂达",
                "Juicy Couture",
                "菲灵",
                "Joyoung/九阳",
                "聚元",
                "Vero Moda",
                "Givenchy/纪梵希",
                "兰歌",
                "J.ESTINA",
                "宁美国度",
                "裸心安家",
                "依博源",
                "初语",
                "Gap",
                "ELLE",
                "feiyue/飞跃",
                "都市丽人",
                "Kenneth Cole",
                "other/其他",
                "RMO＆JUL",
                "Meizu/魅族")
        );
        PROPERTIES.put("颜色分类", Arrays.asList(
                "麻花灰",
                "深灰色",
                "黑色",
                "咖啡",
                "绿色",
                "紫色",
                "紫橙色",
                "红色")
        );
        PROPERTIES.put("产地", Arrays.asList(
                "中国",
                "美国",
                "欧美",
                "日本",
                "海外")
        );
        PROPERTIES.put("风格", Arrays.asList(
                "休闲",
                "可爱",
                "学院",
                "简约",
                "运动",
                "时尚潮流",
                "街头",
                "日系")
        );
        PROPERTIES.put("大小", Arrays.asList(
                "S",
                "M",
                "L",
                "XL",
                "XXL")
        );
        PROPERTIES.put("出售状态", Arrays.asList(
                "现货",
                "预售")
        );
        PROPERTIES.put("型号", Arrays.asList(
                "TRJ50",
                "TRS30",
                "TSD10",
                "KC4729",
                "KC3994",
                "KC264",
                "TSD50",
                "TDX30",
                "TWD10",
                "TWD15")
        );
        PROPERTIES.put("适用对象", Arrays.asList(
                "学生",
                "青年",
                "中年",
                "老年")
        );

        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM tm_item");
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        List<String> itemIds = Lists.newArrayList();
        while (resultSet1.next()) {
            String id = resultSet1.getString(1);
            itemIds.add(id);
        }
        System.out.println(itemIds.size());

        for (String itemId : itemIds) {
            int sequence = 10;
            for (Map.Entry<String, List<String>> entry : PROPERTIES.entrySet()) {
                if (RandomUtils.nextInt(10) < 5) {
                    continue;
                }

                String key = entry.getKey();
                String value = entry.getValue().get(RandomUtils.nextInt(entry.getValue().size()));
                String valueType = "STRING";

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO tm_item_property VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                Date now = new Date();
                preparedStatement.setString(1, TcIdWorker.upperCaseUuid());
                preparedStatement.setString(2, itemId);
                preparedStatement.setString(3, key);
                preparedStatement.setString(4, value);
                preparedStatement.setString(5, valueType);
                preparedStatement.setInt(6, sequence);
                preparedStatement.setString(7, "00000000000000000000000000000000");
                preparedStatement.setDate(8, new java.sql.Date(now.getTime()));
                preparedStatement.setString(9, "00000000000000000000000000000000");
                preparedStatement.setDate(10, new java.sql.Date(now.getTime()));
                preparedStatement.execute();

                sequence += 10;
            }
        }
    }

    private List<String> findTag() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM tm_item_icon");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> list = Lists.newArrayList();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        System.out.println(list.size());
        return list;
    }

    private List<String> findPic() {
        String dest = "/home/zhangyaowu/下载/data/dest";
        File destFile = new File(dest);
        List<String> list = Arrays.stream(destFile.listFiles()).map(File::getName).collect(Collectors.toList());
        System.out.println(list.size());
        return list;
    }


    private List<String> findLeafClassify() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM tm_item_classify WHERE level = 3");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> list = Lists.newArrayList();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        System.out.println(list.size());
        return list;
    }

    private List<TcTuple<String, String>> findGoods() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM tmp_goods");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<TcTuple<String, String>> list = Lists.newArrayList();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            String detail = resultSet.getString(2);
            TcTuple<String, String> stringStringTcTuple = new TcTuple<>(name, detail);
            list.add(stringStringTcTuple);
        }
        System.out.println(list.size());
        return list;
    }

    private String lr(int l) {
        if (l < 10) {
            return "L0" + l;
        } else {
            return String.valueOf("L" + l);
        }
    }

    private void insertClassify(String id, String name, String pid, String hierarchy, int level) throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tm_item_classify VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        Date now = new Date();
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, pid);
        preparedStatement.setString(4, hierarchy);
        preparedStatement.setInt(5, level);
        preparedStatement.setString(6, "00000000000000000000000000000000");
        preparedStatement.setDate(7, new java.sql.Date(now.getTime()));
        preparedStatement.setString(8, "00000000000000000000000000000000");
        preparedStatement.setDate(9, new java.sql.Date(now.getTime()));
        preparedStatement.execute();
        connection.close();
    }


}
