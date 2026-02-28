package com.adminremit.operations.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CashpickupServiceGlobal {

	 public String saveUploadedCashpickupFiles(MultipartFile file) throws IOException;
	   
	   
}
