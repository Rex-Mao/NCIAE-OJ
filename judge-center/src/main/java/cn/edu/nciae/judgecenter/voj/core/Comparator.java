//package cn.edu.nciae.judgecenter.voj.core;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.LineIterator;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Desc : Use to compare the user's output and standard output.
// * @author RexALun
// * @since 2019-12-29
// */
//
//@Component
//public class Comparator {
//
//	/**
//	 * Desc : Use to get the result of two output is same.
//	 * @param standardOutputFilePath - The path of standard output file.
//	 * @param outputFilePath - The path of the user's output file.
//	 * @return Is the standard output and user's output same.
//	 */
//	public boolean isOutputEqualStandard(String standardOutputFilePath, String outputFilePath) throws IOException {
//		File stdFile = new File(standardOutputFilePath);
//		File file = new File(outputFilePath);
//
//		LineIterator stdFileItr = FileUtils.lineIterator(stdFile, "UTF-8");
//		LineIterator fileItr = FileUtils.lineIterator(file, "UTF-8");
//		boolean isEqual = isFileOutputEqualStandard(stdFileItr, fileItr);
//
//		stdFileItr.close();
//		fileItr.close();
//		return isEqual;
//	}
//
//	/**
//	 * Desc : Compare the user's output and standard output.
//	 * @param stdFileItr - The iterator for the standard output file.
//	 * @param fileItr - The iterator for the user's output file.
//	 * @return Is the standard output and user's output same.
//	 */
//	private boolean isFileOutputEqualStandard(LineIterator stdFileItr, LineIterator fileItr) {
//		try {
//			while ( stdFileItr.hasNext() && fileItr.hasNext() ) {
//				String stdLine = stdFileItr.nextLine();
//				String line = fileItr.nextLine();
//
//				if ( !isLineOutputEqualStandard(stdLine, line) ) {
//					return false;
//				}
//			}
//			while ( stdFileItr.hasNext() ) {
//				String line = stdFileItr.nextLine();
//				if ( !isLineEmpty(line, 0) ) {
//					return false;
//				}
//			}
//			while ( fileItr.hasNext() ) {
//				String line = fileItr.nextLine();
//				if ( !isLineEmpty(line, 0) ) {
//					return false;
//				}
//			}
//		} catch ( OutOfMemoryError ex ) {
//			LOGGER.catching(ex);
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * Desc : Compare the two lines.
//	 * @param stdLine - The line from the standard output.
//	 * @param line - The line from the user's output.
//	 * @return Are the lines equaled.
//	 */
//	private boolean isLineOutputEqualStandard(String stdLine, String line) {
//		int i = 0, j = 0;
//		for ( ; i < stdLine.length() && j < line.length(); ++ i, ++ j ) {
//			if (  stdLine.charAt(i) != line.charAt(j) ) {
//				if ( stdLine.charAt(i) == '\n' ) {
//					return isLineEmpty(line, j);
//				} else if ( line.charAt(j) == '\n' ) {
//					return isLineEmpty(stdLine, i);
//				}
//				return false;
//			}
//		}
//		while ( i < stdLine.length() ) {
//			if ( !isLineEmpty(stdLine, i) ) {
//				return false;
//			}
//			++ i;
//		}
//		while ( j < line.length() ) {
//			if ( !isLineEmpty(line, j) ) {
//				return false;
//			}
//			++ j;
//		}
//		return true;
//	}
//
//	/**
//	 * Desc : Overlook the space and \n.
//	 * @param line - The line of output.
//	 * @param startIndex - The start index to compare.
//	 * @return Does the line just include space and \n.
//	 */
//	private boolean isLineEmpty(String line, int startIndex) {
//		for ( int i = startIndex; i < line.length(); ++ i ) {
//			if ( !(line.charAt(i) == ' ' || line.charAt(i) == '\n') ) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * Desc : Logger.
//	 */
//	private static final Logger LOGGER = LogManager.getLogger(Comparator.class);
//}
