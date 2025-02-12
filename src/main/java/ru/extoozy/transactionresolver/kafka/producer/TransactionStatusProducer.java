package ru.extoozy.transactionresolver.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.extoozy.transactionresolver.config.property.TopicProperties;
import ru.extoozy.transactionresolver.dto.TransactionStatusDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionStatusProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final TopicProperties topics;

    public void send(TransactionStatusDto transactionStatusDto) {
        try {
            kafkaTemplate.send(topics.getTransactionResult(), transactionStatusDto);
            log.info("message was send %s to topic %s".formatted(topics.getTransactionResult(), transactionStatusDto));
        } catch (Exception e) {
            log.error("Error occurred while trying send message: ", e);
        } finally {
            kafkaTemplate.flush();
        }
    }

}
