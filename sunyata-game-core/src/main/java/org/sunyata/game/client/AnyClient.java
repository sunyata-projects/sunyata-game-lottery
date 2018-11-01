package org.sunyata.game.client;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.server.OctopusClient;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;

/**
 *
 */
public class AnyClient {
    public static final int THREAND_NUMS = 1;
    private OctopusClient octopusClient = null;
    private boolean isConnect = false;

    private int serverId;
    private String serviceName;
    private ClientServerInfo key;
    private boolean terminative;



    public AnyClient(ClientServerInfo key, AnyClientHandler anyClientHandler) {
        this.key = key;
        this.serverId = key.getServerId();
        this.serviceName = key.getServiceName();
        octopusClient = OctopusClient.createOctopusClient(key.getServerAddress(), key.getServerPort(), anyClientHandler,
                THREAND_NUMS);
    }

    public int getServerId() {
        return serverId;
    }

    public AnyClient setServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public AnyClient setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }


//    public AnyClient(String serviceName, int serverId, String sceneAddress, int scenePort, OctopusMsgHandler<?>
//            messageHandler)
//            throws
//            Exception {
//        this.serverId = serverId;
//        this.serviceName = serviceName;
//        octopusClient = OctopusClient.createOctopusClient(sceneAddress, scenePort, messageHandler, THREAND_NUMS);
//    }

    public void connect() throws Exception {
        octopusClient.connectRetry();
    }

    public void close() throws ExecutionException, InterruptedException {
        octopusClient.close();
    }

    public void writeAndFlush(Object msg) {
        octopusClient.writeAndFlush(msg);
    }

    public boolean isActive() {
        Channel channel = octopusClient.getChannel();
        if(channel==null){
            return false;
        }
        return channel.isActive();
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public void setTerminative(boolean terminative) {
        this.terminative = terminative;
    }

    public boolean isTerminative() {
        return terminative;
    }
}
