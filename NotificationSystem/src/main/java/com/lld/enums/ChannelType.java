package com.lld.enums;

public enum ChannelType {
    EMAIL("email-topic"),
    WHATSAPP("whatsapp-topic"),
    IN_APP("inapp-topic");

    private final String topicName;

    ChannelType(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
