package com.adminremit.auth.repository;

import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.FeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {
    public List<Partner> findAllByPublish(Boolean publish);
    public List<Partner> findAll();
    public List<Partner> findAllByProductAndIsDeleted(Product product, Boolean isDeleted);
    public List<Partner> findAllByIsDeleted(Boolean isDeleted);
	public List<Partner> findAllByIsDeletedAndPublishTrue(boolean b);
  @Query("Select a from Partner a where isDeleted = :isDeleted order by a.partnerName ASC")
	public List<Partner> findAllByIsDeletedAndOrderByPartnerNameAsc(@Param("isDeleted") Boolean isDeleted);
}
