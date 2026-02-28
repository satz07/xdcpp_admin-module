package com.adminremit.operations.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFSCService {

	 public String saveUploadedIFSCFiles(MultipartFile file) throws IOException;
	   
	   
}
