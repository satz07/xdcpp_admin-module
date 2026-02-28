package com.adminremit.user.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.adminremit.operations.model.FileInfo;

@Entity
@Table(name = "edd_master")
public class EDDMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "profession")
    private String profession;

    @Column(name = "profession_type", nullable = true)
    private String professionType;

    @Column(name = "other_profession", nullable = true)
    private String otherProfession;

    @Column(name = "annual_income", nullable = true)
    private String annualIncome;

    @Column(name = "source_income", nullable = true)
    private String sourceIncome;

    @Column(name = "other_income", nullable = true)
    private String otherIncome;

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval= true )
    @Column(name = "file_info")
    List<FileInfo> fileInfo;

    @Column(name = "user_calc_mapping")
    private String userCalculatorMapping;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfessionType() { return professionType; }

    public void setProfessionType(String professionType) { this.professionType = professionType; }

    public String getOtherProfession() { return otherProfession; }

    public void setOtherProfession(String otherProfession) { this.otherProfession = otherProfession; }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getSourceIncome() { return sourceIncome; }

    public void setSourceIncome(String sourceIncome) { this.sourceIncome = sourceIncome; }

    public String getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(String otherIncome) {
        this.otherIncome = otherIncome;
    }

    public List<FileInfo> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<FileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getUserCalculatorMapping() { return userCalculatorMapping; }

    public void setUserCalculatorMapping(String userCalculatorMapping) { this.userCalculatorMapping = userCalculatorMapping; }
}
