package at.htl.processor;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
class QuoteProcessorTest {

    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Test
    void testProcessQuote() {

        companion.produceStrings()
                .usingGenerator(i -> new ProducerRecord<>(
                        "quote-requests", UUID.randomUUID().toString()));

        ConsumerTask<String, String> quotes = companion
                .consumeStrings()
                .fromTopics("quote-requests", 2);
        quotes.awaitCompletion();
        assertEquals(2, quotes.count());
    }


}