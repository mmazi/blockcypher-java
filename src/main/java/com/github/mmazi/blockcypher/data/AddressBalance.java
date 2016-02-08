package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AddressBalance {
    private String address;
    private Long totalReceived;
    private Long totalSent;
    private Long balance;
    private Long unconfirmedBalance;
    private Long finalBalance;
    private Integer nTx;
    private Integer unconfirmedNTx;
    private Integer finalNTx;

    public String getAddress() {
        return address;
    }

    public Long getTotalReceived() {
        return totalReceived;
    }

    public Long getTotalSent() {
        return totalSent;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public Long getFinalBalance() {
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

    @Override
    public String toString() {
        return String.format(
                "AddressBalance{address='%s', balance=%d, unconfirmedBalance=%d, nTx=%d, totalReceived=%d, totalSent=%d, unconfirmedNTx=%d}", address,
                balance, unconfirmedBalance, nTx, totalReceived, totalSent, unconfirmedNTx);
    }
}
