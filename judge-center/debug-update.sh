#!/bin/sh
make clean
make
$M2_HOME/bin/mvn package -DskipTests -f judge-center/pom.xml