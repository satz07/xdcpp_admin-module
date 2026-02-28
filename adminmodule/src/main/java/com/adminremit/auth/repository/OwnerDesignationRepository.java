package com.adminremit.auth.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.LocationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerDesignationRepository extends JpaRepository<OwnerDesignation,Long> {
    public List<OwnerDesignation> findAllByPublish(Boolean publish);
    public List<OwnerDesignation> findAll();
    public List<OwnerDesignation> findAllByIsDeleted(Boolean isDeleted);
    public OwnerDesignation findFirstByDesignationAndIsDeleted(String designation,Boolean isDeleted);
}
