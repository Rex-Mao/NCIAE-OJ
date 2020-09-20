package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.configuration.FileProperties;
import cn.edu.nciae.contentcenter.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/13 6:16 AM
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    private static final String SEPARATOR = File.separator;

    private static final String PROTOCOL = "file:///";

    @Autowired
    private FileProperties fileProperties;

    /**
     * desc : save file to the local
     * @param multipartFile -
     * @param folderPathname - a middle pathname between file root path and the target path
     * @return the absolute path of file at local
     */
    @Override
    public String saveLocalFile(MultipartFile multipartFile, String folderPathname) {
        String filename = generateFilename(multipartFile.getOriginalFilename());
        String basePath = fileProperties.getLocation() + "/" + folderPathname + "/";
        File folder = new File(basePath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }

        String absolutePath = basePath + filename;
        File tempFile = new File(absolutePath);
        while (tempFile.exists()) {
            filename = generateFilename(multipartFile.getOriginalFilename());
            absolutePath = basePath + filename;
            tempFile = new File(absolutePath);
        }
        try {
            Files.write(Paths.get(absolutePath), multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("File saved success. path: [{}].", absolutePath);
        return absolutePath;
    }

    /**
     * desc : generate a filename
     * @param originFilename -
     * @return String
     */
    private String generateFilename(String originFilename) {
        Random random = new Random();
        return random.nextLong() + originFilename;
    }
}
