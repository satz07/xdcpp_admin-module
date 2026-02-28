package com.adminremit.operations.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adminremit.common.models.Constants;
import com.adminremit.common.util.DateUtil;
import com.adminremit.operations.model.IFSCNew;
import com.adminremit.operations.repository.IFSCRepository;

@Service
public class IFSCServiceImpl implements IFSCService {

    private static final Logger LOG = LoggerFactory.getLogger(OperationFileUploadServiceImpl.class);

    private final Path root = Paths.get("uploads");
    @Value("${file.upload.operations}")
    private String fileUploadLocation;

    @UpdateTimestamp
    private Date updateAt = new Date();

    @Autowired
    IFSCRepository ifscRepository;

    @Override
    public String saveUploadedIFSCFiles(MultipartFile file) throws IOException {
        // Make sure directory exists!
        String isSucess = "false";
        File uploadDir = new File(fileUploadLocation);
        uploadDir.mkdirs();
        StringBuilder sb = new StringBuilder();
        if (!file.isEmpty()) {
            String uploadFilePath = fileUploadLocation + "/"
                    + DateUtil.formatCurrentDateByGivenPattern(Constants.FILE_UPLOAD_DATE_TIME_PATTERN) + "_"
                    + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            sb.append(uploadFilePath);

            File fileExcel = new File(uploadFilePath);
            try (FileInputStream excelFile = new FileInputStream(fileExcel);
                 Workbook workbooks = WorkbookFactory.create(excelFile)) {

                Sheet currentSheet = workbooks.getSheetAt(0);
                int numberRows = currentSheet.getPhysicalNumberOfRows();
                LOG.info("numberRows   " + numberRows);
                for (int i = 1; i < currentSheet.getPhysicalNumberOfRows(); i++) {
                    Row row = currentSheet.getRow(i);

                    IFSCNew ifsc = new IFSCNew();
                    IFSCNew ifscNew = ifscRepository.findAllByifscCode(row.getCell(0).getStringCellValue().trim());
                    if (ifscNew == null) {

                        LOG.info("Save=================>" + row.getCell(0));
                        ifsc.setIfscCode(row.getCell(0).getStringCellValue().trim());
                        ifsc.setBankName(row.getCell(1).getStringCellValue().trim());
                        ifsc.setBranchName(row.getCell(2).getStringCellValue().trim());
                        ifsc.setCity(row.getCell(3).getStringCellValue().trim());
                        ifsc.setCountryCode(row.getCell(4).getStringCellValue().trim());
                        ifsc.setInstaAvailable(row.getCell(5).getBooleanCellValue());
                        ifsc.setInstaCode(row.getCell(6).getStringCellValue());
                        ifscRepository.save(ifsc);
                    } else {
                        LOG.info("Update=================>" + row.getCell(0));
                        long id = ifscNew.getId();
                        Date updatedon = updateAt;
                        String ifsccode = row.getCell(0).getStringCellValue().trim();
                        String bankname = row.getCell(1).getStringCellValue().trim();
                        String branchname = row.getCell(2).getStringCellValue().trim();
                        String city = row.getCell(3).getStringCellValue().trim();
                        ifscRepository.update(id, ifsccode, bankname, branchname, city, updatedon);
                    }
                    // check for Matching
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isSucess = "true";

        }
        return isSucess;
    }
}
