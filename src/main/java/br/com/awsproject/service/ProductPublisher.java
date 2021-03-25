package br.com.awsproject.service;

import br.com.awsproject.enums.EventType;
import br.com.awsproject.model.Envelope;
import br.com.awsproject.model.Product;
import br.com.awsproject.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPublisher.class);

    private AmazonSNS snsClient;
    private Topic productEventTopic;
    private ObjectMapper objectMapper;

    public ProductPublisher(
            AmazonSNS snsClient,
            @Qualifier("productEventTopic")Topic productEventTopic,
            ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productEventTopic = productEventTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setProductId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(product.getName());

        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);

        try {
            envelope.setData(objectMapper.writeValueAsString(productEvent));

            snsClient.publish(
                    productEventTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelope)
            );
        } catch (JsonProcessingException e) {
            LOG.error("Failed to create product event message");
        }
    }
}
