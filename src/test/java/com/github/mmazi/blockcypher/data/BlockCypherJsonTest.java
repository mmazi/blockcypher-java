package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockCypherJsonTest {

    private static final Logger log = LoggerFactory.getLogger(BlockCypherJsonTest.class);

    private SimpleDateFormat utcFormat;

    {
        utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

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
    }

    @Test
    public void shouldDeserializeTransaction() throws Exception {
        Transaction tx = parse(Transaction.class);
        log.debug("tx = {}", tx);
        assertThat(tx.getBlockHash()).isEqualTo("000000000000000003e41e1329997acbf1849f21eef94cefec2eaa3f94ff1360");
        assertThat(tx.getHash()).isEqualTo("3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682");
        assertThat(tx.getHash()).isEqualTo("3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682");
        assertThat(tx.getTotal()).isEqualTo(new BigInteger("63380642"));
        assertThat(tx.getFees().intValue()).isEqualTo(30000);
        assertThat(tx.isDoubleSpend()).isFalse();
        assertThat(tx.getConfirmed()).isInThePast();
        assertThat(utcFormat.format(tx.getConfirmed())).isEqualTo("2016-01-28T08:51:20");
        assertThat(tx.getReceived()).isInThePast();
        assertThat(utcFormat.format(tx.getReceived())).isEqualTo("2016-01-28T08:40:23");
    }

    @Test
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void shouldDeserializeTxException() throws Exception {
        BlockCypherTxException txe = parse(BlockCypherTxException.class);
        assertThat(txe.getMessage()).contains("Error validating generated transaction: Output exceeds max money value.");

        Transaction tx = txe.getTx();
        assertThat(tx.getAddresses()).contains("mhqRXa1VBLFtpbRyyiouduv3zbPiKqi8v3");
        assertThat(tx.getHash()).isEqualTo("94b9f54514feab03c5b600da8376f4b0110538820fa171262e761a0f0fe9e0ac");
        assertThat(tx.getTotal()).isEqualTo(new BigInteger("18446744073709473205"));
        assertThat(tx.getOutputs()).hasSize(1);
        assertThat(tx.getOutputs()[0].getValue()).isEqualTo(new BigInteger("-78411"));
        assertThat(tx.getInputs()).hasSize(33);
        assertThat(tx.getInputs()[1].getOutputValue().intValue()).isEqualTo(819);
        assertThat(tx.isDoubleSpend()).isFalse();
        assertThat(utcFormat.format(tx.getReceived())).isEqualTo("2016-09-09T08:31:04");
        assertThat(tx.getReceived()).isInThePast();
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