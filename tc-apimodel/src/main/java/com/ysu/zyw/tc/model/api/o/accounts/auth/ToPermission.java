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
public class ToPermission implements Serializable {

    private static final long serialVersionUID = 1198250649405879428L;

    private String id;

    private String description;

    private Integer dataPms;

}
