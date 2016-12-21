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
public class TcProvince implements Serializable {

    private static final long serialVersionUID = -2800094899251202570L;

    private String id;

    private String name;

}
