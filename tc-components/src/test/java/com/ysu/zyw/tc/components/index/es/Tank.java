package com.ysu.zyw.tc.components.index.es;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tank {

    private String id;

    private long no;

    private Date createdTimestamp;

}
