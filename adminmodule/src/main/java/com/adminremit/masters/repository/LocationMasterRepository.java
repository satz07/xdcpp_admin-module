package com.adminremit.masters.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.models.LocationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationMasterRepository extends JpaRepository<LocationMaster, Long> {
    public List<LocationMaster> findAllByPublish(Boolean publish);
    public List<LocationMaster> findAll();
    public List<LocationMaster> findAllByIsDeletedOrderByLocationNameAsc(Boolean isDeleted);
    //@Query("SELECT t FROM LocationMaster t WHERE t.locationName = ?1")
    public LocationMaster findFirstByLocationName(String locationName);
}
