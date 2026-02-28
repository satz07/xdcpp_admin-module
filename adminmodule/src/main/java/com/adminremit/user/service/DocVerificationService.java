package com.adminremit.user.service;

import com.adminremit.user.model.DocumentVerification;

public interface DocVerificationService {
	public DocumentVerification getDocumentByUserId(String userid);
}
