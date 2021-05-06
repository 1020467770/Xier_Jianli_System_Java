package cn.sqh.service.impl;

import cn.sqh.exception.FileStorageException;
import cn.sqh.service.IStorageService;
import cn.sqh.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

@Service
public class StorageServiceImpl implements IStorageService {

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    private final Path fileStoragePath;

    @Autowired
    @Qualifier("taskExecutor")
    private Executor asyncExecutor;

    public StorageServiceImpl(@Value("${file.upload-dir}")String fileStorageLocation){
        try {
            this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new FileStorageException("无法创建文件夹");
        }
    }


    @Override
    public void storeFile(MultipartFile file, String fileName, String formatName) throws Exception{
        if (fileName.contains("..")) {
            throw new FileStorageException("文件名不符合要求");
        }
        asyncExecutor.execute(() -> {
            try {
                file.transferTo(new File(this.fileStorageLocation + File.separator + formatName));//存储文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Resource loadFileAsResource(String fileFormatName) {
        try {
            Path filePath = this.fileStoragePath.resolve(fileFormatName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("未找到文件" + fileFormatName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("未找到文件" + fileFormatName);
        }
    }
}