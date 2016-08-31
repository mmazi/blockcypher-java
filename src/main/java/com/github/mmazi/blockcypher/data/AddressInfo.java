package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigInteger;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AddressInfo {
    private String address;
    private BigInteger totalReceived;
    private BigInteger totalSent;
    private BigInteger balance;
    private BigInteger unconfirmedBalance;
    private BigInteger finalBalance;
    private Integer nTx;
    private Integer unconfirmedNTx;
    private Integer finalNTx;
    private List<Transaction> txs;
    private List<TxRef> txrefs;

    public String getAddress() {
        return address;
    }

    public BigInteger getTotalReceived() {
        return totalReceived;
    }

    public BigInteger getTotalSent() {
        return totalSent;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public BigInteger getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public BigInteger getFinalBalance() {
        return finalBalance;
    }

    public Integer getnTx() {
        return nTx;
    }

    public Integer getUnconfirmedNTx() {
        return unconfirmedNTx;
    }

    public Integer getFinalNTx() {
        return finalNTx;
    }

    public List<Transaction> getTxs() {
        return txs;
    }

    public List<TxRef> getTxrefs() {
        return txrefs;
    }

    @Override
    public String toString() {
        return String.format(
                "AddressBalance{address='%s', balance=%d, unconfirmedBalance=%d, nTx=%d, totalReceived=%d, totalSent=%d, unconfirmedNTx=%d}", address,
                balance, unconfirmedBalance, nTx, totalReceived, totalSent, unconfirmedNTx);
    }
}
