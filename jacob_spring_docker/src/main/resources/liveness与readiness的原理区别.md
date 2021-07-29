## liveness与readiness的原理区别

探测方式相同，那么liveness与readiness有什么区别？首先，二者能够起到的作用不同。

```go
func (m *kubeGenericRuntimeManager) computePodContainerChanges(pod *v1.Pod, podStatus *kubecontainer.PodStatus) podContainerSpecChanges {
        ......
        liveness, found := m.livenessManager.Get(containerStatus.ID)
		if !found || liveness == proberesults.Success {
			changes.ContainersToKeep[containerStatus.ID] = index
			continue
		}
        ......
```

liveness主要用来确定何时重启容器。liveness探测的结果会存储在livenessManager中。
kubelet在syncPod时，发现该容器的liveness探针检测失败时，会将其加入待启动的容器列表中，在之后的操作中会重新创建该容器。

readiness主要来确定容器是否已经就绪。只有当Pod中的容器都处于就绪状态，也就是pod的condition里的Ready为true时，kubelet才会认定该Pod处于就绪状态。而pod是否处于就绪状态的作用是控制哪些Pod应该作为service的后端。如果Pod处于非就绪状态，那么它们将会被从service的endpoint中移除。

```go
func (m *manager) SetContainerReadiness(podUID types.UID, containerID kubecontainer.ContainerID, ready bool) {
	    ......
    	containerStatus.Ready = ready
        ......
    	readyCondition := GeneratePodReadyCondition(&pod.Spec, status.ContainerStatuses, status.Phase)
    	......
    	m.updateStatusInternal(pod, status, false)
}
```

readiness检查结果会通过SetContainerReadiness函数，设置到pod的status中，从而更新pod的ready condition。

liveness和readiness除了最终的作用不同，另外一个很大的区别是它们的初始值不同。

```go
    switch probeType {
	case readiness:
		w.spec = container.ReadinessProbe
		w.resultsManager = m.readinessManager
		w.initialValue = results.Failure
	case liveness:
		w.spec = container.LivenessProbe
		w.resultsManager = m.livenessManager
		w.initialValue = results.Success
	}
```

liveness的初始值为成功。这样防止在应用还没有成功启动前，就被误杀。如果在规定时间内还未成功启动，才将其设置为失败，从而触发容器重建。

而readiness的初始值为失败。这样防止应用还没有成功启动前就向应用进行流量的导入。如果在规定时间内启动成功，才将其设置为成功，从而将流量向应用导入。

liveness与readiness二者作用不能相互替代。

例如只配置了liveness，那么在容器启动，应用还没有成功就绪之前，这个时候pod是ready的（因为容器成功启动了）。那么流量就会被引入到容器的应用中，可能会导致请求失败。虽然在liveness检查失败后，重启容器，此时pod的ready的condition会变为false。但是前面会有一些流量因为错误状态导入。

当然只配置了readiness是无法触发容器重启的。

因为二者的作用不同，在实际使用中，可以根据实际的需求将二者进行配合使用。