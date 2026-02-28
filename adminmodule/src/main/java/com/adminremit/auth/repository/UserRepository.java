package com.adminremit.auth.repository;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.auth.models.Partner;
import com.adminremit.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public List<User> findAllByPublish(Boolean publish);
    public User findByResetToken(String resetToken) throws RecordNotFoundException;
    public User findByEmailAndPhoneNumber(String email, String phoneNumber) throws RecordNotFoundException;
    public List<User> findAll();
    public List<User> findAllByIsDeleted(Boolean isDeleted);
    public List<User> findAllByIsDeletedAndPartner(Boolean isDeleted, Partner partner);
    public User findByEmailAndIsDeleted(String email, Boolean isDeleted);
}
