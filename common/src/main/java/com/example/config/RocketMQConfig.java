package com.example.config;

import com.example.constant.MQConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
@Slf4j
public class RocketMQConfig {

    private String topic = MQConst.TOPIC;

    private String consumerGroup = MQConst.CONSUMER_GROUP;

}
