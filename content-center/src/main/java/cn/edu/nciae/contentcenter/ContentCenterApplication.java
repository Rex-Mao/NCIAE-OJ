package cn.edu.nciae.contentcenter;

import cn.edu.nciae.contentcenter.rocketmq.sink.JudgeResultSink;
import cn.edu.nciae.contentcenter.rocketmq.source.CheckpointSource;
import cn.edu.nciae.contentcenter.rocketmq.source.JudgeSubmissionSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("cn.edu.nciae.contentcenter.common.mapper")
@EnableBinding({JudgeResultSink.class, JudgeSubmissionSource.class, CheckpointSource.class})
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() { return new RestTemplate(); }
}
