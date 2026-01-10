#FROM docker.m.daocloud.io/openjdk:8u102-jdk
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:8u102-jdk

# 安装 curl 用于健康检查
# 使用 apt-get 而不是 apk
RUN apt-get install -y curl tzdata && \
    rm -rf /var/lib/apt/lists/*

VOLUME /tmp


ENV TZ=Asia/Shanghai

# 添加应用
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 添加启动脚本
RUN echo '#!/bin/sh' > /entrypoint.sh && \
    echo 'exec java -jar /app.jar' >> /entrypoint.sh && \
    chmod +x /entrypoint.sh



ENTRYPOINT ["/entrypoint.sh"]