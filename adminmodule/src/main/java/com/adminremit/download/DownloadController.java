package com.adminremit.download;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/document")
public class DownloadController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);
	
	@Value("${file.upload.operations}")
	private String documentLocation;	
	
	@RequestMapping(path = "/download/{file-name}")
    public ResponseEntity<?> getPDF(@PathVariable("file-name") Optional<String> fileNameHolder) throws Exception{
        if(!fileNameHolder.isPresent()){
            throw new Exception("File not found...");
        }
        final String absoluteFilePath = documentLocation +"/" +fileNameHolder.get();
        LOGGER.debug("Absolute File Name: {}", absoluteFilePath);
        final Path path = Paths.get(absoluteFilePath);
        if(!Files.isReadable(path)){
            throw new Exception("File not found...");
        }
        final byte[] bytes = Files.readAllBytes(path);
        final String contentDisposition = "attachment; filename="+fileNameHolder.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

}
