#!/bin/bash
# deploy-leader-worker.sh

set -e

NAMESPACE="lws-demo"
APP_NAME="springboot-leader-worker"
IMAGE_NAME="leader-worker-demo:1.0.0"

echo "🔧 部署 Spring Boot LeaderWorkerSet 示例"

# 1. 创建命名空间
echo "1. 创建命名空间..."
kubectl apply -f k8s/namespace.yaml

# 2. 创建 ConfigMap
echo "2. 创建 ConfigMap..."
kubectl apply -f k8s/configmap.yaml

# 3. 创建 RBAC
echo "3. 创建 RBAC 权限..."
kubectl apply -f k8s/rbac.yaml

# 4. 部署 LeaderWorkerSet
echo "4. 部署 LeaderWorkerSet..."
kubectl apply -f k8s/leader-worker-set.yaml

# 5. 部署 Service
echo "5. 部署 Service..."
kubectl apply -f k8s/service.yaml

# 6. 等待 Pod 就绪
echo "6. 等待 Pod 就绪..."
sleep 5

echo ""
echo "📊 检查部署状态..."
echo "=============================="

# 检查 LeaderWorkerSet
echo "LeaderWorkerSet 状态:"
kubectl -n $NAMESPACE get leaderworkerset

echo ""
echo "Pod 状态:"
kubectl -n $NAMESPACE get pods -o wide --show-labels

echo ""
echo "Service 状态:"
kubectl -n $NAMESPACE get svc

echo ""
echo "📈 获取访问信息:"
echo "=============================="

# 获取 ClusterIP
LEADER_SERVICE=$(kubectl -n $NAMESPACE get svc springboot-leader-service -o jsonpath='{.spec.clusterIP}')
WORKER_SERVICE=$(kubectl -n $NAMESPACE get svc springboot-worker-service -o jsonpath='{.spec.clusterIP}')
GENERAL_SERVICE=$(kubectl -n $NAMESPACE get svc springboot-leader-worker-service -o jsonpath='{.spec.clusterIP}')

echo "领导者服务: http://$LEADER_SERVICE:8080"
echo "工作者服务: http://$WORKER_SERVICE:8080"
echo "通用服务: http://$GENERAL_SERVICE:8080"

echo ""
echo "🎯 测试命令:"
echo "=============================="
echo "1. 查看所有 Pod 状态:"
echo "   kubectl -n $NAMESPACE get pods -o wide"

echo ""
echo "2. 查看日志:"
echo "   kubectl -n $NAMESPACE logs -l app=$APP_NAME --tail=10"

echo ""
echo "3. 端口转发到领导者:"
echo "   kubectl -n $NAMESPACE port-forward svc/springboot-leader-service 8080:8080"

echo ""
echo "4. 查看事件:"
echo "   kubectl -n $NAMESPACE get events --sort-by='.lastTimestamp'"

echo ""
echo "✅ 部署完成!"