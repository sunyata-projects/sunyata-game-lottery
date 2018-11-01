package client;

import org.sunyata.game.server.OctopusClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 *
 */
@Component
public final class TestClient {
    private static final int THREAND_NUMS = 1;

    private OctopusClient octopusClient;

    private String bossAddress;
    private int bossPort;
    private TestClientHandler bossClientHandler;

    public String getBossAddress() {
        return bossAddress;
    }

    public TestClient setBossAddress(String bossAddress) {
        this.bossAddress = bossAddress;
        return this;
    }

    public int getBossPort() {
        return bossPort;
    }

    public TestClient setBossPort(int bossPort) {
        this.bossPort = bossPort;
        return this;
    }

    public TestClientHandler getBossClientHandler() {
        return bossClientHandler;
    }

    public TestClient setBossClientHandler(TestClientHandler bossClientHandler) {
        this.bossClientHandler = bossClientHandler;
        return this;
    }

    public TestClient() {
    }
    //    public void start(String bossAddress, int bossPort, OctopusMsgHandler<?> bossClientHandler) throws Exception {
//        octopusClient = OctopusClient.createOctopusClient(bossAddress, bossPort, bossClientHandler, THREAND_NUMS);
//        connect();
//    }
    public void init() {
        octopusClient = OctopusClient.createWebSocketClient(bossAddress, bossPort, bossClientHandler, THREAND_NUMS);
    }

    public void connect() throws Exception {
        octopusClient.connectRetry();
    }

    public void close() throws ExecutionException, InterruptedException {
        octopusClient.close();
    }

    public void writeAndFlush(Object msg) {
        octopusClient.writeAndFlush(msg);
    }
}
