package cn.edu.nciae.judgecenter;

import cn.edu.nciae.judgecenter.rocketmq.sink.CheckpointSink;
import cn.edu.nciae.judgecenter.rocketmq.sink.JudgeSubmissionSink;
import cn.edu.nciae.judgecenter.rocketmq.source.JudgeResultSouce;
import cn.edu.nciae.judgecenter.utils.SystemEnvInfo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@SpringBootApplication
@MapperScan("cn.edu.nciae.judgecenter.common.mapper")
@EnableBinding({JudgeSubmissionSink.class, CheckpointSink.class, JudgeResultSouce.class})
public class JudgeCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JudgeCenterApplication.class, args);
		SystemEnvInfo.getSystemEnvironment();
	}

}
