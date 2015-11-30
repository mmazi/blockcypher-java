package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Confidence {
    /**
     * The age of the transaction in milliseconds, based on the earliest time BlockCypher saw it relayed in the network.
     */
    Integer ageMillis;

    /**
     * Number of peers that have sent this transaction to BlockCypher; only positive for unconfirmed transactions. -1 for confirmed transactions.
     */
    Integer receiveCount;

    /**
     * A number from 0 to 1 representing BlockCypher’s confidence that the transaction will make it into the next block.
     */
    double confidence;

    /**
     * The hash of the transaction. While reasonably unique, using hashes as identifiers may be unsafe.
     */
    String txhash;

    /**
     * The BlockCypher URL one can use to query more detailed information about this transaction.
     */
    String txurl;

    public Integer getAgeMillis() {
        return ageMillis;
    }

    public Integer getReceiveCount() {
        return receiveCount;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getTxhash() {
        return txhash;
    }

    public String getTxurl() {
        return txurl;
    }

    @Override
    public String toString() {
        return String.format("Confidence{txhash='%s', confidence=%s, receiveCount=%d, ageMillis=%d, txurl='%s'}",
                txhash, confidence, receiveCount, ageMillis, txurl);
    }
}
