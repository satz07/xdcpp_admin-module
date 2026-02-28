package com.adminremit.receiver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminremit.masters.models.PurposeListMaster;

public interface ReceiverPurposeRepo extends JpaRepository<PurposeListMaster, Long> {

    Optional<PurposeListMaster> findById(Long id);
}
