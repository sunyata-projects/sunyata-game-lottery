package org.sunyata.game.currency.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by leo on 16/7/20.
 */

public class FundDetail  implements Serializable {
    /**
     * id
     */
    private Integer id;

    private String orderId;

    private String account;

    private Integer fundAccountType;

    private Integer fundDetailType;

    private BigDecimal amt;

    private String notes;

    private Timestamp createDateTime;

    public Integer getId() {
        return id;
    }

    public FundDetail setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public FundDetail setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public FundDetail setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getFundAccountType() {
        return fundAccountType;
    }

    public FundDetail setFundAccountType(Integer fundAccountType) {
        this.fundAccountType = fundAccountType;
        return this;
    }

    public Integer getFundDetailType() {
        return fundDetailType;
    }

    public FundDetail setFundDetailType(Integer fundDetailType) {
        this.fundDetailType = fundDetailType;
        return this;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public FundDetail setAmt(BigDecimal amt) {
        this.amt = amt;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public FundDetail setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public FundDetail setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }
}
