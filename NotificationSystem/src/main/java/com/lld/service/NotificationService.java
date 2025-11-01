package com.lld.service;

import com.lld.NotificationFactory;
import com.lld.enums.ChannelType;
import com.lld.kafka.SimpleKafkaConsumer;
import com.lld.model.Notification;
import com.lld.strategy.NotificationGateway;

public class NotificationService {

    public void sendNotification(Notification notification) {
        ChannelType notificationType = notification.getNotificationType();
        NotificationGateway gateway = NotificationFactory.createGateway(notificationType);
        gateway.sendNotification(notification);
        SimpleKafkaConsumer.consume("email-topic");
    }
}
