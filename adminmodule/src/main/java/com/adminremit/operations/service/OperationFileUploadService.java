package com.adminremit.operations.service;

import com.adminremit.operations.model.OperationFileUpload;

import java.util.List;

public interface OperationFileUploadService {

    OperationFileUpload save(OperationFileUpload operationFileUpload);
    public List<OperationFileUpload> getAllOperationDetails();
    OperationFileUpload getOperationView(Long id);

}
