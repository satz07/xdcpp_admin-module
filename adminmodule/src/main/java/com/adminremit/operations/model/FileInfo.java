package com.adminremit.operations.model;

import com.adminremit.common.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
    @Table(name = "files_info")
    public class FileInfo extends BaseEntity {

        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        private String id;

        @Column(name="name")
        private String name;

        @Column(name="url")
        private String url;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "operationfile", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private OperationFileUpload operationFile;

        @OneToMany(mappedBy = "fileInfo", fetch = FetchType.LAZY)
        private List<TransferAccountDetails> transferAccountDetails;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
        return url;
    }

        public void setUrl(String url) {
        this.url = url;
    }


    @JsonIgnore
    public OperationFileUpload getOperationFile() {
        return operationFile;
    }

    @JsonIgnore
    public void setOperationFile(OperationFileUpload operationFile) {
        this.operationFile = operationFile;
    }

    public List<TransferAccountDetails> getTransferAccountDetails() {
        return transferAccountDetails;
    }

    public void setTransferAccountDetails(List<TransferAccountDetails> transferAccountDetails) {
        this.transferAccountDetails = transferAccountDetails;
    }


}