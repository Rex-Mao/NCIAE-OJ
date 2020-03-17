FROM judge-center:stable.0.5
## Run Judger
RUN rm -rf ./*
COPY . .
RUN make clean -C judge-center && \
    make -C judge-center && \
    chmod a+x ./Docker/judgecenter/docker-entrypoint.sh
RUN $M2_HOME/bin/mvn package -DskipTests -f judge-center/pom.xml
RUN /etc/init.d/mysql start && \
    mysql oj_judgecenter < ./Docker/judgecenter/checkpoint.sql
ENTRYPOINT ["./Docker/judgecenter/docker-entrypoint.sh"]
EXPOSE 8818