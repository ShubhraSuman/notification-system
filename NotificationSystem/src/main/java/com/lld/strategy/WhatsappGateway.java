package com.lld.strategy;

import com.lld.enums.ChannelType;
import com.lld.kafka.SimpleKafkaProducer;
import com.lld.model.Notification;
import com.lld.model.User;

import java.util.List;

public class WhatsappGateway implements NotificationGateway {
    @Override
    public void sendNotification(Notification notification) {
        List<User> receiptList = notification.getReceiverList();
        ChannelType channelType = notification.getNotificationType();
        String client = notification.getSenderId();

        for (User user : receiptList) {
            List<ChannelType> supportedChannel = user.getChannelsForClient(client).get();
            if (supportedChannel.stream().anyMatch(channelType1 -> channelType1.equals(channelType))) {
                SimpleKafkaProducer.sendNotification(user.getName(), notification.getContent(), ChannelType.valueOf(String.valueOf(channelType)).getTopicName());
            }
        }
    }
}
