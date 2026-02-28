package com.adminremit.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminremit.user.model.DocumentVerification;
import com.adminremit.user.repository.UserDocVerificationRepo;

@Service
public class DocVerificationServiceImpl implements DocVerificationService {
	
	@Autowired
    private UserDocVerificationRepo userDocVerificationRepo;
	
	private static final Logger LOG = LoggerFactory.getLogger(DocVerificationServiceImpl.class);

	@Override
    public DocumentVerification getDocumentByUserId(String userId){
    	LOG.info("Getting documents by userid:" + userId);
        return userDocVerificationRepo.findTopByUserIdOrderByIdDesc(userId);
    }
}
