package cn.edu.nciae.judgecenter.manager;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.common.entity.Checkpoint;
import cn.edu.nciae.judgecenter.common.entity.Language;
import cn.edu.nciae.judgecenter.common.mapper.CheckpointMapper;
import cn.edu.nciae.judgecenter.exception.CreateDirectoryException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/4 1:23 PM
 */
@Component
public class Preparer {

	@Value("${judger.checkpointDir}")
	private String checkpointDirectory;

	@Autowired
	private CheckpointMapper checkpointMapper;

	/**
	 *
	 * @param submissionDTO - submission info
	 * @param workDirectory - work directory
	 * @param baseFileName - file name without suffix
	 * @throws Exception - CreateDirectoryException
	 */
    public void prepareTargetCode(SubmissionDTO submissionDTO, String workDirectory,
                                  String baseFileName) throws Exception {
        File workDirFile = new File(workDirectory);
		if ( !workDirFile.exists() && !workDirFile.mkdirs() ) {
			throw new CreateDirectoryException("Failed to create directory: " + workDirectory);
		}
		setWorkDirectoryPermission(workDirFile);

		Language language = submissionDTO.getLanguage();
		String code = replaceClassToMain(language, submissionDTO.getCode(), baseFileName);
		String codeFilePath = String.format("%s/%s.%s", workDirectory, baseFileName, language.getLanguageSuffix());
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
			IOUtils.write(code, outputStream, StandardCharsets.UTF_8);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			throw new IOException("Failed to fill the file...");
		}
    }

	/**
	 * desc : get the answer of the problem to the file
	 * @param problemId - the target problem
	 */
	public void getCheckpoints(Long problemId) throws Exception{
		String checkpointsFilePath = String.format("%s/%s", checkpointDirectory, problemId);
		File checkpointsDirFile = new File(checkpointsFilePath);
		if ( !checkpointsDirFile.exists() && !checkpointsDirFile.mkdirs() ) {
			throw new CreateDirectoryException("Failed to create the checkpoints directory: " + checkpointsFilePath);
		}

		List<Checkpoint> checkpoints = checkpointMapper.selectList(Wrappers.<Checkpoint>lambdaQuery().eq(Checkpoint::getPid, problemId));
		for ( Checkpoint checkpoint : checkpoints ) {
			long checkpointId = checkpoint.getCpid();
			{
				// Standard Input File
				String filePath = String.format("%s/input#%s.txt", checkpointsFilePath, checkpointId);
				FileOutputStream outputStream = new FileOutputStream(new File(filePath));
				String input = checkpoint.getInput();
				IOUtils.write(input, outputStream, StandardCharsets.UTF_8);
				outputStream.flush();
				outputStream.close();
			}
			{
				// Standard Output File
				String filePath = String.format("%s/output#%s.txt", checkpointsFilePath, checkpointId);
				FileOutputStream outputStream = new FileOutputStream(new File(filePath));
				String output = checkpoint.getOutput();
				IOUtils.write(output, outputStream, StandardCharsets.UTF_8);
				outputStream.flush();
				outputStream.close();
			}
		}
	}

	/**
	 * desc : chmod the directory of work
	 * @param workDirectory - work directory
	 * @throws IOException - because of the permission of the directory
	 */
	private void setWorkDirectoryPermission(File workDirectory) throws IOException {
		if ( !System.getProperty("os.name").contains("Windows") ) {
			Set<PosixFilePermission> permissions = new HashSet<>();

			permissions.add(PosixFilePermission.OWNER_READ);
			permissions.add(PosixFilePermission.OWNER_WRITE);
			permissions.add(PosixFilePermission.OWNER_EXECUTE);

			permissions.add(PosixFilePermission.GROUP_READ);
			permissions.add(PosixFilePermission.GROUP_WRITE);
			permissions.add(PosixFilePermission.GROUP_EXECUTE);

			permissions.add(PosixFilePermission.OTHERS_READ);
			permissions.add(PosixFilePermission.OTHERS_WRITE);
			permissions.add(PosixFilePermission.OTHERS_EXECUTE);
			Files.setPosixFilePermissions(workDirectory.toPath(), permissions);
		}
	}

	/**
	 *
	 * @param language - language info
	 * @param code - test code
	 * @param newClassName - reedit the class name of Java code
	 * @return String - the reedited code
	 */
	private String replaceClassToMain(Language language, String code, String newClassName) {
		if ( !"Java".equalsIgnoreCase(language.getLanguageName())) {
			return code;
		}
		return code.replaceAll("class[ \n]+Main", "class " + newClassName);
	}
}
