package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Arrays;

/**
 * Input of a transation, ie:
 * <p/>
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
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Input {

    private String prevHash;
    private Long outputIndex;
    private String script;
    private Long outputValue;
    private String[] addresses;
    private String scriptType;

    protected Input() { }

    public String getPrevHash() {
        return prevHash;
    }

    public Long getOutputIndex() {
        return outputIndex;
    }

    public String getScript() {
        return script;
    }

    public Long getOutputValue() {
        return outputValue;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public String getScriptType() {
        return scriptType;
    }

    @Override
    public String toString() {
        return String.format("Input{prevHash='%s', outputIndex=%d, outputValue=%d, addresses=%s, scriptType='%s'}",
                prevHash, outputIndex, outputValue, Arrays.toString(addresses), scriptType);
    }
}
