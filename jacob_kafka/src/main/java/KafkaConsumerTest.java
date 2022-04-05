import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author jacob
 * @Date 2022/3/27 16:31
 * @Version 1.0
 */
public class KafkaConsumerTest {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.0.8:9092,172.16.0.9:9092,172.16.0.10:9092");
        //消费者组id
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("auto.commit.interval.ms", "1000");
        //消费者消费数据分区策略
        //org.apache.kafka.clients.consumer.ConsumerPartitionAssignor consumerPartitionAssignor;
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");
        //反序列化
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("kafka-001"));
        while (true) {
            //间隔时间拉取一次， ofMillis 为毫秒
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                // TimeUnit.SECONDS.sleep(1);
                System.out.printf("offset = %d, key = %s,partion=%s, value = %s%n", record.offset(), record.key(), record.partition(), record.value());
            }

            //异步提交
            consumer.commitAsync();
            //同步提交
            consumer.commitSync();
        }
    }
}
