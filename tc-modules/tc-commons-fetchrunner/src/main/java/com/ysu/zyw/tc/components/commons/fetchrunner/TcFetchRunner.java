package com.ysu.zyw.tc.components.commons.fetchrunner;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 启动策略 启动一次 定时器启动 类metaq启动
 * 跳过策略 不跳过 基于zk锁跳过
 * 异常处理策略 日志处理 错误直接退出
 * 连接池管理(fetch线程和run线程)
 *
 * @param <T>
 */
@Slf4j
public abstract class TcFetchRunner<T> {

    public void go() {
        log.info("fetch runner prepare to go ...");
        try {
            List<T> datas = fetch();
            while (CollectionUtils.isNotEmpty(datas)) {
                run(datas);
                datas = fetch();
            }
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("fetch runner reach end ...");
    }

    public abstract List<T> fetch();

    public abstract void run(List<T> dataList);

}
