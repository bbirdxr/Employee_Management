package com.example.attendance.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.example.attendance.service.AttendanceService;
import com.example.constant.MQConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class RocketMQConsumerService {

    private String consumerGroup = MQConst.CONSUMER_GROUP;

    private String namesrvAddr = MQConst.NAMESRV_ADDR;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    private AttendanceService attendanceService;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQPushConsumer=new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        // 设置 defaultMQPushConsumer 所订阅的 Topic 和 Tag，* 代表全部的 Tag
        defaultMQPushConsumer.subscribe("topic", "*");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置消费者重试间隔
        defaultMQPushConsumer.setSuspendCurrentQueueTimeMillis(10000);

        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            MessageExt messageExt = list.get(0);
            try {
                String topic = messageExt.getTopic();
                byte[] body = messageExt.getBody();
                String tags = messageExt.getTags();
                String message = new String(body);
                log.info("Receiving messages from topic `{}`: {}",topic, message);
                Long employeeId = JSONObject.parseObject(message).getLong("id");
                log.info("employeeId: {}", employeeId);
                attendanceService.clockIn(employeeId);
            } catch (Exception e) {
                log.error(e.getMessage());
                // 出错了，重试 3 次，出错，保存在 mysql 中，进行数据的收集
                int reconsumeTimes = messageExt.getReconsumeTimes();
                if (reconsumeTimes == 3) {
                    // 进行保存，通知消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        defaultMQPushConsumer.start();
    }

    @PreDestroy
    public void destroy(){
         defaultMQPushConsumer.shutdown();
    }
}