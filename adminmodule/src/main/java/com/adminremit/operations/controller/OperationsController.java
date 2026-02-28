package com.adminremit.operations.controller;

import com.adminremit.operations.dto.FileUploadDTO;
import com.adminremit.operations.dto.SearchFileUploadDTO;
import com.adminremit.operations.model.BankAccountDetails;
import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.repository.OperationCustomSearch;
import com.adminremit.operations.service.BankDetailsService;
import com.adminremit.masters.service.CurrenciesService;
import com.adminremit.operations.service.FilesStorageService;
import com.adminremit.operations.service.OperationFileUploadService;
import com.adminremit.operations.service.TransferAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class OperationsController {

    private static final Logger LOG = LoggerFactory.getLogger(OperationsController.class);

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private BankDetailsService bankDetailsService;

    @Autowired
    private TransferAccountService transferAccountService;

    @Autowired
    private OperationFileUploadService operationFileUploadService;

    @Autowired
    OperationCustomSearch operationCustomSearch;

    @Autowired
    FilesStorageService filesStorageService;




    @RequestMapping(value = "/fileupload/operationlist",method = RequestMethod.GET)
    public ModelAndView opsListView(ModelAndView modelAndView, BindingResult bindingResult) throws ParseException {

        List<OperationFileUpload> operationFileUploads = operationFileUploadService.getAllOperationDetails();
        SearchFileUploadDTO searchFileUpload = new SearchFileUploadDTO();
        modelAndView.addObject("searchFileUpload",searchFileUpload);
        modelAndView.addObject("operationFileUpload",operationFileUploads);
        modelAndView.setViewName("fileupload/operation/operation_list");
        return modelAndView;
    }

    @RequestMapping(value = "/fileupload/searchoperationlist/",method = RequestMethod.POST)
    public ModelAndView searchOpListView(ModelAndView modelAndView, @ModelAttribute SearchFileUploadDTO searchFileUpload , BindingResult bindingResult) throws ParseException {

        Date uploadDate= null;
        if(searchFileUpload.getUploadDate()!=null && !searchFileUpload.getUploadDate().isEmpty()){
            uploadDate = new SimpleDateFormat("dd-MM-yyyy").parse(searchFileUpload.getUploadDate());
        }
        LOG.info("Type :"+searchFileUpload.getAccountType()+" User:"+searchFileUpload.getUserId()+" fileName:"+searchFileUpload.getFileName()+" Date:"+uploadDate);
        List<OperationFileUpload> operationFileUploadsCustomSearch = operationCustomSearch.getAllList(searchFileUpload.getAccountType(),searchFileUpload.getUserId(),searchFileUpload.getFileName(),uploadDate);
        if(operationFileUploadsCustomSearch!=null && operationFileUploadsCustomSearch.size()>0) {
            LOG.info(operationFileUploadsCustomSearch.get(0).getAccountNumber());
            LOG.info(" " + operationFileUploadsCustomSearch.get(0).getCreateAt());
        }
        else {
            LOG.info("Unable to Fetch Record");
        }


        modelAndView.addObject("searchFileUpload",searchFileUpload);

        modelAndView.addObject("operationFileUpload",operationFileUploadsCustomSearch);

        modelAndView.setViewName("fileupload/operation/operation_list");
        return modelAndView;
    }

    @RequestMapping(value = "/fileupload/uploadfile",method = RequestMethod.GET)
    public ModelAndView opsUploadFile(ModelAndView modelAndView, @ModelAttribute FileUploadDTO fileUpload ,BindingResult bindingResult) {
        //modelAndView.addObject("designation",designation);
        modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
        modelAndView.addObject("bankdetails",bankDetailsService.getAllBankDetails());
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        modelAndView.addObject("fileUploadDTO",fileUploadDTO);
        modelAndView.setViewName("fileupload/operation/uploadfile");
        return modelAndView;
    }



    @RequestMapping(value = "/fileupload/updateOperations",method = RequestMethod.POST)
    public ModelAndView updateOperations(ModelAndView modelAndView,@ModelAttribute FileUploadDTO fileUpload, BindingResult bindingResult) {
        LOG.info(fileUpload.getId()+"    "+fileUpload.getAccountNumber()+"  "+fileUpload.getAccountType()+"   "+fileUpload.getUploadType()+"   "+fileUpload.getIsApproved());
        filesStorageService.update(fileUpload);

        SearchFileUploadDTO searchFileUpload = new SearchFileUploadDTO();
        modelAndView.addObject("searchFileUpload",searchFileUpload);

        List<OperationFileUpload> operationFileUploads = operationFileUploadService.getAllOperationDetails();
        modelAndView.addObject("operationFileUpload",operationFileUploads);
        modelAndView.setViewName("fileupload/operation/operation_list");
        return modelAndView;
    }

    @RequestMapping(value = "/fileupload/uploadOperationfile",method = RequestMethod.POST)
    public ModelAndView uploadFile(ModelAndView modelAndView, @ModelAttribute FileUploadDTO fileUpload,@RequestParam(value = "interimFile") MultipartFile[] image , BindingResult bindingResult) {
        LOG.info("uploadFile    "+fileUpload.getAccountNumber()+"  "+fileUpload.getAccountType()+"   "+fileUpload.getUploadType()+"  "+image.length);
        boolean isFileUploadSuccess = filesStorageService.save(fileUpload);
        LOG.info("isFileUploadSuccess" + isFileUploadSuccess);
        if(isFileUploadSuccess)
        {
            SearchFileUploadDTO searchFileUpload = new SearchFileUploadDTO();
            modelAndView.addObject("searchFileUpload",searchFileUpload);
            List<OperationFileUpload> operationFileUploads = operationFileUploadService.getAllOperationDetails();
            modelAndView.addObject("operationFileUpload",operationFileUploads);
            modelAndView.setViewName("fileupload/operation/operation_list");

        }
        else
        {
            modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
            modelAndView.addObject("bankdetails",bankDetailsService.getAllBankDetails());
            FileUploadDTO fileUploadDTO = new FileUploadDTO();
            modelAndView.addObject("fileUploadDTO",fileUploadDTO);
            modelAndView.addObject("errorMessage","Please Upload Valid excel file");
            modelAndView.setViewName("fileupload/operation/uploadfile");

        }

        return modelAndView;
    }


    @RequestMapping(name = "account_id", value = {"/fileupload/uploadfile/{bankid}"}, method = RequestMethod.GET)
    public List<BankAccountDetails> receiverDetails(@PathVariable("bankid") Long bankId) throws IOException{

        List<BankAccountDetails> bankAccountList = null;

        bankAccountList = bankDetailsService.getAllBankDetails(bankId);

        LOG.info("Bank Account Details "+bankAccountList);
        return bankAccountList;
    }

    @RequestMapping(value = "/fileupload/viewfile/{id}",method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable("id") String id, ModelAndView modelAndView, BindingResult bindingResult) throws IOException {

        FileInfo fileInfos = filesStorageService.viewFile(id);
        File file = new File(fileInfos.getUrl());
        InputStream is = new BufferedInputStream(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+fileInfos.getName());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(is));
    }

    @RequestMapping(value = "/fileupload/operationview/{id}",method = RequestMethod.GET)
    public ModelAndView operationview(@PathVariable("id") Long id,ModelAndView modelAndView, BindingResult bindingResult) throws IOException {
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        OperationFileUpload operationFileUpload = operationFileUploadService.getOperationView(id);
        fileUploadDTO.setId(operationFileUpload.getId());
        fileUploadDTO.setUploadType(operationFileUpload.getUploadType());
        fileUploadDTO.setAccountType(operationFileUpload.getAccountType());
        fileUploadDTO.setStatementType(operationFileUpload.getStatementType());
        fileUploadDTO.setCurrencyCode(operationFileUpload.getCurrencyCode());
        fileUploadDTO.setBankDetails(operationFileUpload.getBankDetails());
        fileUploadDTO.setAccountNumber(operationFileUpload.getAccountNumber());
        if(operationFileUpload.getStatus().equalsIgnoreCase(filesStorageService.PENDING))
            fileUploadDTO.setIsApproved(false);

        List<BankAccountDetails> bankAccountList = bankDetailsService.getAllBankDetails(operationFileUpload.getBankDetails());

        List<FileInfo> fileInfos = filesStorageService.getFileList(operationFileUpload);


        modelAndView.addObject("currencies",currenciesService.listOfCurrencies());
        modelAndView.addObject("bankdetails",bankDetailsService.getAllBankDetails());
        modelAndView.addObject("bankAccount",bankAccountList);
        modelAndView.addObject("files",fileInfos);

        modelAndView.addObject("fileUploadDTO",fileUploadDTO);

        modelAndView.setViewName("fileupload/operation/operation_view");
        return modelAndView;
    }

    @RequestMapping(value = "/fileupload/operationdelete/{id}",method = RequestMethod.GET)
    public  @ResponseBody  String operationDelete(@PathVariable("id") Long id, ModelAndView modelAndView, BindingResult bindingResult) throws IOException {
        OperationFileUpload operationDelete = operationFileUploadService.getOperationView(id);
        if(operationDelete != null){
            operationDelete.setIsDeleted(true);
            operationFileUploadService.save(operationDelete);
            return "Item has been deleted";
        }else{
            return "Item not deleted yet";
        }
    }

    @RequestMapping(value = "operationFileDetails/{id}/approve", method = RequestMethod.GET)
    public ModelAndView operationApproved(@PathVariable("id") Long id,ModelAndView modelAndView, BindingResult bindingResult) throws IOException {
        OperationFileUpload operationApproved = operationFileUploadService.getOperationView(id);
        if(operationApproved != null){
            operationApproved.setStatus("Approved");
            operationFileUploadService.save(operationApproved);
        }
        SearchFileUploadDTO searchFileUpload = new SearchFileUploadDTO();
        modelAndView.addObject("searchFileUpload",searchFileUpload);
        List<OperationFileUpload> operationFileUploads = operationFileUploadService.getAllOperationDetails();
        modelAndView.addObject("operationFileUpload",operationFileUploads);
        modelAndView.setViewName("fileupload/operation/operation_list");
        return modelAndView;
    }

    @RequestMapping(value = "operationFileDetails/{id}/reject", method = RequestMethod.GET)
    public ModelAndView operationReject(@PathVariable("id") Long id,ModelAndView modelAndView, BindingResult bindingResult) throws IOException {
        OperationFileUpload operationReject = operationFileUploadService.getOperationView(id);
        if(operationReject !=null){
            operationReject.setIsDeleted(true);
            operationFileUploadService.save(operationReject);
        }
        SearchFileUploadDTO searchFileUpload = new SearchFileUploadDTO();
        modelAndView.addObject("searchFileUpload",searchFileUpload);
        List<OperationFileUpload> operationFileUploads = operationFileUploadService.getAllOperationDetails();
        modelAndView.addObject("operationFileUpload",operationFileUploads);
        modelAndView.setViewName("fileupload/operation/operation_list");
        return modelAndView;
    }

}
