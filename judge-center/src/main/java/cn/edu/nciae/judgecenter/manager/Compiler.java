package cn.edu.nciae.judgecenter.manager;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/5 8:52 PM
 */
@Slf4j
@Component
public class Compiler {

    @Autowired
    private Runner runner;

    /**
     * desc : get the result of the compile
     * @param submissionDTO - submission info
     * @param workDirectory - work directory
     * @param baseFileName - file name without suffix
     * @return Map<String, Object> compile result
     */
    public Map<String, Object> getCompileResult(SubmissionDTO submissionDTO, String workDirectory, String baseFileName) {
        String commandLine = getCompileCommandLine(submissionDTO, workDirectory, baseFileName);
		String compileLogPath = String.format("%s/%s-compile.log", workDirectory, baseFileName);
		return getCompileResult(commandLine, compileLogPath);
    }

    /**
     * desc : get the command line string
     * @param submissionDTO - submission info
     * @param workDirectory - work directory
     * @param baseFileName - file name without suffix
     * @return String of command line
     */
    private String getCompileCommandLine(SubmissionDTO submissionDTO,
			String workDirectory, String baseFileName) {
		String filePathWithoutExtension = String.format("%s/%s", workDirectory, baseFileName);
        return submissionDTO.getLanguage().getLanguageCompileCommand()
                .replaceAll("\\{filename}", filePathWithoutExtension);
	}

    /**
     * desc : get the compile result
     * @param commandLine - command
     * @param compileLogPath - path for log compile
     * @return Map<String, Object>
     */
	private Map<String, Object> getCompileResult(String commandLine, String compileLogPath) {
		String inputFilePath = null;
		int timeLimit = 5000;
		int memoryLimit = 0;

		log.info("Start compiling with command: " + commandLine);
		Map<String, Object> runningResult = runner.getRuntimeResult(
				commandLine, inputFilePath, compileLogPath, timeLimit, memoryLimit);
		Map<String, Object> result = new HashMap<>(3, 1);

		boolean isSuccessful = false;
		if ( runningResult != null ) {
			int exitCode = (Integer)runningResult.get("exitCode");
			isSuccessful = exitCode == 0;
		}
		result.put("isSuccessful", isSuccessful);
		result.put("log", getCompileOutput(compileLogPath));
		return result;
	}

	/**
	 * get the compile log from file
	 * @param compileLogPath -
	 * @return String of Log info
	 */
	private String getCompileOutput(String compileLogPath) {
		FileInputStream inputStream = null;
		String compileLog = "";
		try {
			inputStream = new FileInputStream(compileLogPath);
			compileLog = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			inputStream.close();
		} catch (Exception ex) {
			// Do nothing
		}
		return compileLog;
	}
}
