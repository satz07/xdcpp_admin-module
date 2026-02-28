package com.adminremit.auth.repository;

import com.adminremit.auth.enums.FeatureType;
import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.models.FunctionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeaturesMasterRepository extends JpaRepository<FeaturesMaster, Long> {
    public List<FeaturesMaster> findAllByIsDeletedOrderByIdAsc(Boolean publish);
    public List<FeaturesMaster> findAllByFeatureTypeAndPublish(FeatureType featureType, Boolean publish);
    public List<FeaturesMaster> findAllByFunctionMasterAndPublish(FunctionMaster functionMaster, Boolean publish);
}
