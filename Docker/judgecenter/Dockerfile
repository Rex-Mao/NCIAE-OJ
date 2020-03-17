FROM ubuntu:18.04
MAINTAINER RexALun<rexalun99@gmail.com>

# User Settings
ARG MYSQL_ROOT_PASS=judger
ARG MYSQL_USER_PASS=judger

# Set environment variables.
ENV HOME          /root
ENV JAVA_HOME     /usr/lib/jvm/java-11-openjdk-amd64
ENV M2_HOME       /opt/maven
ENV LANG en_US.utf8

# Define working directory.
WORKDIR           /root

# Install MySQL
RUN apt-get update
RUN apt-get install -y mariadb-server mariadb-client
RUN sed -i "s/127\.0\.0\.1/0.0.0.0/g" /etc/mysql/mariadb.conf.d/50-server.cnf

# Install Java
RUN apt-get install -y openjdk-11-jdk

# Install Maven
RUN apt-get install -y wget
RUN wget https://archive.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
RUN tar -xf apache-maven-3.5.4-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-3.5.4 /opt/maven && \
    rm apache-maven-3.5.4-bin.tar.gz

# Setup Judger Project
RUN apt-get install -y git gcc g++ make
COPY . .
RUN cp ./Doc/Maven/setting.xml $M2_HOME/conf/settings.xml

#Set up MySQL
RUN /etc/init.d/mysql start && \
    /usr/bin/mysqladmin -u root password '${MYSQL_ROOT_PASS}' && \
    mysql -e "CREATE DATABASE oj_judgecenter" && \
    mysql oj_judgecenter < ./judge-center/oj_judgecenter.sql &&\
    mysql -e "GRANT SELECT, INSERT, UPDATE, DELETE ON oj_judgecenter.* TO 'judger'@'%' IDENTIFIED BY '${MYSQL_USER_PASS}'"
RUN mkdir -p judge-center/target/classes
RUN make clean -C judge-center && \
    make -C judge-center && \
    chmod a+x ./Docker/judgecenter/docker-entrypoint.sh
RUN $M2_HOME/bin/mvn package -DskipTests -f ./judge-center/pom.xml

# Run MySQL & Judger
ENTRYPOINT ["./Docker/judgecenter/docker-entrypoint.sh"]

EXPOSE 8818