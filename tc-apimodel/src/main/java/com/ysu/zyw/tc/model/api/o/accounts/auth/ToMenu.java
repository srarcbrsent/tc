package com.ysu.zyw.tc.model.api.o.accounts.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToMenu implements Serializable {

    private static final long serialVersionUID = 2804888818897797779L;

    private String id;

    private String description;

    private String project;

    private String platform;

    private String name;

    private Integer level;

    private String link;

    private String structure;

    private List<ToMenu> subMenus;

}
