package com.example.employee.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "topic", consumerGroup = "${rocketmq.consumer.group}")
public class EmployeeRocketMQListener implements RocketMQListener<String> {
    public void onMessage(String message) {
        log.info("收到 mq 消息：" + message);
    }
}
