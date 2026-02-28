package com.adminremit.masters.models;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "maker_checker_values")
@Audited
public class MakerAndCheckerValues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "from_value")
    private String fromValue;

    @Column(name = "to_value")
    private String toValue;

    @ManyToOne
    @JoinColumn(name = "maker_checker_id")
    private MakerAndChecker makerCheckerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public MakerAndChecker getMakerCheckerId() {
        return makerCheckerId;
    }

    public void setMakerCheckerId(MakerAndChecker makerCheckerId) {
        this.makerCheckerId = makerCheckerId;
    }
}
