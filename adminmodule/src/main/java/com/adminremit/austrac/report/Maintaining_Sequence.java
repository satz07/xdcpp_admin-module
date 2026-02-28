package com.adminremit.austrac.report;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "maintaining_sequence")
@Getter
@Setter
public class Maintaining_Sequence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="seq_type")
    private String seqType;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt = new Date();

    @Column(name="current_value")
    private String currentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeqType() {
        return seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
