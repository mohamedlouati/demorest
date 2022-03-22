package com.example.demorest.service;


import com.example.demorest.config.FileStorageProperties;
import com.example.demorest.exception.FileStorageException;
import com.example.demorest.exception.MyFileNotFoundException;
import com.example.demorest.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {

        if (!(file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT1) ||
                file.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.word_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.video_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.presontation_FILE_FORMAT) ||
                file.getOriginalFilename().endsWith(AppConstants.pdf_FILE_FORMAT)
        ))
            throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
                throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME + fileName);
            }
            String newFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + fileName;
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, fileName), ex);
        }

    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName, ex);
        }
    }
}