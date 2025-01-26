package ru.extoozy.transactionresolver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionDto {

    @JsonProperty("client_id")
    private Long clientId;

    @JsonProperty("account_id")
    private UUID accountId;

    @JsonProperty("transaction_id")
    private UUID transactionId;

    @JsonProperty("requested_at")
    private LocalDateTime requestedAt;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("account_balance")
    private BigDecimal accountBalance;
}
