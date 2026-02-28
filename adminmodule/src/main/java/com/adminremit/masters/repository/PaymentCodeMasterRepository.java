package com.adminremit.masters.repository;

import com.adminremit.auth.models.OwnerDesignation;
import com.adminremit.masters.enums.PaymentModeType;
import com.adminremit.masters.models.PaymentCodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentCodeMasterRepository  extends JpaRepository<PaymentCodeMaster,Long> {
    public List<PaymentCodeMaster> findAllByPublish(boolean publish);
    public List<PaymentCodeMaster> findAllByPaymentModeType(PaymentModeType paymentModeType);
    public List<PaymentCodeMaster> findAllByPublishAndPaymentModeType(boolean publish,PaymentModeType paymentModeType);
    public List<PaymentCodeMaster> findAll();
    public List<PaymentCodeMaster> findAllByIsDeleted(Boolean isDeleted);
}
