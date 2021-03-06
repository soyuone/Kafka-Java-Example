package com.johnnyzheng.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ProducerCompressionTest {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.137.129:9092,192.168.137.130:9092,192.168.137.131:9092");
    props.put(ProducerConfig.ACKS_CONFIG, "1");
    props.put(ProducerConfig.RETRIES_CONFIG, 2);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    //启用压缩,压缩只是影响传输,消费者无需特殊设置
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");

    Producer<String, String> producer = null;
    try {
      producer = new KafkaProducer<>(props);
      for (int i = 0; i < 150; i++) {
        String msg = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date())+"Message " + i;
        producer.send(new ProducerRecord<String, String>("HelloKafkaTopic", msg));
        System.out.println("Sent:" + msg);
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      producer.close();
    }

  }

}
