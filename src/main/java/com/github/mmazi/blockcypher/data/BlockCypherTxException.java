package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlockCypherTxException extends BlockCypherException {
    public BlockCypherTxException(@JsonProperty("error") String error, @JsonProperty("errors") List<Error> errors) {
        super(error, errors);
    }

    private Transaction tx;

    public Transaction getTx() {
        return tx;
    }
}
