package cn.edu.nciae.judgecenter.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Contains helper methods for loading native libraries, particularly JNI.
 * 
 * @author gkubisa
 */
@Slf4j
public class NativeLibraryLoader {
	/**
	 * Utility classes should not have a public constructor.
	 */
	private NativeLibraryLoader() { }
	
	/**
	 * Loads a native shared library. It tries the standard System.loadLibrary
	 * method first and if it fails, it looks for the library in the current
	 * class path. It will handle libraries packed within jar files, too.
	 *
	 * @param libraryName - name of the library to load
	 * @throws IOException if the library cannot be extracted from a jar file
	 * into a temporary file
	 */
	public static void loadLibrary(String libraryName) throws Exception {
		try {
			System.loadLibrary(libraryName);
		} catch (UnsatisfiedLinkError e) {
			String fileName = System.mapLibraryName(libraryName);
			int dotPosition = fileName.lastIndexOf('.');
			File file = File.createTempFile(fileName.substring(0, dotPosition), fileName.substring(dotPosition));
			file.deleteOnExit();
			
			byte[] buffer = new byte[4096];
			InputStream inputStream = NativeLibraryLoader.class.getClassLoader().getResourceAsStream(fileName);
			OutputStream outputStream = new FileOutputStream(file);

			try {
				while ( inputStream.available() > 0 ) {
					int StreamLength = inputStream.read(buffer);
					if ( StreamLength >= 0 ) {
						outputStream.write(buffer, 0, StreamLength);
					}
				}
			} finally {
				outputStream.close();
				inputStream.close();
			}
			log.info("Starting to load core library...");
			try {
				System.load(file.getAbsolutePath());
			} catch (Error er) {
				er.getCause();
				er.printStackTrace();
			}
			log.info("Finishing load core library...");
		}
	}
}
