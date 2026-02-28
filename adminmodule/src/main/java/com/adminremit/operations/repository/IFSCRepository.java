package com.adminremit.operations.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.IFSCNew;

@Repository
public interface IFSCRepository extends JpaRepository<IFSCNew, Long> {

	IFSCNew findAllByifscCode(String ifsc);

	@Transactional
	@Modifying
	@Query("update IFSCNew u set u.ifscCode = :ifsc, u.bankName = :bankname,u.branchName=:brancname,u.city=:city,u.createdon=:updatedon  where u.id = :id")
	void update(@Param(value = "id") long id, @Param(value = "ifsc") String ifsc,
			@Param(value = "bankname") String bankname, @Param(value = "brancname") String brancname,
			@Param(value = "city") String city, @Param(value = "updatedon") Date updatedon);

}
