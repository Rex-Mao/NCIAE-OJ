package cn.edu.nciae.judgecenter.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/4 11:20 AM
 */
@Slf4j
public class SystemEnvInfo {

    public static void getSystemEnvironment() {
        log.info("System Information: " );
        log.info("\tOperating System Name: " + System.getProperty("os.name"));
        log.info("\tOperating System Version: " + System.getProperty("os.version"));
        log.info("\tJava VM Name: " + System.getProperty("java.vm.name"));
        log.info("\tJava Runtime Version: " + System.getProperty("java.runtime.version"));
    }
}
