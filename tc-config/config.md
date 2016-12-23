[TOC]

# 中间件部署详情

## MySQL

| 实例 | MySQL |
| --- | --- |
| 版本 | 5.7 |
| 路径 | /usr/local/mysql |
| 数据目录 | /usr/local/mysql/data |
| 日志文件 | /usr/local/mysql/data/mysqld.local.err |
| 进程文件 | /usr/local/mysql/data/mysqld.local.pid |
| 内部域名 | rdb.tc.com |
| 内部端口 | 3306 |
| 认证信息 | 开发: tc_develop / 123456 生产: tc_product / Rjr7)71qnbldjcvvjw4= |
| 特殊说明 | 1.数据库设置为UTF8编码 |

## Zookeeper

| 实例 | Zookeeper |
| --- | --- |
| 版本 | 3.4.8 |
| 路径 | /Users/zhangyaowu/env/local/zookeeper-3.4.8 |
| 数据目录 | /Users/zhangyaowu/env/data/zookeeper |
| 日志文件 | /Users/zhangyaowu/env/data/zookeeper/zookeeper.out |
| 进程文件 | /Users/zhangyaowu/env/data/zookeeper/zookeeper_server.pid |
| 内部域名 | zk.tc.com |
| 内部端口 | 2181 |
| 特殊说明 | 1.设置maxClientCnxns = 100 2.集群化 |

## Mongodb

| 实例 | Mongodb |
| --- | --- |
| 版本 | ? |
| 路径 | ? |
| 数据目录 | ? |
| 日志文件 | ? |
| 进程文件 | ? |
| 内部域名 | mongo.tc.com |
| 内部端口 | 27017 |
| 认证信息 | 开发: tc_develop / 123456 生产: tc_product / Rjr7)71qnbldjcvvjw4= |
| 特殊说明 | ? |

## Redis

| 实例 | Redis |
| --- | --- |
| 版本 | 3.2.5 |
| 路径 | /Users/zhangyaowu/env/local/redis-3.2.5 |
| 数据目录 | /Users/zhangyaowu/env/data/redis |
| 日志文件 | /Users/zhangyaowu/env/logs/redis/redis_6379.log |
| 进程文件 | /Users/zhangyaowu/env/logs/redis/redis_6379.pid |
| 内部域名 | rds.tc.com |
| 内部端口 | 6379 |
| 认证信息 | 开发: 123456 生产: Rjr7)71qnbldjcvvjw4= |
| 特殊说明 | 1.主从 |

## Codis

| 实例 | Codis |
| --- | --- |
| 版本 | 3.1 |
| 路径 | /Users/zhangyaowu/env/local/codis |
| 数据目录 | /Users/zhangyaowu/env/data/codis |
| 日志文件 | /Users/zhangyaowu/env/logs/codis/* |
| 进程文件 | /Users/zhangyaowu/env/logs/codis/* |
| 内部域名 | cds.tc.com |
| 内部端口 | CodisServer: 6380(master) & 6381(slave) / 6382(master) & 6383(slave)<br/>CodisProxy: 19001/19002<br/>CodisAdmin: 18090<br/>CodisFe: 18088 |
| 认证信息 | 开发: 123456 生产: Rjr7)71qnbldjcvvjw4= |
| 特殊说明 | 1.管理页面 cds.tc.com:18088 2.集群名称: tc |

## Nginx

| 实例 | Nginx |
| --- | --- |
| 版本 | 1.10.1 |
| 路径 | /Users/zhangyaowu/env/local/nginx |
| 数据目录 | /Users/zhangyaowu/env/data/nginx |
| 日志文件 | /Users/zhangyaowu/env/logs/nginx/error.log /Users/zhangyaowu/env/logs/nginx/access.log |
| 进程文件 | /Users/zhangyaowu/env/logs/nginx/nginx.pid |
| 内部端口 | 80 |
| 特殊说明 | 1.sudo权限 |

## MetaQ

| 实例 | MetaQ |
| --- | --- |
| 版本 | 1.4.6.3 |
| 路径 | /Users/zhangyaowu/env/local/metaq |
| 数据目录 | /Users/zhangyaowu/env/data/metaq |
| 日志文件 | /Users/zhangyaowu/env/logs/metaq/* |
| 进程文件 | /Users/zhangyaowu/env/logs/metaq/* |
| 内部域名 | mtq.tc.com |
| 内部端口 | 8120 |
| 特殊说明 | 1.管理页面 mtq.tc.com:8120 |

## DubboAdmin

| 实例 | DubboAdmin |
| --- | --- |
| 版本 | LATEST |
| 路径 | /Users/zhangyaowu/env/local/DubboAdmin |
| 数据目录 | /Users/zhangyaowu/env/data/DubboAdmin |
| 日志文件 | /Users/zhangyaowu/env/logs/DubboAdmin/* |
| 进程文件 | /Users/zhangyaowu/env/logs/DubboAdmin/* |
| 内部域名 | dubbo.admin.tc.com |
| 内部端口 | 12005 12080 12009 |
| 特殊说明 | 1.管理页面 dubbo.admin.tc.com:12080 |

## DubboMonitor

| 实例 | DubboMonitor |
| --- | --- |
| 版本 | LATEST |
| 路径 | /Users/zhangyaowu/env/local/DubboMonitor |
| 数据目录 | /Users/zhangyaowu/env/data/DubboMonitor |
| 日志文件 | /Users/zhangyaowu/env/logs/DubboMonitor/* |
| 进程文件 | /Users/zhangyaowu/env/logs/DubboMonitor/* |
| 内部域名 | dubbo.monitor.tc.com |
| 内部端口 | 13005 13080 13009 |
| 特殊说明 | 1.管理页面 dubbo.monitor.tc.com:13080 |

# 应用部署详情

| 实例 | TcApiImpl-01 |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 日志文件 | tomcat.log |
| 进程文件 | tomcat.pid |
| 内部端口 | 21005 21080 21009 |
| 特殊说明 | 1.域名指向: api.tc.com |

| 实例 | TcApiImpl-02 |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 日志文件 | tomcat.log |
| 进程文件 | tomcat.pid |
| 内部端口 | 21105 21180 21109 |
| 特殊说明 | 1.域名指向: api.tc.com |

| 实例 | TcOpenApi-01 |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 日志文件 | tomcat.log |
| 进程文件 | tomcat.pid |
| 内部端口 | 22005 22080 22009 |
| 特殊说明 | 1.域名指向: openapi.tc.com |

| 实例 | TcOpenApi-02 |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 日志文件 | tomcat.log |
| 进程文件 | tomcat.pid |
| 内部端口 | 22105 22180 22109 |
| 特殊说明 | 1.域名指向: openapi.tc.com |

| 实例 | TcStaticResources |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 特殊说明 | 1.域名指向: static.tc.com |

| 实例 | TcStaticHtml |
| --- | --- |
| 版本 | LATEST |
| 路径 | Docker |
| 特殊说明 | 1.域名指向: www.tc.com, tc.com |


