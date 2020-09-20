package cn.edu.nciae.contentcenter.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/13 6:23 AM
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class FileConfig {
}
