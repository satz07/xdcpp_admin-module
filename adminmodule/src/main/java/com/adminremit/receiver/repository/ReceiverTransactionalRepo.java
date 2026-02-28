package com.adminremit.receiver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adminremit.receiver.model.ReceiverTransactionModel;


public  interface ReceiverTransactionalRepo extends JpaRepository<ReceiverTransactionModel, Long> {

    @Query("SELECT u FROM ReceiverTransactionModel u WHERE u.id = :id")
    Optional<ReceiverTransactionModel> findById(@Param(value = "id") Long id);
    
    @Query("SELECT u FROM ReceiverTransactionModel u WHERE u.transactionCalId = :id")
    Optional<ReceiverTransactionModel> findByUid(@Param(value = "id") Long id);
    
    @Query("SELECT u FROM ReceiverTransactionModel u WHERE u.transactionCalId = :id and u.userId = :userId")
    Optional<ReceiverTransactionModel> findByUidAndUserId(@Param(value = "id") Long id, @Param(value = "userId") Long userId);
}

