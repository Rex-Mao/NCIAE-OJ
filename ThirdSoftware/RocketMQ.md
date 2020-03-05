#RocketMQ Handbook
* cd rocketmq-all-4.5.1-bin-release
* nohup sh bin/mqnamesrv &
* tail -f ~/logs/rocketmqlogs/namesrv.log

如果成功启动，能看到类似如下的日志：
2019-07-18 17:03:56 INFO main - The Name Server boot success. ...

* nohup sh bin/mqbroker -n localhost:9876 &
* tail -f ~/logs/rocketmqlogs/broker.log

如果启动成功，能看到类似如下的日志：
2019-07-18 17:08:41 INFO main - The broker[itmuchcomdeMacBook-Pro.local, 192.168.43.197:10911] boot success. serializeType=JSON and name server is localhost:9876
