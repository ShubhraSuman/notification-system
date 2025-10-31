package com.lld.model;

import com.lld.enums.ChannelType;

import java.util.List;
import java.util.UUID;

public class Notification {
    private final String id;
    private final String clientId;
    private List<User> receiverList;
    private final String content;
    private final ChannelType notificationType;

    public Notification(String senderId, List<User> receiverList, String content, ChannelType notificationType) {
        this.id = UUID.randomUUID().toString();
        this.clientId = senderId;
        this.receiverList = receiverList;
        this.content = content;
        this.notificationType = notificationType;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return clientId;
    }

    public List<User> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<User> receiverList) {
        this.receiverList = receiverList;
    }

    public String getContent() {
        return content;
    }

    public ChannelType getNotificationType() {
        return notificationType;
    }

    public void addReceiverForNotication(User user){
        this.receiverList.add(user);
    }

    public boolean removeReceiverFromNotification(User user){
        return this.receiverList.remove(user);
    }
}
