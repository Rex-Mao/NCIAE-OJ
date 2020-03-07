package cn.edu.nciae.judgecenter.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/15 12:31 PM
 */
@RocketMQTransactionListener(txProducerGroup = "tx-judge-result-group")
public class JudgeResultTransactionListener implements RocketMQLocalTransactionListener {
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
