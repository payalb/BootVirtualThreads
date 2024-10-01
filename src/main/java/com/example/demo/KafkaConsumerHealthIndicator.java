package com.example.demo;

import java.time.Duration;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerHealthIndicator implements HealthIndicator {

    private final KafkaConsumer<String, String> consumer;

    public KafkaConsumerHealthIndicator(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public Health health() {
        try {
            // Perform a simple poll to check if the consumer is alive
            consumer.poll(Duration.ofMillis(100));
            return Health.up().withDetail("Kafka Consumer", "Running").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("Kafka Consumer", "Not reachable").build();
        }
    }
}
