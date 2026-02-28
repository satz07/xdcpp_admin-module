package com.adminremit.austrac.report.austracRepository;

import com.adminremit.austrac.report.Maintaining_Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface AstracReportRepository extends JpaRepository<Maintaining_Sequence, Long> {

    @Query(value = "SELECT MAX(current_value) FROM maintaining_sequence WHERE create_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    String checkUpdatedSeqNumber(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
