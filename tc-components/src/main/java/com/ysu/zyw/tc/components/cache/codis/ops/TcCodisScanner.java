package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
public class TcCodisScanner {

    @Getter
    @Setter
    private List<String> groupList;

    @Resource
    private TcCodisOpsForGroupedValue tcCodisOpsForGroupedValue;

    public void scan() {
        long now = new Date().getTime();
        groupList.forEach(group -> {
            log.info("delete group [{}] expired grouped keys", group);
            tcCodisOpsForGroupedValue.delete(group, 0, now);
        });
    }

}
