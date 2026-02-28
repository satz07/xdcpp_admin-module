package com.adminremit.masters.repository;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.models.StatusMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long> {
    public List<StatusMaster> findAllByPublish(boolean publish);
    public List<StatusMaster> findAll();
    public List<StatusMaster> findAllByIsDeleted(Boolean isDeleted);
	public List<StatusMaster> findAllByIsDeletedAndPublishTrue(boolean b);
}
