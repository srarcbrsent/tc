package com.ysu.zyw.tc.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcMbg implements Serializable {
    private String id;

    private String name;

    private Boolean enable;

    private LocalDateTime birthday;

    private LocalDate joinDate;

    private LocalTime joinTime;

    private static final long serialVersionUID = 1L;
}