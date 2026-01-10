#!/bin/bash
# cleanup.sh

NAMESPACE="lws-demo"

echo "🧹 清理 LeaderWorkerSet 部署..."

# 1. 删除 LeaderWorkerSet
echo "删除 LeaderWorkerSet..."
kubectl -n $NAMESPACE delete leaderworkerset springboot-leader-worker --ignore-not-found

# 2. 删除 Service
echo "删除 Service..."
kubectl -n $NAMESPACE delete svc springboot-leader-worker-service --ignore-not-found
kubectl -n $NAMESPACE delete svc springboot-leader-service --ignore-not-found
kubectl -n $NAMESPACE delete svc springboot-worker-service --ignore-not-found
kubectl -n $NAMESPACE delete svc springboot-leader-worker-headless --ignore-not-found

# 3. 删除 ConfigMap
echo "删除 ConfigMap..."
kubectl -n $NAMESPACE delete configmap springboot-config --ignore-not-found

# 4. 删除 RBAC
echo "删除 RBAC..."
kubectl -n $NAMESPACE delete rolebinding leader-worker-rolebinding --ignore-not-found
kubectl -n $NAMESPACE delete role leader-worker-role --ignore-not-found
kubectl -n $NAMESPACE delete serviceaccount leader-worker-sa --ignore-not-found

# 5. 等待 Pod 完全删除
echo "等待 Pod 删除..."
sleep 10

# 6. 删除命名空间
echo "删除命名空间..."
kubectl delete namespace $NAMESPACE --ignore-not-found

echo "✅ 清理完成!"