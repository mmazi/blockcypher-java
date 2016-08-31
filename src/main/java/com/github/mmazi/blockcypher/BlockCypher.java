package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.AddressInfo;
import com.github.mmazi.blockcypher.data.BlockCypherException;
import com.github.mmazi.blockcypher.data.BlockCypherWallet;
import com.github.mmazi.blockcypher.data.Confidence;
import com.github.mmazi.blockcypher.data.Event;
import com.github.mmazi.blockcypher.data.Transaction;
import com.github.mmazi.blockcypher.data.TxSkeleton;
import com.github.mmazi.blockcypher.data.WalletNames;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BlockCypher {

    @GET
    @Path("hooks")
    Event[] listHooks(@QueryParam("token") String token) throws IOException, BlockCypherException;

    @POST
    @Path("hooks")
    @Consumes(MediaType.APPLICATION_JSON)
    Event registerHook(Event subscription) throws IOException, BlockCypherException;

    /**
     * Note that this removes <em>all</em> hooks registered with this id. There may be many hooks for an id
     * in the case of HD-wallets: one hook per each used wallet address.
     */
    @DELETE
    @Path("hooks/{hookId}")
    Event unregisterHooks(@QueryParam("token") String token, @PathParam("hookId") String hookId)
            throws IOException, BlockCypherException;

    @GET
    @Path("wallets")
    @Produces(MediaType.APPLICATION_JSON)
    WalletNames listWalletNames(@QueryParam("token") String token)
            throws IOException, BlockCypherException;

    @GET
    @Path("wallets/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet getWallet(@PathParam("name") String walletName, @QueryParam("token") String token)
            throws IOException, BlockCypherException;

    /**
     * @param wallet For normal wallets, at minimum, you must include the name attribute and at least one public address in the addresses array.
     */
    @POST
    @Path("wallets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet registerWallet(@QueryParam("token") String token, BlockCypherWallet wallet)
            throws IOException, BlockCypherException;

    /**
     * This endpoint returns a list of the addresses associated with the $NAME wallet. It returns the addresses in a partially filled out Wallet
     * which you’ll find under the addresses attribute. For HD wallets it returns an HDChain object.
     *
     * @param used Returns only used addresses if set to true and only unused if false.
     * @param zeroBalance Returns only addresses with zero balance if set to true and only addresses with non-zero balance if false.
     */
    @GET
    @Path("wallets/{name}/addresses")
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet getWalletAddresses(
            @PathParam("name") String walletName,
            @QueryParam("token") String token,
            @QueryParam("used") Boolean used,
            @QueryParam("zerobalance") Boolean zeroBalance
    ) throws IOException, BlockCypherException;

    /**
     * This endpoint allows you to add public addresses to the {name} wallet, by POSTing a partially filled out Wallet object.
     * You only need to include the additional addresses in a new addresses array in the object. If successful, it will return the newly
     * modified Wallet, including an up-to-date, complete listing of addresses.
     *
     * @param addresses only the addresses property of the wallet is used as the new addresses to be added.
     * @param omitWalletAddresses omit addresses in the returned object
     */
    @POST
    @Path("wallets/{name}/addresses")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet addWalletAddresses(
            @PathParam("name") String walletName,
            @QueryParam("token") String token,
            @QueryParam("omitWalletAddresses") Boolean omitWalletAddresses,
            BlockCypherWallet addresses
    ) throws IOException, BlockCypherException;

    @POST
    @Path("wallets/hd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet registerHdWallet(@QueryParam("token") String token, BlockCypherWallet wallet)
            throws IOException, BlockCypherException;

    /**
     * This doesn't work (conflicts with the get wallet by name endpoint), though it is still documented
     * and present in the official SDK (client implementations).
     *
     * @deprecated Use {@link #listWalletNames(String)} instead: it lists both normal and HD wallets.
     */
    @GET
    @Path("wallets/hd")
    @Produces(MediaType.APPLICATION_JSON)
    @Deprecated
    WalletNames listHdWalletNames(@QueryParam("token") String token)
            throws IOException, BlockCypherException;

    @GET
    @Path("wallets/hd/{walletName}")
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet getHdWallet(@QueryParam("token") String token, @PathParam("walletName") String walletName)
            throws IOException, BlockCypherException;

    @DELETE
    @Path("wallets/hd/{walletName}")
    void deleteWallet(@QueryParam("token") String token, @PathParam("walletName") String walletName)
            throws IOException, BlockCypherException;

    /**
     * @return an HDWallet but only with the newly derived address(es) represented in its chains field
     */
    @POST
    @Path("wallets/hd/{walletName}/addresses/derive")
    BlockCypherWallet deriveNewAddresses(@QueryParam("token") String token, @PathParam("walletName") String walletName, @QueryParam("count") Integer count)
            throws IOException, BlockCypherException;

    /**
     * @param includeConfidence whether to return confidence info in the response.
     * @param token token is required when includeConfidence is true.
     */
    @GET
    @Path("/txs/{txhash}")
    Transaction getTransaction(
            @PathParam("txhash") String txhash,
            @QueryParam("includeConfidence") Boolean includeConfidence,
            @QueryParam("token") String token
    ) throws IOException, BlockCypherException;

    @GET
    @Path("/txs/{txhash}/confidence")
    Confidence getConfidence(@PathParam("txhash") String txhash)
            throws IOException, BlockCypherException;

    @POST
    @Path("/txs/new")
    @Consumes(MediaType.APPLICATION_JSON)
    TxSkeleton newTransaction(Transaction skeleton, @QueryParam("includeToSignTx") boolean includeToSignTx)
            throws IOException, BlockCypherException;

    @POST
    @Path("/txs/send")
    @Consumes(MediaType.APPLICATION_JSON)
    TxSkeleton sendTransaction(TxSkeleton skeleton) throws IOException, BlockCypherException;

    // todo: The /addrs/{address}/ endpoints can also be used to get a BlockCypher Wallet balance
    // by providing a wallet name in place of {address}.
    /**
     * Returns the balance data, but doesn't include txs.
     */
    @GET
    @Path("/addrs/{address}/balance")
    AddressInfo getAddressBalance(@PathParam("address") String address)
            throws IOException, BlockCypherException;

    /**
     * Includes basic tx info.
     */
    @GET
    @Path("/addrs/{address}")
    AddressInfo getAddressInfo(
            @PathParam("address") String address,
            @QueryParam("before") Integer beforeBlockHeight,
            @QueryParam("after") Integer afterBlockHeight,
            @QueryParam("limit") Integer limitTxs,
            @QueryParam("confirmations") Integer minConfirmations,
            @QueryParam("includeConfidence") Boolean includeConfidence,
            @QueryParam("omitWalletAddresses") Boolean omitWalletAddresses
    ) throws IOException, BlockCypherException;

    /**
     * Includes full tx info.
     */
    @GET
    @Path("/addrs/{address}/full")
    AddressInfo getAddressFullInfo(
            @PathParam("address") String address,
            @QueryParam("before") Integer beforeBlockHeight,
            @QueryParam("after") Integer afterBlockHeight,
            @QueryParam("limit") Integer limitTxs,
            @QueryParam("confirmations") Integer minConfirmations,
            @QueryParam("includeHex") Boolean includeHex,
            @QueryParam("includeConfidence") Boolean includeConfidence,
            @QueryParam("omitWalletAddresses") Boolean omitWalletAddresses
    ) throws IOException, BlockCypherException;



}
