package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import si.mazi.rescu.HttpStatusExceptionSupport;

import javax.annotation.Nullable;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BlockCypherException extends HttpStatusExceptionSupport {

    public static final Function<Error, String> ERROR_MSG = new Function<Error, String>() {
        @Nullable @Override public String apply(Error input) { return input.getError(); }
    };

    public BlockCypherException(@JsonProperty("error") String error, @JsonProperty("errors") List<Error> errors) {
        super(errors == null ? error : Joiner.on("  ").join(Iterables.transform(errors, ERROR_MSG)));
    }

    public static class Error {
        private String error;
        public String getError() { return error; }
        @Override public String toString() { return error; }
    }
}
