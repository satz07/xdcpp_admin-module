package com.adminremit.operations.service;

import com.adminremit.operations.dto.FileUploadDTO;
import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface FilesStorageService {
    public static String PENDING = "Pending";
    public static String APPROVED = "Approved";


    public String saveUploadedFiles(MultipartFile file) throws IOException;
    public boolean save(FileInfo fileInfo) throws IOException, ParseException;

    public boolean save(FileUploadDTO form);


    public FileInfo viewFile(String id) throws IOException;

    public boolean update(FileUploadDTO form);

    public List<FileInfo> getFileList(OperationFileUpload operationFileUpload) throws IOException;





}
