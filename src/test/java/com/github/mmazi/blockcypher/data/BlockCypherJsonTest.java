package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockCypherJsonTest {

    @Test
    public void shouldDeserializeWallet() throws Exception {
        BlockCypherWallet wallet = parse(BlockCypherWallet.class);

        assertThat(wallet.getExtendedPublicKey()).isEqualTo("xpub661MyMwAqRbcGNcUihNhkk4xV9YYWy5DUs1qi9UFKs2biyKXJ8NBAzyGdTxUMDiKAQiQjg6DejwGu7tDY9Q6PZdhWCLNXat5ZsFuxcYSNJd");
        assertThat(wallet.getName()).isEqualTo("MM_Electrum_TEST");
        assertThat(wallet.getToken()).isEqualTo("ffffffffffffffffffffffffffffff");
        assertThat(wallet.isHd()).isTrue();
        assertThat(wallet.getAddressesFromChains()).hasSize(18);
        assertThat(wallet.getAddresses()).isNull();
        assertThat(wallet.getChains()).hasSize(2);
        assertThat(wallet.getChains().get(1).getChainAddresses()).hasSize(15);
        assertThat(wallet.getChains().get(1).getChainAddresses().get(0).getAddress()).isEqualTo("13uMLSzTg8nJSKFMabaqBS5MKJ8saByA35");
        assertThat(wallet.getChains().get(1).getChainAddresses().get(0).getPath()).isEqualTo("m/1/0");
        assertThat(wallet.getChains().get(1).getChainAddresses().get(0).getPublic()).isNullOrEmpty();
    }

    @Test
    public void shouldDeserializeTxSkeleton() throws Exception {
        TxSkeleton txSkeleton = parse(TxSkeleton.class);
        assertThat(txSkeleton.getTosign()).hasSize(1);
        assertThat(txSkeleton.getTosign()[0].get()).hasSize(11);
        assertThat(txSkeleton.getTosign()[0].get()[0]).isEqualTo(Integer.decode("0x97").byteValue());
        assertThat(txSkeleton.getTosign()[0].get()[10]).isEqualTo(Integer.decode("0xf4").byteValue());
        assertThat(txSkeleton.getErrors().get(0).getError()).isEqualTo("unspecified error");
    }

    private static ObjectMapper createMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static <T> T parse(Class<T> valueType) throws IOException, URISyntaxException {
        return createMapper().readValue(BlockCypherJsonTest.class.getResource(valueType.getSimpleName() + ".json"), valueType);
    }
}