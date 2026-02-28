package com.adminremit.auth.repository;

import com.adminremit.auth.models.FunctionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FunctionMasterRepository extends JpaRepository<FunctionMaster, Long> {
    public List<FunctionMaster> findAllByIsDeletedOrderByIdAsc(Boolean publish);
    public Optional<FunctionMaster> findById(Long  functionId);
}
