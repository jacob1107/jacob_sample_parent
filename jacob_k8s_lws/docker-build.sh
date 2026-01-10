#!/bin/bash

# 构建脚本
VERSION="1.0.0"
IMAGE_NAME="leader-worker-demo"
REGISTRY="your-registry"  # 修改为你的镜像仓库

# 构建应用
#mvn clean package -DskipTests

# 构建Docker镜像
docker build -t ${IMAGE_NAME}:${VERSION} .

# 标记并推送到仓库（可选）
# docker tag ${IMAGE_NAME}:${VERSION} ${REGISTRY}/${IMAGE_NAME}:${VERSION}
# docker push ${REGISTRY}/${IMAGE_NAME}:${VERSION}

echo "Build completed!"