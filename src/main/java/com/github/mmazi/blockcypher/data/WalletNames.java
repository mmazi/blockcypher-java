package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class WalletNames {
    private List<String> walletNames;

    public List<String> getWalletNames() {
        return walletNames;
    }
}
