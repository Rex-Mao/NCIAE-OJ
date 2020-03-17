package cn.edu.nciae.judgecenter.rocketmq;

import cn.edu.nciae.judgecenter.common.entity.Checkpoint;
import cn.edu.nciae.judgecenter.common.mapper.CheckpointMapper;
import cn.edu.nciae.judgecenter.rocketmq.sink.CheckpointSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/8 1:35 PM
 */
@Slf4j
@Component
public class AddCheckpointConsumer {

    @Autowired
    private CheckpointMapper checkpointMapper;

    @StreamListener(CheckpointSink.CHECKPOINT_INPUT)
    public void receive(List<Checkpoint> checkpointList) {
        log.info(String.format("Checkpoints of Problem %s is delivered from the MQ ...", checkpointList.get(0).getPid()));
        for (Checkpoint checkpoint : checkpointList) {
            checkpointMapper.insert(checkpoint);
        }
    }
}
