package cn.edu.nciae.judgecenter.manager;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.common.entity.Checkpoint;
import cn.edu.nciae.judgecenter.common.mapper.CheckpointMapper;
import cn.edu.nciae.judgecenter.utils.DigestUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/4 11:27 AM
 */
@Slf4j
@Component
public class Dispatcher {

    /**
	 * the dir use to store the compile result and output
	 */
	@Value("${judge.center.workDir}")
	private String workBaseDirectory;

	/**
	 * the dir use to store the checkpoints
	 */
	@Value("${judge.center.checkpointDir}")
	private String checkpointDirectory;

	@Autowired
	private Preparer preparer;

	@Autowired
	private Compiler compiler;

	@Autowired
	private Runner runner;

	@Autowired
	private Comparator comparator;

	@Autowired
	private CheckpointMapper checkpointMapper;

	/**
	 * desc : use to create a new task
	 * @param submissionDTO - submission info
	 */
    public List<Map<String, Object>> createNewTask(SubmissionDTO submissionDTO) {
        synchronized (this) {
			List<Map<String, Object>> runtimeResults = new ArrayList<>();
			String baseDirectory = String.format("%s/judge-%s", workBaseDirectory, submissionDTO.getSubmissionId());
			String baseFileName = DigestUtils.getRandomString(12, DigestUtils.Mode.ALPHA);
			preprocess(submissionDTO, baseDirectory, baseFileName);
			if ( compile(submissionDTO, baseDirectory, baseFileName) ) {
				runtimeResults = runProgram(submissionDTO, baseDirectory, baseFileName);
			} else {
				Map<String, Object> logInfo = new HashMap<>();
				logInfo.put("RuntimeSlug", "CE");
				runtimeResults.add(0, logInfo);
			}
//			cleanUp(baseDirectory);
			return runtimeResults;
        }
    }

	/**
	 * desc : prepare the test code and the checkpoint
	 * @param submissionDTO - submission info
	 * @param workDirectory - work directory
	 * @param baseFileName - file name without suffix
	 */
    private void preprocess(SubmissionDTO submissionDTO,
							String workDirectory, String baseFileName) {
		try {
			preparer.prepareTargetCode(submissionDTO, workDirectory, baseFileName);
			preparer.getCheckpoints(submissionDTO.getProblemId());
		} catch (Exception ex) {
			log.error(ex.getMessage());
//			String submissionId = submissionDTO.getSubmissionID();
//			applicationDispatcher.onErrorOccurred(submissionId);
		}
	}

	private boolean compile(SubmissionDTO submissionDTO,
							String workDirectory, String baseFileName) {
		Map<String, Object> result = compiler.getCompileResult(submissionDTO, workDirectory, baseFileName);
//		String submissionId = submissionDTO.getSubmissionID();
//		applicationDispatcher.onCompileFinished(submissionId, result);
		return (Boolean)result.get("isSuccessful");
	}

	/**
	 * desc : run the program as ACM mode
	 * @param submissionDTO - submission info
	 * @param workDirectory - work directory
	 * @param baseFileName - file name without suffix
	 */
	private List<Map<String, Object>> runProgram(SubmissionDTO submissionDTO,
												 String workDirectory, String baseFileName) {
		List<Map<String, Object>> runtimeResults = new ArrayList<Map<String, Object>>();
		Map<String, Object> logInfo = new HashMap<String, Object>();
		logInfo.put("RuntimeSlug", "AC");
		runtimeResults.add(logInfo);
//		String submissionId = submissionDTO.getSubmissionId();
		long problemId = submissionDTO.getProblemId();

		List<Checkpoint> checkpoints = checkpointMapper.selectList(Wrappers.<Checkpoint>lambdaQuery().eq(Checkpoint::getPid, problemId));
		for ( Checkpoint checkpoint : checkpoints ) {
			int checkpointId = checkpoint.getCpid();
//			int checkpointScore = checkpoint.getScore();
			String inputFilePath = String.format("%s/%s/input#%s.txt",
					checkpointDirectory, problemId, checkpointId);
			String stdOutputFilePath = String.format("%s/%s/output#%s.txt",
					checkpointDirectory, problemId, checkpointId);
			String outputFilePath = getOutputFilePath(workDirectory, checkpointId);

			Map<String, Object> runtimeResult = getRuntimeResult(
					runner.getRuntimeResult(submissionDTO, workDirectory, baseFileName, inputFilePath, outputFilePath),
					stdOutputFilePath, outputFilePath);
			runtimeResults.add(runtimeResult);
			if (!"AC".equals((String)runtimeResult.get("runtimeResult"))) {
				logInfo.replace("RuntimeSlug", runtimeResult.get("runtimeResult"));
				break;
			}
//			runtimeResult.put("score", checkpointScore);
//			applicationDispatcher.onOneTestPointFinished(submissionId, checkpointId, runtimeResult);
		}
		return runtimeResults;
//		applicationDispatcher.onAllTestPointsFinished(submissionId, runtimeResults);
	}

	/**
	 * desc : clean up the directory
	 * @param baseDirectory - the directory for output the temp
	 */
	private void cleanUp(String baseDirectory) {
		File baseDirFile = new File(baseDirectory);
		if ( baseDirFile.exists() ) {
			try {
				FileUtils.deleteDirectory(baseDirFile);
			} catch (IOException e) {
				log.warn(e.getMessage());
			}
		}
	}

	/**
	 * 获取程序运行结果(及答案比对结果).
	 * @param result - 包含程序运行结果的Map对象
	 * @param standardOutputFilePath - 标准输出文件路径
	 * @param outputFilePath - 用户输出文件路径
	 * @return 包含程序运行结果的Map对象
	 */
	private Map<String, Object> getRuntimeResult(Map<String, Object> result,
		String standardOutputFilePath, String outputFilePath) {
		String runtimeResultSlug = (String)result.get("runtimeResult");
		int usedTime = (Integer)result.get("usedTime");
		int usedMemory = (Integer)result.get("usedMemory");

		if ( "AC".equals(runtimeResultSlug) && !isOutputTheSame(standardOutputFilePath, outputFilePath) ) {
			runtimeResultSlug = "WA";
			result.put("runtimeResult", runtimeResultSlug);
		}
		log.info(String.format("RuntimeResult: [%s, Time: %d ms, Memory: %d KB]",
				runtimeResultSlug, usedTime, usedMemory));
		return result;
	}

	/**
	 * desc : get the checkpoint path
	 * @param workDirectory - work directory
	 * @param checkpointId - checkpoint Id
	 * @return String of Path
	 */
	private String getOutputFilePath(String workDirectory, int checkpointId) {
		return String.format("%s/output#%s.txt", workDirectory, checkpointId);
	}

	/**
	 * desc : judge the user's output and standard ouput
	 * @param standardOutputFilePath - standard ouput file path
	 * @param outputFilePath - user's output file path
	 * @return boolean value of the judge
	 */
	private boolean isOutputTheSame(String standardOutputFilePath, String outputFilePath) {
		try {
			return comparator.isOutputEqualStandard(standardOutputFilePath, outputFilePath);
		} catch (IOException ex) {
			log.warn(ex.getMessage());
		}
		return false;
	}
}
