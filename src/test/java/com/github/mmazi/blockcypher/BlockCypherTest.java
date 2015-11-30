package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.Transaction;
import org.testng.annotations.Test;
import si.mazi.rescu.RestProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockCypherTest {

    private BlockCypher bc = RestProxyFactory.createProxy(BlockCypher.class, "https://api.blockcypher.com");

    @Test
    public void shouldGetTransaction() throws Exception {
        final String hash = "326da493913aa452d0bfe3ed641277ba87e38c0c220244075bae5a8c6442f9c9";
        final Transaction tx = bc.getTransaction(hash);

        assertThat(tx.getHash()).isEqualTo(hash);
        assertThat(tx.getConfirmations()).isGreaterThan(1);
    }
}