# blockcypher-java

An unofficical Java client for part of [BlockCypher's API](http://dev.blockcypher.com).

See [BlockCypher.java](/src/main/java/com/github/mmazi/blockcypher/BlockCypher.java) for which methods are implemented.

## Usage

You need Java 7 (or later), maven and git installed on your computer.

Get blockcypher-java to your computer and install:

    git clone https://github.com/mmazi/blockcypher-java.git
    cd blockcypher-java
    mvn clean install

In your pom.xml:

    <dependency>
        <groupId>com.github.mmazi.blockcypher-client</groupId>
        <artifactId>blockcypher-client</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

In your app:

    BlockCypher bc = RestProxyFactory.createProxy(BlockCypher.class, "https://api.blockcypher.com");
    String hash = "326da493913aa452d0bfe3ed641277ba87e38c0c220244075bae5a8c6442f9c9";
    Transaction tx = bc.getTransaction(hash);
