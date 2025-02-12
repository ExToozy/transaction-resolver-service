package ru.extoozy.transactionresolver.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.UUID;

@Component
public class TransactionRateLimiter {

    private Cache<UUID, LinkedList<LocalDateTime>> requestsCache;

    @Value("${t1.rate-limiter.transaction-limit.max-requests}")
    private Integer maxRequests;

    @Value("${t1.rate-limiter.transaction-limit.max-interval-between-requests}")
    private Integer maxInterval;

    @PostConstruct
    public void configureRequestsCache() {
        requestsCache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.of(maxInterval, ChronoUnit.MILLIS))
                .build();
    }

    @SneakyThrows
    public boolean isRateLimited(UUID accountId, LocalDateTime requestedAt) {
        var requestTimeList = requestsCache.get(accountId, LinkedList::new);

        if (isRequestsCountEqualsMaximum(requestTimeList) &&
                isRequestIntervalBiggerThanMaxTimeMs(requestTimeList)) {
            return true;
        }

        if (isRequestsCountEqualsMaximum(requestTimeList)) {
            requestTimeList.set(0, requestTimeList.get(1));
            requestTimeList.set(maxRequests - 1, requestedAt);
        } else {
            requestTimeList.add(requestedAt);
        }

        return false;
    }

    private boolean isRequestsCountEqualsMaximum(LinkedList<LocalDateTime> requestTimeList) {
        return requestTimeList.size() == maxRequests;
    }

    private boolean isRequestIntervalBiggerThanMaxTimeMs(LinkedList<LocalDateTime> requestTimeList) {
        return requestTimeList.getFirst()
                .plus(maxInterval, ChronoUnit.MILLIS)
                .isAfter(requestTimeList.getLast());
    }
}
