package com.adminremit.masters.models;


import com.adminremit.operations.model.BaseEntity;

import javax.persistence.*;
import java.util.List;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "receiver_type")
@Audited
public class ReceiverType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "receiver_type")
    private String receiverType;

    @Column(name = "receiver_desc")
    private String receiverDesc;

    @OneToMany(cascade=CascadeType.ALL, mappedBy= "receiverTypes", orphanRemoval= true )
    @Column(name = "purpose_list_id")
    List<PurposeListMaster> purposeListMaster;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverDesc() {
        return receiverDesc;
    }

    public void setReceiverDesc(String receiverDesc) {
        this.receiverDesc = receiverDesc;
    }

    public List<PurposeListMaster> getPurposeListMaster() {
        return purposeListMaster;
    }

    public void setPurposeListMaster(List<PurposeListMaster> purposeListMaster) {
        this.purposeListMaster = purposeListMaster;
    }
}
