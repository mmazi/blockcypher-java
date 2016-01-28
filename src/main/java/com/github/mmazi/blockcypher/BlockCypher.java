package com.github.mmazi.blockcypher;

import com.github.mmazi.blockcypher.data.BlockCypherException;
import com.github.mmazi.blockcypher.data.BlockCypherWallet;
import com.github.mmazi.blockcypher.data.Confidence;
import com.github.mmazi.blockcypher.data.Event;
import com.github.mmazi.blockcypher.data.Transaction;

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
import java.util.List;

@Path("v1/btc/main")
@Produces(MediaType.APPLICATION_JSON)
public interface BlockCypher {

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
    @Path("hooks")
    Event[] listHooks(@QueryParam("token") String token) throws IOException, BlockCypherException;

    @POST
    @Path("wallets/hd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet registerHdWallet(@QueryParam("token") String token, BlockCypherWallet wallet)
            throws IOException, BlockCypherException;

    @GET
    @Path("wallets")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> listWalletNames(@QueryParam("token") String token)
            throws IOException, BlockCypherException;

    // todo! This doesn't work (conflicts with the get wallet by name endpoint)
    @GET
    @Path("wallets/hd")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> listHdWalletNames(@QueryParam("token") String token)
            throws IOException, BlockCypherException;

    @GET
    @Path("wallets/hd/{walletName}")
    @Produces(MediaType.APPLICATION_JSON)
    BlockCypherWallet getWallet(@QueryParam("token") String token, @PathParam("walletName") String walletName)
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

    @GET
    @Path("/txs/{txhash}")
    Transaction getTransaction(@PathParam("txhash") String txhash, @QueryParam("includeConfidence") Boolean includeConfidence)
            throws IOException, BlockCypherException;

    @GET
    @Path("/txs/{txhash}/confidence")
    Confidence getConfidence(@PathParam("txhash") String txhash)
            throws IOException, BlockCypherException;

}
