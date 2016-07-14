package com.github.mmazi.blockcypher.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxSkeleton {

    private Transaction tx;
    private Bytes[] tosign;
    private Bytes[] signatures;
    private Bytes[] pubkeys;
    @Nullable
    private Bytes[] tosignTx;

    protected TxSkeleton() { }

    private TxSkeleton(Transaction tx) {
        this.tx = tx;
    }

    public Transaction getTx() {
        return tx;
    }

    public Bytes[] getTosign() {
        return tosign;
    }

    public void setSignatures(Bytes[] signatures) {
        this.signatures = signatures;
    }

    public void setPubkeys(Bytes[] pubkeys) {
        this.pubkeys = pubkeys;
    }

    @Nullable
    public Bytes[] getTosignTx() {
        return tosignTx;
    }

    public Bytes[] getSignatures() {
        return signatures;
    }

    public Bytes[] getPubkeys() {
        return pubkeys;
    }

    @JsonDeserialize(using = Bytes.Deserializer.class)
    @JsonSerialize(using = Bytes.Serializer.class)
    public static class Bytes {
        private byte[] bytes;

        public Bytes(byte[] bytes) {
            this.bytes = bytes;
        }

        Bytes(String hex) {
            //bytes = new BigInteger(hex, 16).toByteArray();
            bytes = DatatypeConverter.parseHexBinary(hex);
        }

        public byte[] get() {
            return bytes;
        }

        public static class Deserializer extends JsonDeserializer<Bytes> {
            @Override
            public Bytes deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return new Bytes(p.getValueAsString());
            }
        }

        public static class Serializer extends JsonSerializer<Bytes> {
            @Override
            public void serialize(Bytes value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(DatatypeConverter.printHexBinary(value.get()));
            }
        }
    }


}
