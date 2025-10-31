package com.lld.strategy;

import com.lld.enums.ChannelType;
import com.lld.model.Notification;
import com.lld.model.User;

import java.util.List;

public class WhatsappGateway implements NotificationGateway{
    @Override
    public void sendNotification(Notification notification) {
        List<User> receiptList = notification.getReceiverList();
        ChannelType channelType = notification.getNotificationType();
        String client = notification.getSenderId();

        for(User user : receiptList){
            if(user.getChannelsForClient(client).stream().anyMatch(c -> channelType.equals(channelType))){
                System.out.println("--- Sending WHATSAPP ---");
                System.out.println("To: " + user.getName());
                System.out.println("Body: " + notification.getContent());
                System.out.println("---------------------\n");
            }
        }
    }
}
