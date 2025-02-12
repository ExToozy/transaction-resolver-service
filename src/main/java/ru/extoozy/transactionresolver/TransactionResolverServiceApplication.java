package ru.extoozy.transactionresolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TransactionResolverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionResolverServiceApplication.class, args);
    }

}
