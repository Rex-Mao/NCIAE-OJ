###Remember to change the broker conf file
* Add the autoCreateTopicEnable = true
* Change the brokerIP1 & brokerIP2 if you use the public net
* You can change ./Docker/rocketmq/broker/conf/broker.conf 
  to change the docker by volumes.
* Don't use host.docker.internal it was not stable
* pay attention to the hosts file when use test