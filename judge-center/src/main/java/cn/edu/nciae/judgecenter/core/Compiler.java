/* Vectory Online Judge - A cross-platform judge online system
 * Copyright (C) 2018 Haozhe Xie <cshzxie@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *                              _ooOoo_  
 *                             o8888888o  
 *                             88" . "88  
 *                             (| -_- |)  
 *                             O\  =  /O  
 *                          ____/`---'\____  
 *                        .'  \\|     |//  `.  
 *                       /  \\|||  :  |||//  \  
 *                      /  _||||| -:- |||||-  \  
 *                      |   | \\\  -  /// |   |  
 *                      | \_|  ''\---/''  |   |  
 *                      \  .-\__  `-`  ___/-. /  
 *                    ___`. .'  /--.--\  `. . __  
 *                 ."" '<  `.___\_<|>_/___.'  >'"".  
 *                | | :  `- \`.;`\ _ /`;.`/ - ` : | |  
 *                \  \ `-.   \_ __\ /__ _/   .-` /  /  
 *           ======`-.____`-.___\_____/___.-`____.-'======  
 *                              `=---=' 
 *
 *                          HERE BE BUDDHA
 *
 */
package cn.edu.nciae.judgecenter.core;

import cn.edu.nciae.judgecenter.model.Submission;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc : Use to compile the user's code.
 * @author RexALun
 * @since 2019-12-24
 */
@Component
public class Compiler {
	/**
	 * 获取编译输出结果.
	 * @param submission - 提交记录对象
	 * @param workDirectory - 编译输出目录
	 * @param baseFileName - 编译输出文件名
	 * @return 包含编译输出结果的Map<String, Object>对象
	 */
	public Map<String, Object> getCompileResult(Submission submission,
												String workDirectory, String baseFileName) {
		String commandLine = getCompileCommandLine(submission, workDirectory, baseFileName);
		String compileLogPath = getCompileLogPath(workDirectory, baseFileName);

		return getCompileResult(commandLine, compileLogPath);
	}
	
	/**
	 * 获取编译命令.
	 * @param submission - 提交记录对象
	 * @param workDirectory - 编译输出目录
	 * @param baseFileName - 编译输出文件名
	 * @return 编译命令
	 */
	private String getCompileCommandLine(Submission submission, 
			String workDirectory, String baseFileName) {
		String filePathWithoutExtension = String.format("%s/%s", 
											new Object[] {workDirectory, baseFileName});
		String compileCommand = submission.getLanguage()
											.getCompileCommand()
											.replaceAll("\\{filename\\}", filePathWithoutExtension);
		return compileCommand;
	}
	
	/**
	 * 获取编译日志输出的文件路径.
	 * @param workDirectory - 编译输出目录
	 * @param baseFileName - 编译输出文件名
	 * @return 编译日志输出的文件路径
	 */
	private String getCompileLogPath(String workDirectory, String baseFileName) {
		return String.format("%s/%s-compile.log", 
				new Object[] {workDirectory, baseFileName});
	}
	
	/**
	 * 获取编译输出结果.
	 * @param commandLine - 编译命令
	 * @param compileLogPath - 编译日志输出路径
	 * @return 包含编译输出结果的Map<String, Object>对象
	 */
	private Map<String, Object> getCompileResult(String commandLine, String compileLogPath) {
		String inputFilePath = null;
		int timeLimit = 5000;
		int memoryLimit = 0;
		
		LOGGER.info("Start compiling with command: " + commandLine);
		Map<String, Object> runningResult = compilerRunner.getRuntimeResult(
				commandLine, inputFilePath, compileLogPath, timeLimit, memoryLimit);
		Map<String, Object> result = new HashMap<String, Object>(3, 1);
		
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
	 * 获取编译日志内容.
	 * @param compileLogPath - 编译日志路径
	 * @return 编译日志内容
	 */
	private String getCompileOutput(String compileLogPath) {
		FileInputStream inputStream = null;
		String compileLog = "";
		try {
			inputStream = new FileInputStream(compileLogPath);
			compileLog = IOUtils.toString(inputStream);
			inputStream.close();
		} catch (Exception ex) {
			// Do nothing
		}
		return compileLog;
	}
	
	/**
	 * 自动注入的Runner对象.
	 * 用于执行编译命令.
	 */
	@Autowired
	private Runner compilerRunner;
	
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(Compiler.class);
}
