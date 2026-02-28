package com.adminremit.operations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adminremit.operations.model.CashPickupGlobal;



@Repository
public interface CashpickupGlobalRepository extends JpaRepository<CashPickupGlobal, Long> {

	CashPickupGlobal findAllById(Long cashpickup);
	List<CashPickupGlobal> findByCountryCodeAndDeliveryPartnerCode(String countryCode,String delipveryPartnerCode);
	

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("update IFSCGlobal u set u.ifscCode = :ifsc, u.bankName = :bankname,u.branchName=:brancname,u.city=:city,u.countryCode=:countrycode,u.instaAvailable=:instaavailable,u.instaCode=:instacode,u.createdon=:updatedon  where u.id = :id"
	 * ) void update(@Param(value = "id") long id, @Param(value = "ifsc") String
	 * ifsc,
	 * 
	 * @Param(value = "bankname") String bankname, @Param(value = "brancname")
	 * String brancname,
	 * 
	 * @Param(value = "city") String city,@Param(value = "countrycode") String
	 * courntycode,@Param(value = "instaavailable") boolean
	 * instaavailable,@Param(value = "instacode") String instacode, @Param(value =
	 * "updatedon") Date updatedon);
	 */

}
