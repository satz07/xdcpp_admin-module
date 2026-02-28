package com.adminremit.operations.repository;

import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileInfo, String> {


    FileInfo findAllById(String id);
    List<FileInfo> findAllByOperationFile(OperationFileUpload operationFileUpload);
    FileInfo findAllByName(String name);

}
