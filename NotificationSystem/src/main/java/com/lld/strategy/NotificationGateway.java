package com.lld.strategy;

import com.lld.model.Notification;

public interface NotificationGateway {
    void sendNotification(Notification notification);
}
