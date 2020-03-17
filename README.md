# Getting Started

## Server Port Configuration
* registry-center: 8848
* gateway : 8866
* user-center : 883X - 8840
* content-center : 882X - 8830
* judge-center : 881X - 8820
* rocketmq-broker : 10911
* rocketmq-nameserver : 9876
* rocketmq-console : 24300

## JNI Configuration of JudgeCenter
**Windows**:

```
cd %JAVA_HOME%\include\win32
copy jawt_md.h  ..
copy jni_md.h  ..

cd judger
mvn package -DskipTests
```

**Linux**:

```
cd $JAVA_HOME/include/linux
cp jawt_md.h jni_md.h ..

cd SOURCE_CODE_PATH/judger
mvn package -DskipTests
```

If the build is successful, the terminal will display a message as following:

```
[INFO] Executing tasks

jni:
     [echo] Generating JNI headers
     [exec] mkdir -p target/cpp
     [exec] g++ -c -std=c++11 -Wall -fPIC -I ... -o target/cpp/Judger.Core.Runner.o
[INFO] Executed tasks
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 12.432 s
[INFO] Finished at: 2015-11-26T13:22:46+08:00
[INFO] Final Memory: 81M/513M
[INFO] ------------------------------------------------------------------------
```

