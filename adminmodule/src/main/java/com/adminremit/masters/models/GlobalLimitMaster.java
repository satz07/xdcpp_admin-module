package com.adminremit.masters.models;

import org.hibernate.envers.Audited;

import com.adminremit.auth.models.Partner;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "global_limit_master")
@Audited
public class GlobalLimitMaster extends FeeSettingBaseModel {

    @ManyToOne
    @JoinColumn(name = "payment_code")
    private PaymentReceiveMode paymentCode;

    @ManyToOne
    @JoinColumn(name = "receive_code")
    private PaymentReceiveMode receiveCode;

    @Column(name = "min_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal minTxnLimit;

    @Column(name = "max_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal maxTxnLimit;

    @Column(name = "daily_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal dailyLimit;

    @Column(name = "daily_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal dailyTxnLimit;

    @Column(name = "weekly_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal weeklyLimit;

    @Column(name = "weekly_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal weeklyTxnLimit;

    @Column(name = "monthly_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal monthlyLimit;

    @Column(name = "monthly_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal monthlyTxnLimit;

    @Column(name = "quarterly_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal quarterlyLimit;

    @Column(name = "quarterly_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal quarterlyTxnLimit;

    @Column(name = "half_yearly_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal halfYearlyLimit;

    @Column(name = "half_yearly_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal halfYearlyTxnLimit;

    @Column(name = "annual_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal annualLimit;

    @Column(name = "annual_txn_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal annualTxnLimit;
    
    @Column(name = "avg_trans_limit",columnDefinition="Decimal(20,5) default '0.00'")
    private BigDecimal avgTransLimit;

    @Column(name = "is_risk_limit_applicable")
    private boolean isRiskLimitApplicable;

    @ManyToOne
    @JoinColumn(name = "partner_name")
    private Partner partnerName;

    public Partner getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(Partner partnerName) {
		this.partnerName = partnerName;
	}

	public PaymentReceiveMode getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(PaymentReceiveMode paymentCode) {
        this.paymentCode = paymentCode;
    }

    public PaymentReceiveMode getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(PaymentReceiveMode receiveCode) {
        this.receiveCode = receiveCode;
    }

    public BigDecimal getMinTxnLimit() {
        return minTxnLimit;
    }

    public void setMinTxnLimit(BigDecimal minTxnLimit) {
        this.minTxnLimit = minTxnLimit;
    }

    public BigDecimal getMaxTxnLimit() {
        return maxTxnLimit;
    }

    public void setMaxTxnLimit(BigDecimal maxTxnLimit) {
        this.maxTxnLimit = maxTxnLimit;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getDailyTxnLimit() {
        return dailyTxnLimit;
    }

    public void setDailyTxnLimit(BigDecimal dailyTxnLimit) {
        this.dailyTxnLimit = dailyTxnLimit;
    }

    public BigDecimal getWeeklyLimit() {
        return weeklyLimit;
    }

    public void setWeeklyLimit(BigDecimal weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public BigDecimal getWeeklyTxnLimit() {
        return weeklyTxnLimit;
    }

    public void setWeeklyTxnLimit(BigDecimal weeklyTxnLimit) {
        this.weeklyTxnLimit = weeklyTxnLimit;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public BigDecimal getMonthlyTxnLimit() {
        return monthlyTxnLimit;
    }

    public void setMonthlyTxnLimit(BigDecimal monthlyTxnLimit) {
        this.monthlyTxnLimit = monthlyTxnLimit;
    }

    public BigDecimal getQuarterlyLimit() {
        return quarterlyLimit;
    }

    public void setQuarterlyLimit(BigDecimal quarterlyLimit) {
        this.quarterlyLimit = quarterlyLimit;
    }

    public BigDecimal getQuarterlyTxnLimit() {
        return quarterlyTxnLimit;
    }

    public void setQuarterlyTxnLimit(BigDecimal quarterlyTxnLimit) {
        this.quarterlyTxnLimit = quarterlyTxnLimit;
    }

    public BigDecimal getHalfYearlyLimit() {
        return halfYearlyLimit;
    }

    public void setHalfYearlyLimit(BigDecimal halfYearlyLimit) {
        this.halfYearlyLimit = halfYearlyLimit;
    }

    public BigDecimal getHalfYearlyTxnLimit() {
        return halfYearlyTxnLimit;
    }

    public void setHalfYearlyTxnLimit(BigDecimal halfYearlyTxnLimit) {
        this.halfYearlyTxnLimit = halfYearlyTxnLimit;
    }

    public BigDecimal getAnnualLimit() {
        return annualLimit;
    }

    public void setAnnualLimit(BigDecimal annualLimit) {
        this.annualLimit = annualLimit;
    }

    public BigDecimal getAnnualTxnLimit() {
        return annualTxnLimit;
    }

    public void setAnnualTxnLimit(BigDecimal annualTxnLimit) {
        this.annualTxnLimit = annualTxnLimit;
    }
    
    public BigDecimal getAvgTransLimit() {
		return avgTransLimit;
	}

	public void setAvgTransLimit(BigDecimal avgTransLimit) {
		this.avgTransLimit = avgTransLimit;
	}

	public boolean isRiskLimitApplicable() {
        return isRiskLimitApplicable;
    }

    public boolean getIsRiskLimitApplicable() {
        return isRiskLimitApplicable;
    }

    public void setIsRiskLimitApplicable(boolean riskLimitApplicable) {
        isRiskLimitApplicable = riskLimitApplicable;
    }
    
    public BigDecimal getAnnual_limit() {
        return annualLimit;
    }

    public void setAnnual_limit(BigDecimal annual_limit) {
        this.annualLimit = annual_limit;
    }

    public BigDecimal getAnnual_txn_limit() {
        return annualTxnLimit;
    }

    public void setAnnual_txn_limit(BigDecimal annual_txn_limit) {
        this.annualTxnLimit = annual_txn_limit;
    }

    public BigDecimal getDaily_limit() {
        return dailyLimit;
    }

    public void setDaily_limit(BigDecimal daily_limit) {
        this.dailyLimit = daily_limit;
    }

    public BigDecimal getDaily_txn_limit() {
        return dailyTxnLimit;
    }

    public void setDaily_txn_limit(BigDecimal daily_txn_limit) {
        this.dailyTxnLimit = daily_txn_limit;
    }

    public BigDecimal getHalf_yearly_limit() {
        return halfYearlyLimit;
    }

    public void setHalf_yearly_limit(BigDecimal half_yearly_limit) {
        this.halfYearlyLimit = half_yearly_limit;
    }

    public BigDecimal getHalf_yearly_txn_limit() {
        return halfYearlyTxnLimit;
    }

    public void setHalf_yearly_txn_limit(BigDecimal half_yearly_txn_limit) {
        this.halfYearlyTxnLimit = half_yearly_txn_limit;
    }

    public BigDecimal getMax_limit() {
        return maxTxnLimit;
    }

    public void setMax_limit(BigDecimal max_limit) {
        this.maxTxnLimit = max_limit;
    }

    public BigDecimal getMin_limit() {
        return minTxnLimit;
    }

    public void setMin_limit(BigDecimal min_limit) {
        this.minTxnLimit = min_limit;
    }

    public BigDecimal getMonthly_limit() {
        return monthlyLimit;
    }

    public void setMonthly_limit(BigDecimal monthly_limit) {
        this.monthlyLimit = monthly_limit;
    }

    public BigDecimal getMonthly_txn_limit() {
        return monthlyTxnLimit;
    }

    public void setMonthly_txn_limit(BigDecimal monthly_txn_limit) {
        this.monthlyTxnLimit = monthly_txn_limit;
    }

    public BigDecimal getQuarterly_limit() {
        return quarterlyLimit;
    }

    public void setQuarterly_limit(BigDecimal quarterly_limit) {
        this.quarterlyLimit = quarterly_limit;
    }

    public BigDecimal getQuarterly_txn_limit() {
        return quarterlyTxnLimit;
    }

    public void setQuarterly_txn_limit(BigDecimal quarterly_txn_limit) {
        this.quarterlyTxnLimit = quarterly_txn_limit;
    }

    public BigDecimal getWeekly_limit() {
        return weeklyLimit;
    }

    public void setWeekly_limit(BigDecimal weekly_limit) {
        this.weeklyLimit = weekly_limit;
    }

    public BigDecimal getWeekly_txn_limit() {
        return weeklyTxnLimit;
    }

    public void setWeekly_txn_limit(BigDecimal weekly_txn_limit) {
        this.weeklyTxnLimit = weekly_txn_limit;
    }
}
