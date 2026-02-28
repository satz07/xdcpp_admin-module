package com.adminremit.masters.repository;

import com.adminremit.masters.enums.MakerCheckerFunction;
import com.adminremit.masters.models.MakerAndCheckerSLAMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakerAndCheckerSLAMasterRepository extends JpaRepository<MakerAndCheckerSLAMaster,Long> {
    public List<MakerAndCheckerSLAMaster> findAllByPublish(Boolean publish);
    public List<MakerAndCheckerSLAMaster> findByMakerCheckerFunction(MakerCheckerFunction makerCheckerFunction);
    public List<MakerAndCheckerSLAMaster> findByMakerCheckerField(String makerCheckerField);
    public List<MakerAndCheckerSLAMaster> findAll();
}
