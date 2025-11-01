package com.lld;

import com.lld.enums.ChannelType;
import com.lld.model.Notification;
import com.lld.model.User;
import com.lld.service.NotificationService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User shubhra = new User("Shubhra", "shubhramail2000@gmail.com", "8456091277");
        User akash = new User("Akash", "akash712@gmail.com", "9779135436");

        shubhra.addChannelForClient("FB-110", ChannelType.EMAIL);
        akash.addChannelForClient("FB-110", ChannelType.EMAIL);

        Notification notification = new Notification("FB-110", List.of(shubhra, akash), "Notification-1", ChannelType.EMAIL);
        NotificationService notificationService = new NotificationService();
        notificationService.sendNotification(notification);
    }
}