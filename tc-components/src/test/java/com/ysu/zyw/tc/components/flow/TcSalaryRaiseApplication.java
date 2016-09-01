package com.ysu.zyw.tc.components.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcSalaryRaiseApplication implements Serializable {

    @Id
    private String id;

    private String name;

    private long currentSalary;

    private long expectedValue;

}
