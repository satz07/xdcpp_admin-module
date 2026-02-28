package com.adminremit.operations.repository;

import com.adminremit.masters.models.BankMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankMasterRepository  extends JpaRepository<BankMaster, Long> {

    List<BankMaster> findAllById(Long id);
}
