package cn.edu.nciae.judgecenter.core;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.utils.DigestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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

	/**
	 * desc : use to create a new task
	 * @param submissionDTO - submission info
	 */
    public void createNewTask(SubmissionDTO submissionDTO) {
        synchronized (this) {
			String baseDirectory = String.format("%s/judge-%s", workBaseDirectory, submissionDTO.getSubmissionID());
			String baseFileName = DigestUtils.getRandomString(12, DigestUtils.Mode.ALPHA);
			preprocess(submissionDTO, baseDirectory, baseFileName);
			if ( compile(submissionDTO, baseDirectory, baseFileName) ) {
//				runProgram(submission, baseDirectory, baseFileName);
			}
			cleanUp(baseDirectory);
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
			String submissionId = submissionDTO.getSubmissionID();
//			applicationDispatcher.onErrorOccurred(submissionId);
		}
	}

	private boolean compile(SubmissionDTO submissionDTO,
							String workDirectory, String baseFileName) {
		String submissionId = submissionDTO.getSubmissionID();
		Map<String, Object> result =
				compiler.getCompileResult(submissionDTO, workDirectory, baseFileName);

//		applicationDispatcher.onCompileFinished(submissionId, result);
		return (Boolean)result.get("isSuccessful");
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
}
