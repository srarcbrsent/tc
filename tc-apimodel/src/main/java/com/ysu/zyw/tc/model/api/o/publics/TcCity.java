package com.ysu.zyw.tc.model.api.o.publics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcCity implements Serializable {

    private static final long serialVersionUID = -6778902834090224369L;

    private String id;

    private String name;

    private String provinceId;

    private String provinceName;

}
