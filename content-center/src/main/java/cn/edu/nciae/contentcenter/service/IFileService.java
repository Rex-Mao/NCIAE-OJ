package cn.edu.nciae.contentcenter.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/13 6:07 AM
 */
public interface IFileService {
    /**
     * desc : save file to the local
     * @param multipartFile -
     * @param folderPathname - a middle pathname between file root path and the target path
     * @return the absolute path of file at local
     */
    String saveLocalFile(MultipartFile multipartFile, String folderPathname);
}
