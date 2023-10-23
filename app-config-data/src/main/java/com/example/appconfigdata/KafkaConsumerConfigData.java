package com.example.appconfigdata;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
public class KafkaConsumerConfigData {
    private String keyDeserializer;
    private String valueDeserializer;
    private String consumerGroupId;
    // This property determines what happens when a consumer starts consuming
    // from a Kafka topic for the first time or when it needs to reset its offset
    private String autoOffsetReset;
    // This key might be used to identify the Avro schema for deserialization
    private String specificAvroReaderKey;
    // A flag or configuration indicating whether a specific Avro reader is enabled.
    private String specificAvroReader;
    // A flag or configuration indicating whether batch listening is enabled.
    // In a batch listener, you can process multiple messages at once
    private Boolean batchListener;
    // A flag or configuration indicating whether
    // the consumer should automatically start when the application context is created
    private Boolean autoStartup;
    //The number of concurrent consumer threads that will be used to process Kafka messages.
    // It affects parallelism.
    private Integer concurrencyLevel;
    //  The session timeout for the consumer session,
    //  which is part of group management in Kafka
    private Integer sessionTimeoutMs;
    // The interval at which the consumer sends heartbeats to the Kafka broker to keep its session alive.
    private Integer heartbeatIntervalMs;
    // The maximum interval between poll invocations.
    // If a poll isn't called within this time, the consumer will be considered inactive
    private Integer maxPollIntervalMs;
    // The maximum number of records to poll in a single poll request
    private Integer maxPollRecords;
    // The maximum number of bytes to fetch from a single partition in one request (default value)
    private Integer maxPartitionFetchBytesDefault;
    // A factor that can be used to increase the maximum partition fetch bytes if needed
    private Integer maxPartitionFetchBytesBoostFactor;
    //  The maximum time to block during a poll operation.
    //  If no records are available, the poll will return after this timeout.
    private Long pollTimeoutMs;
}
