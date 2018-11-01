package org.sunyata.game.currency.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by leo on 16/7/20.
 */
public class FundBalance implements Serializable {

    private Integer id;
    private String account;
    private String accountName;
    private Integer fundAccountType;

//    private BigDecimal availableBalance;
    private BigDecimal actualBalance;

    private Timestamp updateDateTime;

    private Integer funChangeId;

    public Integer getId() {
        return id;
    }

    public FundBalance setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public FundBalance setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getFundAccountType() {
        return fundAccountType;
    }

    public FundBalance setFundAccountType(Integer fundAccountType) {
        this.fundAccountType = fundAccountType;
        return this;
    }

//    public BigDecimal getAvailableBalance() {
//        return availableBalance;
//    }
//
//    public FundBalance setAvailableBalance(BigDecimal availableBalance) {
//        this.availableBalance = availableBalance;
//        return this;
//    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public FundBalance setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
        return this;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public FundBalance setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public Integer getFunChangeId() {
        return funChangeId;
    }

    public FundBalance setFunChangeId(Integer funChangeId) {
        this.funChangeId = funChangeId;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public FundBalance setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }
}
