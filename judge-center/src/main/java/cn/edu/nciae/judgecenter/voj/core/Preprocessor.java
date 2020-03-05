//package cn.edu.nciae.judgecenter.VOJ.core;
//
//
//import cn.edu.nciae.judgecenter.VOJ.exception.CreateDirectoryException;
//import cn.edu.nciae.judgecenter.VOJ.mapper.CheckpointMapper;
//import cn.edu.nciae.judgecenter.VOJ.model.Checkpoint;
//import cn.edu.nciae.judgecenter.VOJ.model.Language;
//import cn.edu.nciae.judgecenter.VOJ.model.Submission;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static java.util.regex.Pattern.*;
//
///**
// * 预处理器, 用于完成评测前准备工作.
// *
// * @author RexALun
// * @since 2019-12-26
// */
//@Component
//public class Preprocessor {
//	/**
//	 * 创建测试代码至本地磁盘.
//	 *
//	 * @param submission - 评测记录对象
//	 * @param workDirectory - 用于产生编译输出的目录
//	 * @param baseFileName - 随机文件名(不包含后缀)
//	 * @throws Exception
//	 */
//	public void createTestCode(Submission submission,
//							   String workDirectory, String baseFileName) throws Exception {
//		File workDirFile = new File(workDirectory);
//		if ( !workDirFile.exists() && !workDirFile.mkdirs() ) {
//			throw new CreateDirectoryException("Failed to create directory: " + workDirectory);
//		}
//		setWorkDirectoryPermission(workDirFile);
//
//		Language language = submission.getLanguage();
//		String code = replaceClassName(language, submission.getCode(), baseFileName);
//		String codeFilePath = String.format("%s/%s.%s",
//				new Object[] {workDirectory, baseFileName, getCodeFileSuffix(language)});
//
//		FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
//		IOUtils.write(code, outputStream);
//		IOUtils.closeQuietly(outputStream);
//	}
//
//	/**
//	 * 获取代码文件的后缀名.
//	 * @param language - 编程语言对象
//	 * @return 代码文件的后缀名
//	 */
//	private String getCodeFileSuffix(Language language) {
//		String compileCommand = language.getCompileCommand();
//
//		Pattern pattern = compile("\\{filename\\}\\.((?!exe| ).)+");
//		Matcher matcher = pattern.matcher(compileCommand);
//
//		if ( matcher.find() ) {
//			String sourceFileName = matcher.group();
//			return sourceFileName.replaceAll("\\{filename\\}\\.", "");
//		}
//		return "";
//	}
//
//	/**
//	 * 替换部分语言中的类名(如Java), 以保证正常通过编译.
//	 * @param language - 编程语言对象
//	 * @param code - 待替换的代码
//	 * @param newClassName - 新的类名
//	 */
//	private String replaceClassName(Language language, String code, String newClassName) {
//		if ( !language.getLanguageName().equalsIgnoreCase("Java") ) {
//			return code;
//		}
//		return code.replaceAll("class[ \n]+Main", "class " + newClassName);
//	}
//
//	/**
//	 * 设置代码文件所在目录的读写权限.
//	 * 在Linux下, 代码以UID=1536的用户运行, 因此需要为Others用户组分配写权限.
//	 * @param workDirectory 用于产生编译输出的目录
//	 */
//	private void setWorkDirectoryPermission(File workDirectory) throws IOException {
//		if ( !System.getProperty("os.name").contains("Windows") ) {
//			Set<PosixFilePermission> permissions = new HashSet<>();
//
//			permissions.add(PosixFilePermission.OWNER_READ);
//			permissions.add(PosixFilePermission.OWNER_WRITE);
//			permissions.add(PosixFilePermission.OWNER_EXECUTE);
//
//			permissions.add(PosixFilePermission.GROUP_READ);
//			permissions.add(PosixFilePermission.GROUP_WRITE);
//			permissions.add(PosixFilePermission.GROUP_EXECUTE);
//
//			permissions.add(PosixFilePermission.OTHERS_READ);
//			permissions.add(PosixFilePermission.OTHERS_WRITE);
//			permissions.add(PosixFilePermission.OTHERS_EXECUTE);
//			Files.setPosixFilePermissions(workDirectory.toPath(), permissions);
//		}
//	}
//
//	/**
//	 * 从数据库抓取评测数据.
//	 * @param problemId - 试题的唯一标识符
//	 * @throws Exception
//	 */
//	public void fetchTestPoints(long problemId) throws Exception {
//		String checkpointsFilePath = String.format("%s/%s",
//				new Object[] {checkpointDirectory, problemId});
//		File checkpointsDirFile = new File(checkpointsFilePath);
//		if ( !checkpointsDirFile.exists() && !checkpointsDirFile.mkdirs() ) {
//			throw new CreateDirectoryException("Failed to create the checkpoints directory: " + checkpointsFilePath);
//		}
//
//		List<Checkpoint> checkpoints =
//				checkpointMapper.getCheckpointsUsingProblemId(problemId);
//		for ( Checkpoint checkpoint : checkpoints ) {
//			long checkpointId = checkpoint.getCheckpointId();
//			{ // Standard Input File
//				String filePath = String.format("%s/input#%s.txt",
//						new Object[] { checkpointsFilePath, checkpointId });
//				FileOutputStream outputStream = new FileOutputStream(new File(filePath));
//				String input = checkpoint.getInput();
//				IOUtils.write(input, outputStream);
//				IOUtils.closeQuietly(outputStream);
//			}
//			{ // Standard Output File
//				String filePath = String.format("%s/output#%s.txt",
//						new Object[] { checkpointsFilePath, checkpointId });
//				FileOutputStream outputStream = new FileOutputStream(new File(filePath));
//				String output = checkpoint.getOutput();
//				IOUtils.write(output, outputStream);
//				IOUtils.closeQuietly(outputStream);
//			}
//		}
//	}
//
//	/**
//	 * 自动注入的CheckpointMapper对象.
//	 * 用于获取试题的测试点.
//	 */
//	@Autowired
//	private CheckpointMapper checkpointMapper;
//
//	/**
//	 * 测试点的存储目录.
//	 * 用于存储测试点的输入输出数据.
//	 */
//	@Value("${judger.checkpointDir}")
//	private String checkpointDirectory;
//}
