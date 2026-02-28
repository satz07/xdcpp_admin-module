package com.adminremit.masters.repository;

import com.adminremit.masters.enums.MarkerCheckerStatus;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.models.MakerAndChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakerAndCheckerRepository extends JpaRepository<MakerAndChecker,Long> {
    public List<MakerAndChecker> findAllByPublish(Boolean publish);
    public List<MakerAndChecker> findAllByMarkerCheckerStatus(MarkerCheckerStatus markerCheckerStatus);
    public List<MakerAndChecker> findAll();
}
