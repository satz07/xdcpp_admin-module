package com.adminremit.operations.repository;

import com.adminremit.operations.model.OperationFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationFileDetailsRepository extends JpaRepository<OperationFileUpload, Long>, JpaSpecificationExecutor<OperationFileUpload> {

    List<OperationFileUpload> findAllByIsDeleted(boolean b);
}

