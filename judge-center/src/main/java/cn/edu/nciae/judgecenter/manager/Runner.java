package cn.edu.nciae.judgecenter.manager;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.common.entity.Language;
import cn.edu.nciae.judgecenter.utils.NativeLibraryLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
	 * desc : A user with lower permission for security
	 */
	@Value("${judge.system.username}")
	private String systemUsername;

    /**
     * desc : A user with lower permission for security
     */
	@Value("${judge.system.password}")
	private String systemPassword;

    /**
     * desc : load the native library
     */
	static {
		try {
			NativeLibraryLoader.loadLibrary("JudgerCore");
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getMessage());
		}
	}

    /**
     * desc : get runtime result
     * @param submissionDTO - submission info
     * @param workDirectory - work directory
     * @param baseFileName - file name without suffix
     * @param inputFilePath - input file path
     * @param outputFilePath - output file path
     * @return Map<String, Object> Result
     */
    public Map<String, Object> getRuntimeResult(SubmissionDTO submissionDTO, String workDirectory,
												String baseFileName, String inputFilePath, String outputFilePath) {
        String commandLine = getCommandLineForRun(submissionDTO, workDirectory, baseFileName);
		double timeLimit = getTimeLimit(submissionDTO);
		double memoryLimit = getMemoryLimit(submissionDTO);

		Map<String, Object> result = new HashMap<>(4, 1);
		String runtimeResultSlug = "SE";
		double usedTime = 0;
		double usedMemory = 0;

		try {
			log.info(String.format("[Submission #%s] Start running with command %s (TimeLimit=%d, MemoryLimit=%s)",
                    submissionDTO.getSubmissionId(), commandLine, (int)timeLimit, memoryLimit));
			Map<String, Object> runtimeResult = getRuntimeResult(commandLine, systemUsername, systemPassword,
                    inputFilePath, outputFilePath, (int)timeLimit, (int)memoryLimit);

			int exitCode = (Integer) runtimeResult.get("exitCode");
			usedTime = (Integer) runtimeResult.get("usedTime");
			usedMemory = (Integer) runtimeResult.get("usedMemory");
			runtimeResultSlug = getRuntimeResultSlug(exitCode, (int)timeLimit, (int)usedTime, (int)memoryLimit, (int)usedMemory);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		result.put("runtimeResult", runtimeResultSlug);
		result.put("usedTime", usedTime);
		result.put("usedMemory", usedMemory);
		return result;
    }

    /**
     * desc : encapsulate the native method
     * @param commandLine - command line
     * @param inputFilePath - it's ok to be null when compile
     * @param outputFilePath - it's ok to be null when compile
     * @param timeLimit - 0 means no limit
     * @param memoryLimit - 0 means no limit (MB->KB)
     * @return Map<String, Object> Result
     */
    public Map<String, Object> getRuntimeResult(String commandLine, String inputFilePath, String outputFilePath,
												int timeLimit, int memoryLimit) {
        Map<String, Object> result = null;
        try {
            result = getRuntimeResult(commandLine, systemUsername, systemPassword,
                    inputFilePath, outputFilePath, timeLimit, memoryLimit);
        } catch ( Exception ex ) {
            log.warn(ex.getMessage());
        }
        return result;
    }

    /**
	 * 获取程序运行结果.
	 * @param commandLine - 待执行程序的命令行
	 * @param systemUsername - 登录操作系统的用户名
	 * @param systemPassword - 登录操作系统的密码
	 * @param inputFilePath - 输入文件路径(可为NULL)
	 * @param outputFilePath - 输出文件路径(可为NULL)
	 * @param timeLimit - 时间限制(单位ms, 0表示不限制)
	 * @param memoryLimit - 内存限制(单位KB, 0表示不限制)
	 * @return 一个包含程序运行结果的Map<String, Object>对象
	 */
	public native Map<String, Object> getRuntimeResult(String commandLine,
			String systemUsername, String systemPassword, String inputFilePath,
			String outputFilePath, int timeLimit, int memoryLimit);


    /**
     * desc : return the command line for run program
     * @param submissionDTO - submission info
     * @param workDirectory - work directory
     * @param baseFileName - file name without suffix
     * @return String of the command line
     */
    private String getCommandLineForRun(SubmissionDTO submissionDTO, String workDirectory, String baseFileName) {
        Language language = submissionDTO.getLanguage();
        String commandLineTemplate = language.getLanguageRunCommand();
        String absolutePathWithoutSuffix = String.format("%s/%s", workDirectory, baseFileName);
        StringBuilder commandLineForRun = new StringBuilder(commandLineTemplate
                                                            .replaceAll("\\{filename}", absolutePathWithoutSuffix));
        if ( "Java".equalsIgnoreCase(language.getLanguageName()) ) {
			int lastIndexOfSpace = commandLineForRun.lastIndexOf("/");
			commandLineForRun.setCharAt(lastIndexOfSpace, ' ');
		}
		return commandLineForRun.toString();
    }

    /**
     * desc : the time limit of problem with the specific kind of language
     * @param submissionDTO - submission info
     * @return double of MS
     */
	private double getTimeLimit(SubmissionDTO submissionDTO) {
		Language language = submissionDTO.getLanguage();
		double timeLimit = submissionDTO.getTimeLimit();

		if ( "Java".equalsIgnoreCase(language.getLanguageName()) ) {
			timeLimit *= 2;
		}
		return timeLimit;
	}

    /**
     * desc : get memory limit
     * @param submissionDTO - submission info
     * @return double of MB
     */
	private double getMemoryLimit(SubmissionDTO submissionDTO) {
		return submissionDTO.getMemoryLimit();
	}

    /**
     * desc :
     * @param exitCode -
     * @param timeLimit -
     * @param timeUsed -
     * @param memoryLimit -
     * @param memoryUsed -
     * @return String of result
     */
	private String getRuntimeResultSlug(int exitCode, int timeLimit, int timeUsed, int memoryLimit, int memoryUsed) {
        if ( timeUsed >= timeLimit ) {
            return "TLE";
        }
        // memory use KB unit
        if ( memoryUsed >= memoryLimit ) {
            return "MLE";
        }
	    if ( exitCode == 0 ) {
			// Output will be compared in next stage
			return "AC";
		}
		return "RE";
	}
}
