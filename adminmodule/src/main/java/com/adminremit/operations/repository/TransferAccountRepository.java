package com.adminremit.operations.repository;

import com.adminremit.operations.model.FileInfo;
import com.adminremit.operations.model.TransferAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransferAccountRepository  extends JpaRepository<TransferAccountDetails, Long> {

    List<TransferAccountDetails> findAllByFileInfo(FileInfo fileInfo);
    List<TransferAccountDetails> findAllBytransactionRefNo(String refNo);
    
    /**
     * 
     * @param bankRef
     * @param senderName
     * @param accountNumber
     * @return null where multiple records are found for the matching account details  
     */
    @Query(value = "SELECT account from TransferAccountDetails account WHERE account.bankRef =:bankRef and account.senderName =:senderName and "
    		+ "account.accountNumber =:accountNumber ")
    List<TransferAccountDetails> findAccountIfAlreadyPresent(@Param("bankRef") String bankRef,@Param("senderName") String senderName,@Param("accountNumber") BigDecimal accountNumber);

}
