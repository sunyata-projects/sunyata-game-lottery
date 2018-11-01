package org.sunyata.game.currency.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by leo on 16/7/20.
 */

public class FundChange implements Serializable {


    private Integer id;

    private Integer detailId;

    private String account;

    private Integer fundAccountType;

    private BigDecimal availableAmtVariation;
    private BigDecimal actualAmtVariation;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;

    private Integer fundDetailType;

    private String notes;

    private Timestamp createDateTime;

    public Integer getId() {
        return id;
    }

    public FundChange setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public FundChange setDetailId(Integer detailId) {
        this.detailId = detailId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public FundChange setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getFundAccountType() {
        return fundAccountType;
    }

    public FundChange setFundAccountType(Integer fundAccountType) {
        this.fundAccountType = fundAccountType;
        return this;
    }

    public BigDecimal getAvailableAmtVariation() {
        return availableAmtVariation;
    }

    public FundChange setAvailableAmtVariation(BigDecimal availableAmtVariation) {
        this.availableAmtVariation = availableAmtVariation;
        return this;
    }

    public BigDecimal getActualAmtVariation() {
        return actualAmtVariation;
    }

    public FundChange setActualAmtVariation(BigDecimal actualAmtVariation) {
        this.actualAmtVariation = actualAmtVariation;
        return this;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public FundChange setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public FundChange setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public FundChange setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public FundChange setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public Integer getFundDetailType() {
        return fundDetailType;
    }

    public FundChange setFundDetailType(Integer fundDetailType) {
        this.fundDetailType = fundDetailType;
        return this;
    }
}
