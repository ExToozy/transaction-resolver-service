package ru.extoozy.transactionresolver.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("t1.kafka.topic")
public class TopicProperties {

    private String transactionAccept;

    private String transactionResult;

}
