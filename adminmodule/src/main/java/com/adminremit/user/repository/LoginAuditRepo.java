package com.adminremit.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.user.enums.UserLoginAuditStatus;
import com.adminremit.user.model.UserLoginAudit;

@Repository
public interface LoginAuditRepo extends JpaRepository<UserLoginAudit, Long> {
    List<UserLoginAudit> findByEmail(String email);    
    UserLoginAudit findTopByEmailAndAuditTypeOrderByIdDesc(String email, UserLoginAuditStatus auditType);
    UserLoginAudit findTopByEmailAndAuditTypeAndRefIdOrderByIdDesc(String email, UserLoginAuditStatus auditType, String refId);
    UserLoginAudit findTopByEmailAndAuditTypeAndBeneficiaryIdOrderByIdDesc(String email, UserLoginAuditStatus auditType, long beneficaryId);
}


