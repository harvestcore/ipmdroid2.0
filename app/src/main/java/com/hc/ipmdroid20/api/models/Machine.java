package com.hc.ipmdroid20.api.models;

public class Machine {
    public String name;
    public String description;
    public String type;
    public String ipv4;
    public String ipv6;
    public String mac;
    public String broadcast;
    public String gateway;
    public String netmask;
    public String network;

    public Machine(
        String name,
        String description,
        String type,
        String ipv4,
        String ipv6,
        String mac,
        String broadcast,
        String gateway,
        String netmask,
        String network
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.mac = mac;
        this.broadcast = broadcast;
        this.gateway = gateway;
        this.netmask = netmask;
        this.network = network;
    }

    public Machine(
        String name,
        String description,
        String type,
        String ipv4,
        String mac
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.ipv4 = ipv4;
        this.mac = mac;
    }
}
