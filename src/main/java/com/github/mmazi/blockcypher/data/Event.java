package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Event extends BlockCypherResult {

    public static final Pattern ADDR_FILTER = Pattern.compile("addr=([13][123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{25,33})");

    @JsonProperty private String id;
    @JsonProperty private String url;
    @JsonProperty private int errors;
    @JsonProperty private String filter;
    @JsonProperty private String address;
    @JsonProperty private String walletName;
    @JsonProperty private String script;
    @JsonProperty private String token;
    @JsonProperty private String event;
    @JsonProperty private Integer confirmations;

    protected Event() { }

    protected Event(@JsonProperty("error") String error) {
        super(error);
    }

    public Event(String token, String url, String address, String walletName) {
        this.token = token;
        this.address = address;
        this.url = url;
        this.event = "tx-confirmation";
        this.confirmations = 6;
        this.walletName = walletName;
    }

    public static Event createForAddress(String token, String address, String url) {
        return new Event(token, url, address, null);
    }

    public static Event createForWallet(String token, String walletName, String url) {
        return new Event(token, url, null, walletName);
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAddress() {
        return address != null ? address : filter == null ? null : extractAddress(filter);
    }

    public String getWalletName() {
        return walletName;
    }

    private String extractAddress(String filter) {
        Matcher matcher = ADDR_FILTER.matcher(filter);
        return matcher.find() ? matcher.group(1) : null;
    }

    @Override
    public String toString() {
        return String.format("Event{id='%s', url='%s', errors=%d, event='%s', filter='%s', address='%s', walletName='%s'}",
                id, url, errors, event, filter, address, walletName);
    }
}
