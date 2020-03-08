package cn.edu.nciae.judgecenter.rocketmq;

import cn.edu.nciae.judgecenter.common.entity.Checkpoint;
import cn.edu.nciae.judgecenter.common.mapper.CheckpointMapper;
import cn.edu.nciae.judgecenter.rocketmq.sink.CheckpointSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/8 1:35 PM
 */
public class AddCheckpointConsumer {

    @Autowired
    private CheckpointMapper checkpointMapper;

    @StreamListener(CheckpointSink.CHECKPOINT_INPUT)
    public void receive(List<Checkpoint> checkpointList) {
        for (Checkpoint checkpoint : checkpointList) {
            checkpointMapper.insert(checkpoint);
        }
    }
}
