1. 容器内 cat  /sys/class/net/eth0/iflink    查看网卡编号
2. 宿主机 ip addr |grep  {编号}  ，找到对应网卡
3. tcpdump -i {网卡} -w  /tmp/xxx.pacp  -XXX -s 2000 tcp