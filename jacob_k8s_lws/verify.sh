#!/bin/bash

# 验证脚本
NAMESPACE="lws-demo"

echo "1. Checking LeaderWorkerSet status..."
kubectl -n $NAMESPACE get leaderworkerset

echo "2. Checking pods..."
kubectl -n $NAMESPACE get pods -o wide

echo "3. Checking services..."
kubectl -n $NAMESPACE get svc

echo "4. Getting leader pod..."
LEADER_POD=$(kubectl -n $NAMESPACE get pods -l role=leader -o jsonpath='{.items[0].metadata.name}')
echo "Leader Pod: $LEADER_POD"

echo "5. Testing leader service..."
kubectl -n $NAMESPACE port-forward svc/springboot-leader-service 8080:8080 &
PORT_FORWARD_PID=$!
sleep 3

echo "6. Testing endpoints..."
echo "=== Testing /api/health ==="
curl -s http://localhost:8080/api/health | jq .

echo -e "\n=== Testing /api/leader/status ==="
curl -s http://localhost:8080/api/leader/status | jq .

echo -e "\n=== Testing /api/worker/tasks ==="
curl -s http://localhost:8080/api/worker/tasks | jq .

echo -e "\n7. Testing all pods..."
for pod in $(kubectl -n $NAMESPACE get pods -l app=springboot-leader-worker -o jsonpath='{.items[*].metadata.name}'); do
  echo -e "\n=== Pod: $pod ==="
  kubectl -n $NAMESPACE exec $pod -- curl -s http://localhost:8080/api/health
done

# 清理端口转发
kill $PORT_FORWARD_PID 2>/dev/null

echo -e "\nVerification completed!"
