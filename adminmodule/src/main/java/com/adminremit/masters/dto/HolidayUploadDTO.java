package com.adminremit.masters.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HolidayUploadDTO {
    private MultipartFile file;
}
