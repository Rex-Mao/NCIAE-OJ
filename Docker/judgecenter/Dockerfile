FROM ubuntu:18.04
MAINTAINER RexALun<rexalun99@gmail.com>

# User Settings
ARG MYSQL_ROOT_PASS=judger
ARG MYSQL_USER_PASS=judger

# Set environment variables.
ENV HOME          /root
ENV JAVA_HOME     /usr/lib/jvm/java-11-openjdk-amd64
ENV M2_HOME       /opt/maven

# Define working directory.
WORKDIR           /root

# Install MySQL and Set up MySQL
RUN apt-get update
RUN apt-get install -y mariadb-server mariadb-client
RUN sed -i "s/127\.0\.0\.1/0.0.0.0/g" /etc/mysql/mariadb.conf.d/50-server.cnf
RUN /etc/init.d/mysql start && \
    /usr/bin/mysqladmin -u root password '${MYSQL_ROOT_PASS}' && \
    mysql -e "CREATE DATABASE oj_judgecenter" && \
    mysql -e "GRANT SELECT, INSERT, UPDATE, DELETE ON judgecenter.* TO 'judger'@'%' IDENTIFIED BY '${MYSQL_USER_PASS}'"

# Install Java
RUN apt-get install -y openjdk-11-jdk

# Install Maven
RUN apt-get install -y wget
RUN wget https://archive.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
RUN tar -xf apache-maven-3.5.4-bin.tar.gz -C /opt
RUN mv /opt/apache-maven-3.5.4 /opt/maven
RUN rm apache-maven-3.5.4-bin.tar.gz

# Setup Judger Project
RUN apt-get install -y git gcc g++ make
#COPY ../judgecenter ./judgecenter
#/Users/rexmao/Documents/RexStudio/NCIAE-OJ/judge-center
COPY ../../judge-center ./judgecenter
RUN /usr/bin/mysqladmin -u root password '${MYSQL_ROOT_PASS}' && \
    mysql judgecenter < judgecenter/oj_judgecenter.sql
RUN mkdir -p judgecenter/target/classes
RUN $M2_HOME/bin/mvn package -DskipTests -f ./judgecenter/pom.xml
RUN make clean -C judgecenter && make

# Run Judger
CMD ["java", "-jar", "judgecenter/target/judge-center-0.0.1-SNAPSHOT.jar"]

EXPOSE 8818