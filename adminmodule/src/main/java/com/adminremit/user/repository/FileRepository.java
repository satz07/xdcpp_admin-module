package com.adminremit.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.user.model.DdFileInfo;

@Repository
public interface FileRepository extends JpaRepository<DdFileInfo, String> {

    List<DdFileInfo> findAllByUserId(Long id);
    DdFileInfo findAllById(String id);

}
