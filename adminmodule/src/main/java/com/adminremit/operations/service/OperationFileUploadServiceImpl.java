package com.adminremit.operations.service;

import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.repository.OperationFileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationFileUploadServiceImpl implements  OperationFileUploadService{

    private static final Logger LOG = LoggerFactory.getLogger(OperationFileUploadServiceImpl.class);


    @Autowired
    OperationFileDetailsRepository operationFileDetailsRepository;


    @Override
    public OperationFileUpload save(OperationFileUpload operationFileUpload) {

            OperationFileUpload operationFileUpload1 = operationFileDetailsRepository.save(operationFileUpload);
            return operationFileUpload1;

    }

    @Override
    public List<OperationFileUpload> getAllOperationDetails() {

        List<OperationFileUpload> operationFileUpload1 = operationFileDetailsRepository.findAllByIsDeleted(false);
        return operationFileUpload1;

    }

    @Override
    public OperationFileUpload getOperationView(Long id) {
        OperationFileUpload operationFileUpload = operationFileDetailsRepository.findById(id).get();
        return operationFileUpload;
    }
}
