package com.adminremit.auth.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.models.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {
    public RoleMaster findByRoleName(String roleName);
    public List<RoleMaster> findAllByPublish(Boolean publish);
    public List<RoleMaster> findAll();
    public List<RoleMaster> findAllByIsDeleted(Boolean isDeleted);
}
