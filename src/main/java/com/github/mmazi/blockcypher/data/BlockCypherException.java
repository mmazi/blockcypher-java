package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.HttpStatusExceptionSupport;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BlockCypherException extends HttpStatusExceptionSupport {

    public BlockCypherException(@JsonProperty("error") String error) {
        super(error);
    }
}
