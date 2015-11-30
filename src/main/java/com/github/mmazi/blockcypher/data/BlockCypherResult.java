package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.ExceptionalReturnContentException;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BlockCypherResult {
    public BlockCypherResult() {
    }

    public BlockCypherResult(@JsonProperty("error") String error) {
        if (error != null) {
            throw new ExceptionalReturnContentException(error);
        }
    }
}
