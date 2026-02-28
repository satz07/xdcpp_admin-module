package com.adminremit.masters.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.models.PurposeListMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurposeListMasterRepository extends JpaRepository<PurposeListMaster, Long> {
    public List<PurposeListMaster> findAllByPublish(boolean publish);
    public List<PurposeListMaster> findAll();
    public List<PurposeListMaster> findAllByIsDeleted(Boolean isDeleted);
	public List<PurposeListMaster> findAllByIsDeletedAndPublishTrue(boolean b);
}
