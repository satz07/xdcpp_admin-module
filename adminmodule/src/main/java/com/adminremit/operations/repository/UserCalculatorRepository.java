package com.adminremit.operations.repository;

import com.adminremit.operations.model.RemittUser;
import com.adminremit.operations.model.UserCalculatorMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalculatorRepository extends JpaRepository<UserCalculatorMapping, Long> {

    UserCalculatorMapping findByUid(String uid);

    UserCalculatorMapping findByUser(RemittUser user);

    Optional<UserCalculatorMapping> findById(Long id);

    @Modifying
    @Query("update UserCalculatorMapping u set u.status = :status, u.statusReason = :statusReason  where u.id = :id")
    void cancel(@Param(value = "id") long id, @Param(value = "status") String status, @Param(value = "statusReason") String statusReason);

    @Transactional
    @Modifying
    @Query("update UserCalculatorMapping u set u.status = :status  where u.id = :id")
    void cancel(@Param(value = "id") long id, @Param(value = "status") String status);

    
    @Query("SELECT count(user) FROM UserCalculatorMapping WHERE user = ?1")
    public int getUserTransactionCount(RemittUser user);

    List<UserCalculatorMapping> findAllByStatus(String status);

    List<UserCalculatorMapping> findAllByRefId(String refId);


    @Query(value = "SELECT u FROM UserCalculatorMapping u WHERE u.refId IN :refId")
    List<UserCalculatorMapping> findUserCalculatorMappingByNameRefid(@Param("refId") Collection<String> refId);


}
