package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Arrays;

/**
 * Output of a transaction, ie:
 * {
 * "value": 100000000,
 * "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
 * "spent_by": "",
 * "addresses": [
 * "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
 * ],
 * "script_type": "pay-to-pubkey-hash"
 * }
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Output {

    private Long value;
    private String script;
    private String spentBy;
    private String[] addresses;
    private String scriptType;

    protected Output() { }

    Output(Long value, String[] addresses) {
        this.value = value;
        this.addresses = addresses;
    }

    public Long getValue() {
        return value;
    }

    public String getScript() {
        return script;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public String getScriptType() {
        return scriptType;
    }

    @Override
    public String toString() {
        return String.format("Output{value=%d, spentBy='%s', addresses=%s, scriptType='%s'}",
                value, spentBy, Arrays.toString(addresses), scriptType);
    }
}
