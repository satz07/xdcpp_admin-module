package com.adminremit.masters.repository;

import com.adminremit.masters.models.MakerAndCheckerValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakerAndCheckerValuesRepository extends JpaRepository<MakerAndCheckerValues, Long> {
    public List<MakerAndCheckerValues> findAll();
}
