package ru.extoozy.transactionresolver.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.extoozy.transactionresolver.dto.TransactionDto;
import ru.extoozy.transactionresolver.service.TransactionResolverService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionConsumer {

    private final TransactionResolverService transactionService;

    @KafkaListener(
            topics = "${t1.kafka.topic.transaction-accept}"
    )
    public void handle(List<TransactionDto> transactionDtos, Acknowledgment ack) {
        try {
            transactionDtos.forEach(transactionService::resolveTransactionResult);
        } catch (Exception e) {
            log.error("Error occurred while trying read message: ", e);
        } finally {
            ack.acknowledge();
        }
    }
}
