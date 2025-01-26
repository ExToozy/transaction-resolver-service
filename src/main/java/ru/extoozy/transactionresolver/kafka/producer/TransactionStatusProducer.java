package ru.extoozy.transactionresolver.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.extoozy.transactionresolver.dto.TransactionStatusDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionStatusProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${t1.kafka.topic.transaction_result}")
    private String transactionStatusTopic;

    public void send(TransactionStatusDto transactionStatusDto) {
        try {
            kafkaTemplate.send(transactionStatusTopic, transactionStatusDto);
            log.info("message was send %s to topic %s".formatted(transactionStatusTopic, transactionStatusDto));
        } catch (Exception e) {
            log.error("Error occurred while trying send message: ", e);
        } finally {
            kafkaTemplate.flush();
        }
    }

}
