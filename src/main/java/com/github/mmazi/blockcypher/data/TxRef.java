package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigInteger;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxRef {

    private String txHash; // "471b4a98b5cfe91a95cd492465e1ba1570055510d7cbe1a85845cee8c91efa0a",
    private Integer blockHeight; // 427653,
    private Integer txInputN; // -1,
    private Integer txOutputN; // 0,
    private BigInteger value; // 1101019180,
    private BigInteger refBalance; // 83891846588,
    private Boolean spent; // false,
    private Integer confirmations; // 1,
    private Date confirmed; // "2016-08-31T10:59:03Z",
    private Boolean doubleSpend; // false

    public String getTxHash() {
        return txHash;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public Integer getTxInputN() {
        return txInputN;
    }

    public Integer getTxOutputN() {
        return txOutputN;
    }

    public BigInteger getValue() {
        return value;
    }

    public BigInteger getRefBalance() {
        return refBalance;
    }

    public Boolean getSpent() {
        return spent;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public Date getConfirmed() {
        return confirmed;
    }

    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    @Override
    public String toString() {
        return String.format(
                "TxRef{txHash='%s', blockHeight=%d, txInputN=%d, txOutputN=%d, value=%d, refBalance=%d, spent=%s, confirmations=%d, confirmed=%s, doubleSpend=%s}",
                txHash, blockHeight, txInputN, txOutputN, value, refBalance, spent, confirmations, confirmed, doubleSpend);
    }
}
