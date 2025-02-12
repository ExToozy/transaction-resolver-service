package ru.extoozy.transactionresolver.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("t1.kafka.producer")
public class ProducerProperties {

    private String servers;

    private String retries;

    private String retryBackoffMs;

    private String enableIdempotence;

    private String keySerializer;

    private String valueSerializer;

}
