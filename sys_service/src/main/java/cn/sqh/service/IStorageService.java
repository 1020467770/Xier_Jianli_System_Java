package cn.sqh.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    void storeFile(MultipartFile file, String fileName, String formatName)throws Exception;

    Resource loadFileAsResource(String fileName);
}
