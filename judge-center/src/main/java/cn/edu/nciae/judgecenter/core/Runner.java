package cn.edu.nciae.judgecenter.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/5 9:12 PM
 */
@Slf4j
@Component
public class Runner {

    /**
     * desc : get runtime result
     * @param commandLine - command line
     * @param inputFilePath -
     * @param compileLogPath -
     * @param timeLimit -
     * @param memoryLimit -
     * @return Map<String, Object>
     */
    public Map<String, Object> getRuntimeResult(String commandLine, String inputFilePath, String compileLogPath, int timeLimit, int memoryLimit) {
//        String commandLine = getCommandLine(SubmissionDTO, workDirectory, baseFileName);
//		int timeLimit = getTimeLimit(submission);
//		int memoryLimit = getMemoryLimit(submission);

		Map<String, Object> result = new HashMap<>(4, 1);
//		String runtimeResultSlug = "SE";
//		int usedTime = 0;
//		int usedMemory = 0;
//
//		try {
//			log.info(String.format("[Submission #%d] Start running with command %s (TimeLimit=%d, MemoryLimit=%s)",
//								new Object[] { submission.getSubmissionId(), commandLine, timeLimit, memoryLimit }));
//			Map<String, Object> runtimeResult = getRuntimeResult(commandLine,
//					systemUsername, systemPassword, inputFilePath, outputFilePath,
//					timeLimit, memoryLimit);
//
//			int exitCode = (Integer) runtimeResult.get("exitCode");
//			usedTime = (Integer) runtimeResult.get("usedTime");
//			usedMemory = (Integer) runtimeResult.get("usedMemory");
//			runtimeResultSlug = getRuntimeResultSlug(exitCode, timeLimit, usedTime, memoryLimit, usedMemory);
//		} catch ( Exception e ) {
//			log.warn(e.getMessage());
//		}
//
//		result.put("runtimeResult", runtimeResultSlug);
//		result.put("usedTime", usedTime);
//		result.put("usedMemory", usedMemory);
		return result;
    }
}
