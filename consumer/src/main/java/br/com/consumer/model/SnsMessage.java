package br.com.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsMessage {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Timestamp")
    private String timestampo;

    @JsonProperty("MessageId")
    private String messageId;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(final String topicArn) {
        this.topicArn = topicArn;
    }

    public String getTimestampo() {
        return timestampo;
    }

    public void setTimestampo(final String timestampo) {
        this.timestampo = timestampo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
}
