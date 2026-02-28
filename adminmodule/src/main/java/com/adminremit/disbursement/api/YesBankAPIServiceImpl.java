package com.adminremit.disbursement.api;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adminremit.backoffice.dto.RemittResponseDTO;
import com.adminremit.backoffice.dto.RemittanceDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class YesBankAPIServiceImpl implements YesBankAPIService {
	
	private static final Logger LOG = LoggerFactory.getLogger(YesBankAPIServiceImpl.class);
	
	@Value("${send.remitt.url}")
    private String remittServiceURL;

    @Value("${ifsc.api.clientid}")
    private String ifscApiClientId;

    @Value("${ifsc.api.client.secret}")
    private String ifscApiClientSecret;

    @Value("${ifsc.api.username}")
    private String ifscApiUserName;

    @Value("${ifsc.api.password}")
    private String ifscApiPassword;

    @Value("${send.remitt.url}")
    private String sendRemittUrl;

    @Value("${remit.partner.code}")
    private String partnerCode;

    @Value("${ifsc.api.version}")
    private String version;

    @Value("${remit.me.refno}")
    private String meRefNo;

    @Value("${upi.api.url}")
    private String upiApiURL;

    private final String X_IBM_CLIENT_ID = "X-IBM-Client-Id";

    private final String X_IBM_CLIENT_SECRET = "X-IBM-Client-Secret";
    
    @Autowired
    private OkHttpClient sslOkHttpClient;
	
	@Override
    public String remitTransaction(RemittanceDTO remittanceDTO) throws Exception {
        return this.makeHttpCall(this.benificaryNameCheck(remittanceDTO));
    }
	
	private String makeHttpCall(String soapBody) {
        try {
            String credentials = Credentials.basic(ifscApiUserName, ifscApiPassword);
            okhttp3.MediaType mediaType1 = okhttp3.MediaType.parse("application/xml");
            RequestBody body1 = RequestBody.create(mediaType1, soapBody);
            Request request1 = new Request.Builder()
                    .url(remittServiceURL)
                    .post(body1)
                    .addHeader("Content-Type", "application/xml")
                    .addHeader("Accept", "application/xml")
                    .addHeader(X_IBM_CLIENT_ID, ifscApiClientId)
                    .addHeader(X_IBM_CLIENT_SECRET, ifscApiClientSecret)
                    .addHeader("Authorization", credentials)
                    .build();

            Response response1 = sslOkHttpClient.newCall(request1).execute();
            String body = response1.body().string();
            LOG.info("YB Response:" + body);
            return convertXMLToJson(body);

        } catch (Exception e) {
        	LOG.info("Error occurred while calling yesbank api" + e.getMessage());
        	LOG.info(e.getStackTrace().toString());
            e.printStackTrace();
        }
        return null;
    }

	@Override
    public String benificaryNameCheck(RemittanceDTO remittanceDTO) throws RemoteException {
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ser=\"http://www.quantiguous.com/services\">\r\n  " +
                " <soap:Header />\r\n  " +
                "<soap:Body>\r\n      " +
                "<ser:startRemit>\r\n         " +
                "<ser:version>"+version+"</ser:version>\r\n         " +
                "<ser:uniqueRequestNo>" + remittanceDTO.getRefId() + "</ser:uniqueRequestNo>\r\n       " +
                "  <ser:partnerCode>"+partnerCode+"</ser:partnerCode>\r\n        " +
                " <ser:remitterType>"+remittanceDTO.getRemitterType()+"</ser:remitterType>\r\n         " +
                "<ser:remitterName>\r\n           " +
                " <ser:fullName>"+remittanceDTO.getRemitterFullName()+"</ser:fullName>\r\n    " +
                "     </ser:remitterName>\r\n       " +
                "  <ser:remitterAddress>\r\n         " +
                "   <ser:address1>"+remittanceDTO.getRemitterAddress()+"</ser:address1>\r\n  " +
                "          <ser:country>"+remittanceDTO.getRemitterCountry()+"</ser:country>\r\n    " +
                "     </ser:remitterAddress>\r\n         " +
                "<ser:remitterContact />\r\n         <ser:remitterIdentities>\r\n          " +
                "    <ser:identity>\r\n              " +
                " <ser:idType>partnerProvidedID</ser:idType>\r\n         " +
                "      <ser:idNumber>"+remittanceDTO.getRemitterIdNumber()+"</ser:idNumber>\r\n      " +
                "      </ser:identity>\r\n         </ser:remitterIdentities>\r\n    " +
                "     <ser:beneficiaryType>"+remittanceDTO.getBeneficiaryType()+"</ser:beneficiaryType>\r\n      " +
                "   <ser:beneficiaryName>\r\n            <ser:fullName>"+remittanceDTO.getBeneFullName()+"</ser:fullName>\r\n       " +
                "  </ser:beneficiaryName>\r\n         <ser:beneficiaryAddress>\r\n        " +
                "    <ser:address1>"+remittanceDTO.getBeneficiaryAddress()+"</ser:address1>\r\n       " +
                "  </ser:beneficiaryAddress>\r\n      " +
                "   <ser:beneficiaryContact/>\r\n       " +
                "  <ser:beneficiaryIdentities/>\r\n    " +
                "   <ser:beneficiaryAccountNo>"+remittanceDTO.getBeneficiaryAccountNo()+"</ser:beneficiaryAccountNo>\r\n    " +
                "           <ser:beneficiaryIFSC>"+remittanceDTO.getBeneficiaryIFSC()+"</ser:beneficiaryIFSC>\r\n     " +
                "    <ser:transferType>"+remittanceDTO.getTransferType()+"</ser:transferType>\r\n        " +
                " <ser:transferCurrencyCode>"+remittanceDTO.getTransferCurrencyCode()+"</ser:transferCurrencyCode>\r\n   " +
                "      <ser:transferAmount>"+remittanceDTO.getTransferAmount()+"</ser:transferAmount>\r\n      " +
                "   <ser:remitterToBeneficiaryInfo>"+remittanceDTO.getRemitterToBeneficiaryInfo()+"</ser:remitterToBeneficiaryInfo>\r\n    " +
                "     <ser:purposeCode>"+remittanceDTO.getPurposeCode()+"</ser:purposeCode>\r\n      </ser:startRemit>\r\n " +
                "  </soap:Body>\r\n</soap:Envelope>";
        LOG.info("start Non UPI remit request::::");
        LOG.info(request);
        return request;
    }
	
	public String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String uniqueString = "ANDREMITT_" + String.format("%06d", number);
        return uniqueString;
    }
	
	private String convertXMLToJson(String responseBody) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(responseBody);
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(jsonNode);
            System.out.println("*** Converting XML to JSON ***");
            System.out.println(value);
            return value;
        } catch (JsonParseException e) {
        	LOG.info("Error in JSON parsing" + e.getMessage());
        	e.printStackTrace();
        } catch (JsonMappingException e) {
        	LOG.info("Error in JSON mapping" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
        	LOG.info("Error in IO exception" + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

	@Override
    public String transactionResponse(RemittResponseDTO remittResponseDTO) throws Exception {
        return this.makeHttpCall(this.getTransactionStatus(remittResponseDTO));
    }
	
	@Override
    public String getTransactionStatus(RemittResponseDTO remittResponseDTO) {
        String request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ser=\"http://www.quantiguous.com/services\">\n\t" +
                "<soap:Body>\n\t" +
                "<ser:getStatus>\n\t" +
                "<ser:version>"+version+"</ser:version>\n\t" +
                "<ser:partnerCode>"+partnerCode+"</ser:partnerCode>\n\t" +
                "<ser:requestReferenceNo>" + remittResponseDTO.getRequestReferenceNo() + "</ser:requestReferenceNo>\n\t" +
                "</ser:getStatus>\n\t" +
                "</soap:Body>\n" +
                "</soap:Envelope>";
        return request;
    }
	
	public String getStatus(String referenceNumber) {
		return this.makeHttpCall(this.getStatusByReferenceId(referenceNumber));
	}
	
	public String getBalance() {
		return this.makeHttpCall(this.getBalanceOfAccount());
	}
	
	private String getStatusByReferenceId(String referenceNumber) {
        String request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ser=\"http://www.quantiguous.com/services\">\n\t" +
                "<soap:Body>\n\t" +
                "<ser:getStatus>\n\t" +
                "<ser:version>"+version+"</ser:version>\n\t" +
                "<ser:partnerCode>"+partnerCode+"</ser:partnerCode>\n\t" +
                "<ser:requestReferenceNo>" + referenceNumber + "</ser:requestReferenceNo>\n\t" +
                "</ser:getStatus>\n\t" +
                "</soap:Body>\n" +
                "</soap:Envelope>";
        return request;
    }

	private String getBalanceOfAccount() {
		String request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" \r\n"
				+ "xmlns:ser=\"http://www.quantiguous.com/services\">\r\n"
				+ "<soap:Header/>\n\t"
				+ "<soap:Body>\n\t"
				+ "<ser:getBalance>\n\t"
				+ "<ser:version>"+version+"</ser:version>\n\t"
				+ "<ser:partnerCode>"+partnerCode+"</ser:partnerCode>\r\n"
				+ "</ser:getBalance>\r\n"
				+ "</soap:Body>\r\n"
				+ "</soap:Envelope>";
		return request;
	}
	
	@Override
    public String startUPITransaction(RemittanceDTO remittanceDTO) throws Exception {
        return this.makeHttpCall(this.startUPIRequest(remittanceDTO));
    }
	
	private String startUPIRequest(RemittanceDTO remittanceDTO) {
        String payload = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ser=\"http://www.quantiguous.com/services\">\r\n" +
                "<soap:Header/>\r\n   " +
                "<soap:Body>\r\n" +
                "<ser:startRemit>\r\n" +
                "<ser:version>"+version+"</ser:version>\r\n" +
                "<ser:uniqueRequestNo>"+remittanceDTO.getRefId()+"</ser:uniqueRequestNo>\r\n" +
                "<ser:partnerCode>"+remittanceDTO.getPartnerCode()+"</ser:partnerCode>\r\n " +
                "<ser:remitterType>"+remittanceDTO.getRemitterType()+"</ser:remitterType>\r\n " +
                "<ser:remitterName>\r\n<ser:fullName>"+remittanceDTO.getRemitterFullName()+"</ser:fullName>\r\n " +
                "</ser:remitterName>\r\n " +
                "<ser:remitterAddress>\r\n" +
                "<ser:address1>"+remittanceDTO.getRemitterAddress()+"</ser:address1>\r\n" +
                "<ser:address3>"+remittanceDTO.getRemitterAddress()+"</ser:address3>\r\n  " +
                "<ser:postalCode>"+remittanceDTO.getRemitterZipCode()+"</ser:postalCode>\r\n " +
                "<ser:city>"+remittanceDTO.getRemitterCity()+"</ser:city>\r\n " +
                "<ser:stateOrProvince>"+remittanceDTO.getRemitterProvince()+"</ser:stateOrProvince>\r\n " +
                "<ser:country>"+remittanceDTO.getRemitterCountry()+"</ser:country>\r\n  " +
                "</ser:remitterAddress>\r\n " +
                "<ser:remitterContact>\r\n" +
                "<ser:mobileNo>"+remittanceDTO.getRemitterPhone()+"</ser:mobileNo>\r\n " +
                "<ser:emailID>"+remittanceDTO.getRemitterEmail()+"</ser:emailID>\r\n " +
                "</ser:remitterContact>\r\n" +
                "<ser:remitterIdentities>\r\n" +
                "<ser:identity>\r\n" +
                "<ser:idType>partnerProvidedID</ser:idType>\r\n   " +
                "<ser:idNumber>"+remittanceDTO.getRemitterIdNumber()+"</ser:idNumber>\r\n" +
                "</ser:identity>\r\n " +
                "</ser:remitterIdentities>\r\n " +
                "<ser:beneficiaryType>"+remittanceDTO.getBeneficiaryType()+"</ser:beneficiaryType>\r\n " +
                "<ser:beneficiaryName>\r\n" +
                "<ser:fullName>"+remittanceDTO.getBeneFullName()+"</ser:fullName>\r\n " +
                "</ser:beneficiaryName>\r\n " +
                "<ser:beneficiaryVPA>"+remittanceDTO.getBeneficiaryAccountNo()+"</ser:beneficiaryVPA>\r\n " +
                "<ser:beneficiaryAddress>\r\n<ser:address1>address1</ser:address1>\r\n " +
                "<ser:address2>address2</ser:address2>\r\n" +
                "<ser:address3>address3</ser:address3>\r\n" +
                "<ser:postalCode>PBNo1</ser:postalCode>\r\n" +
                "<ser:city>Mumbai</ser:city>\r\n" +
                "<ser:stateOrProvince>Maharashtra</ser:stateOrProvince>\r\n" +
                "<ser:country>IN</ser:country>\r\n " +
                "</ser:beneficiaryAddress>\r\n " +
                "<ser:beneficiaryContact>\r\n" +
                "<ser:mobileNo>9566778899</ser:mobileNo>\r\n " +
                "<ser:emailID>rajani@gmail.com</ser:emailID>\r\n " +
                "</ser:beneficiaryContact>\r\n " +
                "<ser:beneficiaryIdentities>\r\n" +
                "</ser:beneficiaryIdentities>\r\n" +
                "<ser:transferType>"+ remittanceDTO.getTransferType()+"</ser:transferType>\r\n " +
                "<ser:transferCurrencyCode>"+remittanceDTO.getTransferCurrencyCode()+"</ser:transferCurrencyCode>\r\n " +
                "<ser:transferAmount>"+remittanceDTO.getTransferAmount()+"</ser:transferAmount>\r\n " +
                "<ser:remitterToBeneficiaryInfo>FamilyExpenses</ser:remitterToBeneficiaryInfo>\r\n " +
                "<ser:purposeCode>"+remittanceDTO.getPurposeCode()+"</ser:purposeCode>\r\n " +
                "</ser:startRemit>\r\n" +
                "</soap:Body>\r\n" +
                "</soap:Envelope>";
        
        LOG.info("start UPI remit request::::");
        LOG.info(payload);
        
        return payload;
    }

	@Override
    public String getUPIDetails(String upiId) throws Exception {
        return this.makeUPIHttpCall(this.validateUPI(upiId));
    }
	
	@Override
    public String validateUPI(String upiId) throws Exception {
        String request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
                "xmlns:upir=\"http://xmlns.yesbank.com/UPIRemitServices/\">\n" +
                "<soap:Header/>\n" +
                "\t<soap:Body>\n" +
                "\t<upir:ValidateVPA>\n" +
                "\t<PartnerCode>"+partnerCode+"</PartnerCode>\n" +
                "\t<MeRefNo>"+meRefNo+"</MeRefNo>\n" +
                "\t<VirtualAddress>"+upiId+"</VirtualAddress>\n" +
                "\t</upir:ValidateVPA>\n" +
                "\t</soap:Body>\n" +
                "</soap:Envelope>";
        return request;
    }
	
	private String makeUPIHttpCall(String soapBody) {
        try {
            OkHttpClient client1 = new OkHttpClient();
            String credentials = Credentials.basic(ifscApiUserName, ifscApiPassword);
            okhttp3.MediaType mediaType1 = okhttp3.MediaType.parse("text/xml");
            RequestBody body1 = RequestBody.create(mediaType1, soapBody);
            Request request1 = new Request.Builder()
                    .url(upiApiURL)
                    .post(body1)
                    .addHeader("content-type", "text/xml")
                    .addHeader(X_IBM_CLIENT_ID, ifscApiClientId)
                    .addHeader(X_IBM_CLIENT_SECRET, ifscApiClientSecret)
                    .addHeader("Authorization", credentials)
                    .build();

            LOG.info("Making http call, " + body1);
            Response response1 = client1.newCall(request1).execute();
            LOG.info("http call response, " + response1.body().string());
            return convertXMLToJson(response1.body().string());

        } catch (Exception e) {
        	LOG.error("Error occurred while making http call, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

