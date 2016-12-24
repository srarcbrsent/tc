package com.ysu.zyw.tc.demos.bin;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TcRegionImportBin {

    // http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/
    public void loadSource() throws IOException {
        try (InputStream is = new ClassPathResource("region.txt").getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            br.lines().forEachOrdered(line -> {
                String code = line.split("     ")[0];
                String name = line.split("     ")[1];

                if (name.contains("　　　")) {
                    print("P" + code.substring(0, 2) + "C" + code.substring(2, 4) + "R" + code.substring(4, 6),
                            "P" + code.substring(0, 2) + "C" + code.substring(2, 4),
                            name.split("　　　")[1].trim());
                } else if (name.contains("　　")) {
                    print("P" + code.substring(0, 2) + "C" + code.substring(2, 4),
                            "P" + code.substring(0, 2),
                            name.split("　　")[1].trim());
                } else if (name.contains("　")) {
                    print("P" + code.substring(0, 2), "CHN", name.split("　")[1].trim());
                }
            });
        }
    }

    private void print(String code, String parentCode, String name) {
        System.out.println("INSERT INTO dd_region ("
                + "id, parent_id, `name`, updated_person, updated_timestamp, created_person, created_timestamp"
                + ") VALUES ("
                + "'" + code + "',"
                + "'" + parentCode + "',"
                + "'" + name + "',"
                + "'" + "00000000000000000000000000000000" + "',"
                + "'" + "2016-12-12 12:12:12.000" + "',"
                + "'" + "00000000000000000000000000000000" + "',"
                + "'" + "2016-12-12 12:12:12.000" + "'"
                + ");");
    }

    public static void main(String[] args) throws IOException {
        new TcRegionImportBin().loadSource();
    }

}
