FROM judge-center:base.1.0
## Run Judger
RUN rm -rf ./*
COPY . .
RUN make clean -C judge-center && make -C judge-center
RUN $M2_HOME/bin/mvn package -DskipTests -f judge-center/pom.xml
CMD ["java", "-jar", "judge-center/target/judge-center-0.0.1-SNAPSHOT.jar"]
EXPOSE 8818