package com.example.config;

import com.example.constant.MQConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Slf4j
public class RocketMQProducerConfig {
    private String producerGroup = MQConst.PRODUCER_GROUP;
    private String namesrvAddr = MQConst.NAMESRV_ADDR;

    private Integer maxMessageSize = MQConst.MAX_MESSAGE_SIZE;

    private Integer sendMsgTimeout = MQConst.SEND_MSG_TIMEOUT;

    private Integer retryTimesWhenSendFailed = MQConst.RETRY_TIMES_WHEN_SEND_FAILED;

    private DefaultMQProducer defaultMQProducer;

    @Bean
    public DefaultMQProducer defaultMQProducer() {
        return defaultMQProducer;
    }

    @PostConstruct
    public void initMQ() {
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setMaxMessageSize(this.maxMessageSize);
        defaultMQProducer.setSendMsgTimeout(this.sendMsgTimeout);
        defaultMQProducer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
//        try {
//            defaultMQProducer.start();
//        } catch (MQClientException e) {
//            log.error(e.getErrorMessage());
//        }
    }

    @PreDestroy
    public void destroy() {
        defaultMQProducer.shutdown();
    }
}