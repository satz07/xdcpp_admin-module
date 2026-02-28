package com.adminremit.aml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.aml.model.AMLVerification;

@Repository
public interface AMLRepository extends JpaRepository<AMLVerification,Long> {

}
