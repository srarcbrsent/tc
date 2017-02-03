package com.ysu.zyw.tc.components.commons.fetchrunner;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 启动策略 启动一次 定时器启动 类metaq启动
 * 跳过策略 不跳过 基于zk锁跳过
 * 异常处理策略 日志处理 错误直接退出
 * 连接池管理(fetch线程和run线程)
 * @param <T>
 */
public abstract class TcFetchRunner<T> {

    @Getter
    @Setter
    private TcSkipStrategy tcSkipStrategy = TcNonSkipStrategy.INSTANCE;

    public abstract List<T> fetch();

    public abstract void run(List<T> dataList);

}
