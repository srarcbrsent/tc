FROM java:8-jdk
MAINTAINER yaowu.zhang <zhang_yao_wu1993@yeah.net>

COPY . /usr/src/myapp

WORKDIR /usr/src/myapp
RUN javac Main.java
CMD ["java", "Main"]