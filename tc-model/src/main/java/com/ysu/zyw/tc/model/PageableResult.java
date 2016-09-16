package com.ysu.zyw.tc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageableResult<T> extends Result<T> {

    private int currentPage = -1;

    private int totalPage = -1;

    private int pageSize = -1;

}
