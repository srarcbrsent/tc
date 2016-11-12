package com.ysu.zyw.tc.model.api.o.accounts.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToMenu implements Serializable {

    private String id;

    private String description;

    private String name;

    private String link;

    private String structure;

}