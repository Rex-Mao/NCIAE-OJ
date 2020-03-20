package cn.edu.nciae.judgecenter.service.impl;

import cn.edu.nciae.judgecenter.common.dto.RecordDTO;
import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.manager.Dispatcher;
import cn.edu.nciae.judgecenter.rocketmq.source.JudgeResultSouce;
import cn.edu.nciae.judgecenter.service.IJudgeSubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/7 12:37 PM
 */
@Slf4j
@Service
public class JudgeSubmissionServiceImpl implements IJudgeSubmissionService {

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private JudgeResultSouce judgeResultSouce;

    /**
     * desc : judge submission as Pure ACM Mode
     * @param submissionDTO - submission info
     */
    @Override
    public void judgeSubmission(SubmissionDTO submissionDTO) {
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.initRecordWithSubmissionDTO(submissionDTO);
        List<Map<String, Object>> runtimeResults = dispatcher.createNewTask(submissionDTO);
        Map<String, Object> logInfo = runtimeResults.remove(0);
        String runtimeSlug = (String)logInfo.get("RuntimeSlug");
        recordDTO.transferRuntimeSlugToStatus(runtimeSlug);

        int totalTime = 0;
		int maxMemory = 0;

		for ( Map<String, Object> runtimeResult : runtimeResults ) {
			int usedTime = getUsedTime(runtimeResult);
			int usedMemory = getUsedMemory(runtimeResult);

            totalTime = totalTime + usedTime;
			if ( usedMemory > maxMemory ) {
				maxMemory = usedMemory;
			}
		}

        recordDTO.setUsedTime((double)totalTime);
		recordDTO.setUsedMemory((double)maxMemory);
        judgeResultSouce.output().send(MessageBuilder.withPayload(recordDTO).build());
		log.info(String.format("Record %s is delivered to the MQ ...", recordDTO.getRecordId()));
	}

    /**
	 * desc : get one checkpoint time usage info
	 * @param runtimeResult - runtime result set
	 * @return int - runtime used(MS)
	 */
	private int getUsedTime(Map<String, Object> runtimeResult) {
		Object usedTimeObject = runtimeResult.get("usedTime");

		if (usedTimeObject == null) {
			return 0;
		}
		return ((Double)usedTimeObject).intValue();
	}

	/**
	 * desc : get one checkpoint memeory usage info
	 * @param runtimeResult - runtime result set
	 * @return int - runtime memory used(KB)
	 */
	private int getUsedMemory(Map<String, Object> runtimeResult) {
		Object usedMemoryObject = runtimeResult.get("usedMemory");

		if (usedMemoryObject == null || ((Double)usedMemoryObject) == 0) {
			return 0;
		}
		return ((Double)usedMemoryObject).intValue();
	}
}
