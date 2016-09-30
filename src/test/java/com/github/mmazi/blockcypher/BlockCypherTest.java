package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.AddressInfo;
import com.github.mmazi.blockcypher.data.BlockCypherException;
import com.github.mmazi.blockcypher.data.Transaction;
import com.github.mmazi.blockcypher.data.TxSkeleton;
import com.google.common.collect.ImmutableMap;
import org.assertj.core.api.ThrowableAssert;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.params.MainNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import si.mazi.rescu.RestProxyFactory;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class BlockCypherTest {
    private static final Logger log = LoggerFactory.getLogger(BlockCypherTest.class);
    private static final String TOKEN = "67a052c824cf4455bc58b3e4c0e3fd0b";

    private BlockCypher bc = RestProxyFactory.createProxy(BlockCypher.class, "https://api.blockcypher.com/v1/btc/main/");

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test(enabled = false)
    public void getWalletShouldFailWithNonexistentWalletName() throws Exception {
        BlockCypherException throwable = (BlockCypherException)catchThrowable(new ThrowableAssert.ThrowingCallable() {
            public void call() throws Throwable {
                bc.getWallet("no-such-wallet", TOKEN);
            }
        });
        assertThat(throwable)
                .isInstanceOf(BlockCypherException.class)
                .hasMessageContaining("Wallet not found");

        assertThat(throwable.getHttpStatusCode()).isEqualTo(404);
    }

    @Test(enabled = false)
    public void shouldGetTransaction() throws Exception {
        final String hash = "3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682";
        final Transaction tx = bc.getTransaction(hash, false, null);

        assertThat(tx.getHash()).isEqualTo(hash);
        assertThat(tx.getConfirmed()).isInThePast();
        assertThat(tx.getReceived()).isInThePast();
        //assertThat(tx.getConfidence()).isNull(); // Currently still returned even with includeConfidence = false.
    }

    @Test(enabled = false)
    public void shouldGetTransactions() throws Exception {
        final List<Transaction> tx = bc.getTransactions(new BlockCypher.Strings(
                Arrays.asList("3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682", "3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682")
        ), false, null);

        assertThat(tx).hasSize(2);
    }

    @Test(enabled = false)
    public void shouldGetTransactionWithConfidence() throws Exception {
        final String hash = "3c8897ce06418a00a880e9d465365e01119252dbdfa39ed5906c4195e7db2682";
        final Transaction tx = bc.getTransaction(hash, true, TOKEN);

        assertThat(tx.getHash()).isEqualTo(hash);
        assertThat(tx.getReceived()).isInThePast();
        assertThat(tx.getConfidence()).isNotNull();
    }

    @Test(enabled = false)
    public void shouldGetAddressBalance() throws Exception {
        final AddressInfo balance = bc.getAddressBalance("173ujrhEVGqaZvPHXLqwXiSmPVMo225cqT");

        log.debug("balance = {}", balance);

        assertThat(balance.getAddress()).isEqualTo("173ujrhEVGqaZvPHXLqwXiSmPVMo225cqT");
        assertThat(balance.getTotalReceived()).isGreaterThan(new BigInteger("100000000000"));
    }

    @Test(enabled = false)
    public void shouldGetAddressFullInfo() throws Exception {
        final AddressInfo addressInfo = bc.getAddressFullInfo("33WDB3nsVHH5qafCAriPb8Jmb1e3ptsY4h", 429336, 429168, 50, 200, null, null, null, null);

        log.debug("addressInfo = {}", addressInfo);

        assertThat(addressInfo.getAddress()).isEqualTo("33WDB3nsVHH5qafCAriPb8Jmb1e3ptsY4h");
        assertThat(addressInfo.getTotalReceived()).isGreaterThan(new BigInteger("100000000000"));
        assertThat(addressInfo.hasMore()).isFalse();
        assertThat(addressInfo.getTxs().size()).isGreaterThan(20);
        for (Transaction transaction : addressInfo.getTxs()) {
            assertThat(transaction.getInputs()).isNotEmpty();
            assertThat(transaction.getNextInputs()).isNull();
        }
        assertThat(addressInfo.getWallet()).isNull();
        assertThat(addressInfo.getHdWallet()).isNull();
    }

    @Test(enabled = false)
    public void shouldGetAddressInfo() throws Exception {
        final AddressInfo addressInfo = bc.getAddressInfo("3KbWWjumBGLBUWYCeidydxe1uET9QyWoEg", null, null, 33, null, null, null);

        log.debug("addressInfo = {}", addressInfo);

        assertThat(addressInfo.getAddress()).isEqualTo("3KbWWjumBGLBUWYCeidydxe1uET9QyWoEg");
        assertThat(addressInfo.getTotalReceived()).isGreaterThan(new BigInteger("10000000000"));
        assertThat(addressInfo.getTxrefs()).isNotNull().hasSize(33);
    }

    @Test(enabled = false)
    public void shouldCreateSignSendTx() throws Exception {
        byte[] masterEntropy = Hex.decode("ed1fca83d319615dd0eed49576db10d21f05a7ea6d461516f5804dc2c1316372");
        List<String> words = MnemonicCode.INSTANCE.toMnemonic(masterEntropy);
        byte[] masterSeed = MnemonicCode.toSeed(words, "");
        DeterministicKey key = HDKeyDerivation.createMasterPrivateKey(masterSeed);

        String inputAddress = key.toAddress(MainNetParams.get()).toBase58();
        log.debug("inputAddress = {}", inputAddress);

        List<String> inputAddresses = Collections.singletonList(inputAddress);
        ImmutableMap<String, BigInteger> outputs = ImmutableMap.of("1LUJshaMHcArBDN8AM4bviKFVVRhSo73rB", new BigInteger("1000"));
        Transaction tx = Transaction.newTransaction(inputAddresses, outputs, Transaction.Preference.high);
        TxSkeleton txSkeleton = bc.newTransaction(tx, true);

        TxSkeleton.Bytes[] toSign = txSkeleton.getTosign();
        TxSkeleton.Bytes[] signatures = new TxSkeleton.Bytes[toSign.length];
        for (int i = 0; i < toSign.length; i++) {
            byte[] signature = key.sign(Sha256Hash.wrap(toSign[i].get())).encodeToDER();
            signatures[i] = new TxSkeleton.Bytes(signature);
        }

        txSkeleton.setSignatures(signatures);
        txSkeleton.setPubkeys(new TxSkeleton.Bytes[]{new TxSkeleton.Bytes(key.getPubKey())});

        bc.sendTransaction(txSkeleton);
    }
}