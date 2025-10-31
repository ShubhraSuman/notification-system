package com.lld.model;

import com.lld.enums.ChannelType;

import java.util.*;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phoneNo;
    private HashMap<String, List<ChannelType>> allowedChannel;

    public User(String name, String email, String phoneNo) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.allowedChannel = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void addChannelForClient(String client, ChannelType channelType){
        this.allowedChannel.computeIfAbsent(client,k->new ArrayList<>()).add(channelType);
    }

    public boolean removeChannelForClient(String client, ChannelType channelType){
        List<ChannelType> allowedChannelList = this.allowedChannel.get(client);

        if(!allowedChannelList.contains(channelType)){
            return false;
        }
        this.allowedChannel.remove(channelType);
        this.allowedChannel.put(client,allowedChannelList);
        return true;
    }

    public Optional<List<ChannelType>> getChannelsForClient(String client){
        return Optional.ofNullable(this.allowedChannel.getOrDefault(client, new ArrayList<>()));
    }
}
