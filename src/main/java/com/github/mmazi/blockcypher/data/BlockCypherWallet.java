package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BlockCypherWallet {

    private String name;
    private Boolean hd = true;
    private String token;
    private String extendedPublicKey;
    private List<Integer> subchainIndexes = Arrays.asList(0, 1);
    private List<SubChain> chains = Arrays.asList(new SubChain(0), new SubChain(1));
    private List<String> addresses;

    protected BlockCypherWallet() { }

    public BlockCypherWallet(String name, String extendedPublicKey) {
        this.name = name;
        this.extendedPublicKey = extendedPublicKey;
    }

    public String getName() {
        return name;
    }

    public String getExtendedPublicKey() {
        return extendedPublicKey;
    }

    public List<SubChain> getChains() {
        return chains;
    }

    public List<Integer> getSubchainIndexes() {
        return subchainIndexes;
    }

    public boolean isHd() {
        return hd;
    }

    public String getToken() {
        return token;
    }

    @JsonIgnore(true)
    public Collection<String> getAddressesFromChains() {
        return Sets.newHashSet(Iterables.concat(Iterables.transform(chains, SubChain.ADDRS)));
    }

    public List<String> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return String.format("BlockCypherWallet{name='%s', hd=%s, extendedPublicKey='%s', chains=%s}",
                name, hd, extendedPublicKey, chains);
    }

    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
    public static class SubChain {
        public static final Function<SubChain, Iterable<String>> ADDRS = new Function<SubChain, Iterable<String>>() {
            @Nullable @Override public Iterable<String> apply(SubChain input) {
                return Iterables.transform(input.chainAddresses, Address.ADDR);
            }
        };

        private Integer index;
        private List<Address> chainAddresses;

        protected SubChain() { }

        public SubChain(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }

        public List<Address> getChainAddresses() {
            return chainAddresses;
        }

        @Override
        public String toString() {
            return String.format("SubChain{index=%d, addresses=%s}", index, chainAddresses);
        }

        @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
        public static class Address {
            public static final Function<Address, String> ADDR = new Function<Address, String>() {
                @Nullable @Override public String apply(@Nullable Address input) { return input.address; }
            };

            private String address;
            private String Public;
            private String path;

            public String getAddress() {
                return address;
            }

            public String getPublic() {
                return Public;
            }

            public String getPath() {
                return path;
            }

            @Override
            public String toString() {
                return String.format("Address{%s, path='%s'}", address, path);
            }
        }
    }
}
