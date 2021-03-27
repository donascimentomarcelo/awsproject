package br.com.consumer.service;

import br.com.consumer.model.Envelope;
import br.com.consumer.model.ProductEvent;
import br.com.consumer.model.SnsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class ProductEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ProductEventConsumer.class);

    private ObjectMapper objectMapper;

    @Autowired
    public ProductEventConsumer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(final TextMessage textMessage) throws JMSException, JsonProcessingException {

        final SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        final Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        LOG.info("Product event received - Event: {} - ProductId: {} - ",
                envelope.getEventType(),
                productEvent.getProductId());
    }
}
