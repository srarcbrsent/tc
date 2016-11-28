package com.ysu.zyw.tc.model.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public abstract class TcPitaya extends HashMap<String, Object> {

    private String generatePerson;

    private Date generateTime;

    private String type;

    public TcPitaya(String generatePerson) {
        this.generatePerson = generatePerson;
        this.generateTime = new Date();
        this.type = this.getClass().getName();
    }

}
