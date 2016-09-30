package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.AddressInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import si.mazi.rescu.RestProxyFactory;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockCypherEthTest {
    private static final Logger log = LoggerFactory.getLogger(BlockCypherEthTest.class);
    private static final String TOKEN = "67a052c824cf4455bc58b3e4c0e3fd0b";

    private BlockCypher bc = RestProxyFactory.createProxy(BlockCypher.class, "https://api.blockcypher.com/v1/eth/main/");

    @Test(enabled = false)
    public void shouldGetAddressInfo() throws Exception {
        final AddressInfo addressInfo = bc.getAddressInfo("154af3e01ec56bc55fd585622e33e3dfb8a248d8", null, null, 33, null, null, null);

        log.debug("addressInfo = {}", addressInfo);

        assertThat(addressInfo.getAddress()).isEqualTo("154af3e01ec56bc55fd585622e33e3dfb8a248d8");

        assertThat(addressInfo.getTotalReceived()).isGreaterThan(new BigInteger("100000000000"));
        assertThat(addressInfo.getTxrefs()).isNotNull().hasSize(33);
    }

}