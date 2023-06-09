*yum install ntpdate*

ntpdate ntp1.aliyun.com



docker run --name jacob_sentinel -d \
-p 18485:8858 \
 bladex/sentinel-dashboard





docker run -d --name jacob_rabbitmq -p 5672:5672 -p 15672:15672 -v `pwd`/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin   rabbitmq:management





docker run -d -e TZ="Asia/Shanghai" -p 2181:2181 -v $PWD/data:/data --name jacob_zookeeper --restart always zookeeper:3.4