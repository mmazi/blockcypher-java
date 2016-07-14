package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Representation of a Transaction, ie:
 * {
 * "block_hash": "00000000cfa6c00b7ee5550644c09f5175eff4665dc24018c50417f83348886c",
 * "block_height": 271609,
 * "hash": "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35",
 * "addresses": [
 * "mqz1CxAGWahHuaTnjHFnitfv8VguUwe7dN",
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz",
 * "mz8KgrQiSqZ3UmaXsx23hQZMVDet5wYBFF",
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "total": 499950000,
 * "fees": 0,
 * "relayed_by": "",
 * "confirmed": "2014-08-03T15:52:11Z",
 * "received": "2014-08-03T15:51:46.512Z",
 * "ver": 1,
 * "lock_time": 0,
 * "vin_sz": 1,
 * "vout_sz": 3,
 * "confirmations": 1392,
 * "inputs": [
 * {
 * "prev_hash": "c3fe841599794f88374b0aaf0cbd5b3897d75c4dc897a846e6040054d5495d66",
 * "output_index": 0,
 * "script": "483045022100ddb75ef19a31eb5e25595cb35c6b5f058912cc168a32a215c680a5532900904202200efb197876164fa246ff5009a04f39ff51db70adb90ee342f0aa97ec19d776eb012103f78041c92a4aea6e44ac937c8bd7e504e14768a40879dc7655e533a749fea55b",
 * "output_value": 499950000,
 * "addresses": [
 * "mqz1CxAGWahHuaTnjHFnitfv8VguUwe7dN"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ],
 * "outputs": [
 * {
 * "value": 100000000,
 * "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
 * "spent_by": "",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": 100000000,
 * "script": "76a914f343f510e12156df80fee18ea1a319002f55747788ac",
 * "spent_by": "2d43c093db79ecb03dc44552a05dfe468e504e7f8077841401f055c5ae30b69d",
 * "addresses": [
 * "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * },
 * {
 * "value": 299950000,
 * "script": "76a914cc22ae49e38122e0edcd97c5197cf12b39be01af88ac",
 * "spent_by": "a26abe3174100bb95bc771e7e3368435c7e8e4afcfa0e91c6f2a37cedc43d7bc",
 * "addresses": [
 * "mz8KgrQiSqZ3UmaXsx23hQZMVDet5wYBFF"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * ]
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    private String hash;
    private String blockHash;
    private Integer blockHeight;

    private String[] addresses;

    private Long total;
    private Long fees;
    private String relayedBy;
    private Date confirmed;
    private Date received;
    private Integer ver;
    private Long lockTime;
    private Integer vinSz;
    private Integer voutSz;
    private Integer confirmations;

    private Integer receiveCount;
    private Double confidence;
    // low, medium, high,...
    private String preference;
    private boolean doubleSpend;
    private String doubleSpendTx;

    private Input[] inputs;
    private Output[] outputs;

    protected Transaction() { }

    public static Transaction newTransaction(List<String> inputAddresses, String outputAddress, long outputValue, Preference preference) {
        Transaction tx = new Transaction();
        tx.inputs = new Input[inputAddresses.size()];
        tx.outputs = new Output[] { new Output(outputValue, new String[]{outputAddress}) };
        for (int i = 0; i < inputAddresses.size(); i++) {
            tx.inputs[i] = new Input(new String[]{inputAddresses.get(i)});
        }
        tx.preference = preference.name();
        return tx;
    }

    public String getHash() {
        return hash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public Long getTotal() {
        return total;
    }

    public Long getFees() {
        return fees;
    }

    public String getRelayedBy() {
        return relayedBy;
    }

    public Date getConfirmed() {
        return confirmed;
    }

    @JsonIgnore
    public Date getReceived() {
        return received;
    }

    @JsonIgnore(false)
    protected void setReceived(Date received) {
        this.received = received;
    }

    public Integer getVer() {
        return ver;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public Integer getVinSz() {
        return vinSz;
    }

    public Integer getVoutSz() {
        return voutSz;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public Integer getReceiveCount() {
        return receiveCount;
    }

    public Double getConfidence() {
        return confidence;
    }

    public String getPreference() {
        return preference;
    }

    public boolean isDoubleSpend() {
        return doubleSpend;
    }

    public String getDoubleSpendTx() {
        return doubleSpendTx;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public Output[] getOutputs() {
        return outputs;
    }

    public enum Preference { high, medium, low }

    @Override
    public String toString() {
        return String.format("Transaction{hash='%s', blockHash='%s', blockHeight=%d, addresses=%s, total=%d, fees=%d, relayedBy='%s', confirmed=%s, received=%s, ver=%d, lockTime=%d, vinSz=%d, voutSz=%d, confirmations=%d, receiveCount=%d, confidence=%s, preference='%s', doubleSpend=%s, doubleSpendTx='%s', inputs=%s, outputs=%s}",
                hash, blockHash, blockHeight, Arrays.toString(addresses), total, fees, relayedBy, confirmed, received, ver, lockTime, vinSz, voutSz, confirmations, receiveCount, confidence, preference, doubleSpend, doubleSpendTx, Arrays.toString(inputs), Arrays.toString(outputs));
    }
}
