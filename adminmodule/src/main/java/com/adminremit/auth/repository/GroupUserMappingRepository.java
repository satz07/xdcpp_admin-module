package com.adminremit.auth.repository;

import com.adminremit.auth.models.GroupUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserMappingRepository extends JpaRepository<GroupUserMapping, Long> {
}
