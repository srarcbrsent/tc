FROM java:8-jdk

MAINTAINER yaowu.zhang <zhang_yao_wu1993@yeah.net>

ENV

USER monitor

ADD dubbo-monitor-simple-2.8.4-assembly.tar.gz /monitor/
ADD dubbo.properties dubbo-monitor-simple-2.8.4/conf/dubbo.properties

RUN mkdir /monitor/logs

WORKDIR /monitor

VOLUME ["/monitor/logs"]

EXPOSE $ZOO_PORT 2888 3888

CMD ["zkServer.sh"]
