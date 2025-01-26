package ru.extoozy.transactionresolver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.extoozy.transactionresolver.dto.TransactionDto;
import ru.extoozy.transactionresolver.dto.TransactionStatusDto;
import ru.extoozy.transactionresolver.enums.TransactionStatus;
import ru.extoozy.transactionresolver.kafka.producer.TransactionStatusProducer;
import ru.extoozy.transactionresolver.service.TransactionResolverService;
import ru.extoozy.transactionresolver.util.TransactionRateLimiter;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionResolverServiceImpl implements TransactionResolverService {

    private final TransactionRateLimiter transactionRateLimiter;

    private final TransactionStatusProducer transactionStatusProducer;

    @Override
    public void resolveTransactionResult(TransactionDto dto) {

        TransactionStatus status;

        if (transactionRateLimiter.isRateLimited(dto.getAccountId(), dto.getRequestedAt())) {
            status = TransactionStatus.BLOCKED;

        } else if (isWithdrawalExceedsBalance(dto)) {
            status = TransactionStatus.REJECTED;

        } else {
            status = TransactionStatus.ACCEPTED;
        }

        TransactionStatusDto transactionStatusDto = TransactionStatusDto.builder()
                .accountId(dto.getAccountId())
                .transactionId(dto.getTransactionId())
                .status(status)
                .build();

        log.info("Transaction with id %s was %s".formatted(dto.getTransactionId(), status));

        transactionStatusProducer.send(transactionStatusDto);

    }

    private boolean isWithdrawalExceedsBalance(TransactionDto dto) {
        return isWithdrawalTransaction(dto.getTransactionAmount()) &&
                isAmountBiggerThanBalance(dto.getTransactionAmount().negate(), dto.getAccountBalance());
    }

    private boolean isWithdrawalTransaction(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isAmountBiggerThanBalance(BigDecimal amount, BigDecimal accountBalance) {
        return amount.compareTo(accountBalance) > 0;
    }
}
