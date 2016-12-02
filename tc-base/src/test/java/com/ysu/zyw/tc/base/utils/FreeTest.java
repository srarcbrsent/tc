package com.ysu.zyw.tc.base.utils;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.Test;

import java.util.ArrayList;

public class FreeTest {

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    public static class Company {
        private String id;
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String id;

        private String companyId;

        private Company company;
    }

    @Test
    public void test01() {
        ArrayList<Company> companies =
                Lists.newArrayList(new Company("1"), new Company("2"));
        ArrayList<User> users =
                Lists.newArrayList(new User().setId("3").setCompanyId("1"), new User().setId("4").setCompanyId("2"));

        TcUtils.match(
                companies,
                Company::getId,
                users,
                User::getCompanyId,
                (company, user) -> user.setCompany(company)
        );
        System.out.println(users);
    }

    @Test
    public void test02() {
        System.out.println(TcFormatUtils.format("helo [{}] [{}]", 1,
                new User().setId("3").setCompanyId("1"), new User().setId("4").setCompanyId("2")));
    }

}
