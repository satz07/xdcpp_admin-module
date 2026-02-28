package com.adminremit.auth.repository;

import com.adminremit.auth.models.GroupMaster;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.LocationMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findAllByPublish(Boolean publish);
    public Page<Product> findAll(Pageable pageable);
    @Query(value = "SELECT nextval('remit.productCodeSeq')", nativeQuery = true)
    public Long getNextValProductCodeSeq();
    public List<Product> findAll();
    public List<Product> findAllByIsDeleted(Boolean isDeleted);
    public List<Product> findAllByIsDeletedAndOwnerDesignation(Boolean isDeleted, OwnerDesignation ownerDesignation);
    public List<Product> findAllByIsDeletedAndLocationMaster(Boolean isDeleted, LocationMaster locationMaster);
	public List<Product> findAllByIsDeletedAndPublishTrue(boolean b);
	public Page<Product> findAllByPublishTrue(Pageable pageable);
	public List<Product> findAllByIsDeletedAndOwnerDesignationAndPublishTrue(boolean b, OwnerDesignation byId);
	public List<Product> findAllByIsDeletedAndLocationMasterAndPublishTrue(boolean b, LocationMaster locationById);
}
