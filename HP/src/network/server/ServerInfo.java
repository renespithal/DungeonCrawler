package network.server;

/**
 * Created by Jenny on 11.01.2016.
 */
public class ServerInfo {

    private String group;

    private int port;
    private String ip;

    private String status;

    public ServerInfo(String group, int port, String ip) {
        this.group = group;
        this.port = port;
        this.ip = ip;
    }

    public ServerInfo(String group, int port, String ip, String status) {
        this.group = group;
        this.port = port;
        this.ip = ip;
        this.status = status;
    }

    /* Getter & Setter */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
