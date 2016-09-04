package com.ysu.zyw.tc.dao.po;

import java.io.Serializable;

@lombok.Data
@lombok.experimental.Accessors(chain = true)
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Uc implements Serializable {
    private String id;

    private static final long serialVersionUID = 1L;
}