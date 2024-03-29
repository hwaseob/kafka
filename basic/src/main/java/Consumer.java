import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {
        Properties config = new Properties();
        // 환경 변수 설정
//        configs.put("bootstrap.servers", "localhost:9092");     // kafka server host 및 port
        config.put("bootstrap.servers", "192.168.56.101:9092");     // kafka server host 및 port
        config.put("session.timeout.ms", "10000");             // session 설정
        config.put("group.id", "test20180604");                // topic 설정
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");    // key deserializer
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  // value deserializer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);    // consumer 생성
        consumer.subscribe(Arrays.asList("test20180604"));      // topic 설정
        while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
            ConsumerRecords<String, String> records = consumer.poll(500);
            for (ConsumerRecord<String, String> record : records) {
                String s = record.topic();
                if ("test20180604".equals(s)) {
                    System.out.println(record.value());
                } else {
                    throw new IllegalStateException("get message on topic " + record.topic());
                }
            }
        }
    }

}