#!/bin/sh

echo " - Starting MySQL && Judge Center - " && \
     /etc/init.d/mysql start && \
#     mysql oj_judgecenter < ./Docker/judgecenter/checkpoint.sql && \
     java -jar judge-center/target/judge-center-0.0.1-SNAPSHOT.jar
