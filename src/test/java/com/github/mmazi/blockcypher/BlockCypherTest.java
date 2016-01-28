package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.Transaction;
import org.testng.annotations.Test;
import si.mazi.rescu.RestProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockCypherTest {

    private BlockCypher bc = RestProxyFactory.createProxy(BlockCypher.class, "https://api.blockcypher.com");

    @Test
    public void shouldGetTransaction() throws Exception {
        final String hash = "3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682";
        final Transaction tx = bc.getTransaction(hash, false);

        assertThat(tx.getHash()).isEqualTo(hash);
        //assertThat(tx.getConfidence()).isNull(); // Currently still returned even with includeConfidence = false.
    }

    @Test
    public void shouldGetTransactionWithConfidence() throws Exception {
        final String hash = "3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682";
        final Transaction tx = bc.getTransaction(hash, true);

        assertThat(tx.getHash()).isEqualTo(hash);
        assertThat(tx.getConfidence()).isNotNull();
    }
}