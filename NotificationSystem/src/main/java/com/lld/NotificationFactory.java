package com.lld;

import com.lld.enums.ChannelType;
import com.lld.strategy.EmailGateway;
import com.lld.strategy.NotificationGateway;
import com.lld.strategy.WhatsappGateway;

import java.util.HashMap;

public class NotificationFactory {
    private final static HashMap<ChannelType, NotificationGateway> gatewayMap = new HashMap<>();

    public static NotificationGateway createGateway(ChannelType type) {
        NotificationGateway gateway = null;
        switch (type) {
            case EMAIL:
                gateway = new EmailGateway();
                break;
            case WHATSAPP:
                gateway = new WhatsappGateway();
                break;
        }
        gatewayMap.put(type, gateway);
        return gateway;
    }
}
