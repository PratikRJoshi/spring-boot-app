package com.pratik.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class Payload {
    @NotBlank(message = "payload_id cannot be blank")
    @JsonProperty("payload_id") // helps Jackson map JSON fields to this Java field.
    private String payloadId;

    @NotBlank(message = "kafka_topic cannot be blank")
    @JsonProperty("kafka_topic") // helps Jackson map JSON fields to this Java field.
    private String kafkaTopic;

    // Java 8+ time representation that’s easy to work with
    @NotNull(message = "created_at instant is mandatory") // @NotBlank works only on Strings, not on Instant. @NotNull is needed
    @JsonProperty("created_at") // helps Jackson map JSON fields to this Java field.
    private Instant createdAt;
}
