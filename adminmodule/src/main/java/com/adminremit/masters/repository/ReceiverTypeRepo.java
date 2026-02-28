package com.adminremit.masters.repository;

import com.adminremit.masters.models.ReceiverType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverTypeRepo extends JpaRepository<ReceiverType, Long> {

    ReceiverType findByReceiverType(String receiverType);
    ReceiverType findAllById(Long Id);




}
