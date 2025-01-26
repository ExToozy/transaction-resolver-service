package ru.extoozy.transactionresolver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.extoozy.transactionresolver.enums.TransactionStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionStatusDto {

    @JsonProperty("account_id")
    private UUID accountId;

    @JsonProperty("transaction_id")
    private UUID transactionId;

    private TransactionStatus status;
}
