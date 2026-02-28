package com.adminremit.common.util;

import com.adminremit.masters.exception.ValidationException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@UtilityClass
public class ExcelUtils {

  private static final DataFormatter dataFormatter;

  static {
    dataFormatter = new DataFormatter();
  }

  public static Workbook createWorkbook(final MultipartFile file) {
    log.debug("Reading file and creating excel workbook. Original file name:{}, file name:{}", file.getOriginalFilename(), file.getName());
    try {
      return WorkbookFactory.create(file.getInputStream());
    } catch (final IOException e) {
      log.error("File stream not readable. Original file name:{}, file name:{}", file.getOriginalFilename(), file.getName());
      throw new ValidationException(e.getMessage());
    } catch (final EncryptedDocumentException e) {
      log.error("Could not read.File is encrypted. Original file name:{}, file name:{}", file.getOriginalFilename(), file.getName());
      throw new ValidationException(e.getMessage());
    }
  }

  public static String getStringCellValue(final Row currentRow, final int index) {
    final String text = dataFormatter.formatCellValue(currentRow.getCell(index));
    if (text.isEmpty()) {
      return null;
    }
    return text;
  }

  public static Integer getIntegerValue(final String number) {
    try {
      return Integer.parseInt(number);
    } catch (final NumberFormatException e) {
      return null;
    }
  }


}
