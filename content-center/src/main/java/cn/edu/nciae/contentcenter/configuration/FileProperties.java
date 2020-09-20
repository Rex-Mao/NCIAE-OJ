package cn.edu.nciae.contentcenter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/13 6:18 AM
 */
@ConfigurationProperties(prefix = "cn.edu.nciae.contentcenter.file")
public class FileProperties {

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
