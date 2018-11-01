package org.sunyata.game.service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by leo on 17/11/9.
 */
public class ClientServerInfo {
    public String getServiceName() {
        return serviceName;
    }

    private String serviceName;

    public String getServerAddress() {
        return serverAddress;
    }

    public ClientServerInfo setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public ClientServerInfo setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public Integer getServerId() {
        return serverId;
    }

    public ClientServerInfo setServerId(Integer serverId) {
        this.serverId = serverId;
        return this;
    }

    private String serverAddress;
    private Integer serverPort;
    private Integer serverId;

    public ClientServerInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

//    @Override
//    public boolean equals(Object obj) {
//        ClientServerInfo other = (ClientServerInfo) obj;
//        boolean b = (this.getServiceName().equals(other.getServiceName()))
//                && this.getGatewayServerId().equals(other.getGatewayServerId())
//                && this.getServerAddress().equals(other.getServerAddress())
//                && this.getServerPort().equals(other.getServerPort());
//        return b;
//    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getServiceName())
                .append(this.getServerId())
                .append(this.getServerAddress())
                .append(this.getServerPort())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ClientServerInfo)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final ClientServerInfo otherObject = (ClientServerInfo) obj;

        return new EqualsBuilder()
                .append(this.getServiceName(), otherObject.getServiceName())
                .append(this.getServerId(), otherObject.getServerId())
                .append(this.getServerAddress(), otherObject.getServerAddress())
                .append(this.getServerPort(), otherObject.getServerPort())
                .isEquals();
/*
      if (obj == null)
      {
         return false;
      }
      if (getClass() != obj.getClass())
      {
         return false;
      }
      final SimpleDataExample other = (SimpleDataExample) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
      {
         return false;
      }
      if (this.name != other.name && (this.name == null || !this.name.equals(other.name)))
      {
         return false;
      }
      return true;
 */
    }

    /**
     * Provide String representation of me.
     *
     * @return String representation of me.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("serviceName", this.getServiceName())
                .append("serverId", this.getServerId())
                .append("serverAddress", this.getServerAddress())
                .append("serverPort", this.getServerPort())
                .toString();
    }
}