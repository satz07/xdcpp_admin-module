package com.adminremit.operations.repository;


import com.adminremit.operations.model.BankAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountDetails, Long> {

    public List<BankAccountDetails> findAllByBankId(Long id);
}
