import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author jacob
 * @Date 2022/3/27 16:27
 * @Version 1.0
 */
public class ProducerKafkaTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        RecordAccumulator recordAccumulator;
        Properties props = new Properties();
        //服务器集群
        props.put("bootstrap.servers", "172.16.0.8:9092,172.16.0.9:9092,172.16.0.10:9092");

        props.put("acks", "all");
        //key 序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //开启幂等性
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        //设置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        //开启幂等性，小于等于5内，单分区内有序;未开启幂等性，设置为1
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        //设置transactional.id，开启事务时使用
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional.id");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("kafka-001", i % 5, UUID.randomUUID().toString(), String.valueOf(i));
            // producer.send(new ProducerRecord<String, String>("kafka-001",Integer.toString(i), UUID.randomUUID().toString()+Integer.toString(i)));
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e) {
                        System.out.println("发送异常了");
                    } else {
                        System.out.println("offset=" + recordMetadata.offset() + ",partition=" + recordMetadata.partition());
                    }
                }
            });
            TimeUnit.MICROSECONDS.sleep(50);

        }
        producer.close();
    }
}
