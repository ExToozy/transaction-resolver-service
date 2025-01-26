package ru.extoozy.transactionresolver.service;

import ru.extoozy.transactionresolver.dto.TransactionDto;

public interface TransactionResolverService {
    void resolveTransactionResult(TransactionDto dto);
}
