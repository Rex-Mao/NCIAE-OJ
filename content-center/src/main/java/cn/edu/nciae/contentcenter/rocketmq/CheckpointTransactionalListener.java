package cn.edu.nciae.contentcenter.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/8 1:26 PM
 */
@RocketMQTransactionListener(txProducerGroup = "tx-add-checkpoint-group")
public class CheckpointTransactionalListener implements RocketMQLocalTransactionListener{

    /**
     * desc : use to execute the transaction
     * @param message -
     * @param o -
     * @return -
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * desc : if rocketmq can not get the message committing status, use this to check local
     * transaction status to tell the rocketmq how to do with the message
     * @param message -
     * @return -
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return RocketMQLocalTransactionState.COMMIT;
    }
}

