package com.adminremit.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.user.model.IPTagging;

@Repository
public interface UserIPRepository extends JpaRepository<IPTagging, Long> {

    IPTagging findByIpAddress(String ip);
    IPTagging findByUid(String uid);

}
