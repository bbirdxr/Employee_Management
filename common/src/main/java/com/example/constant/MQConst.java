package com.example.constant;

public class MQConst {
    public static final String NAMESRV_ADDR = "127.0.0.1:9876";

    public static final String TOPIC = "topic";

    public static final String CONSUMER_GROUP = "consumer_group";

    public static final String PRODUCER_GROUP = "producer_group";

    public static final Integer MAX_MESSAGE_SIZE = 4096;

    public static final Integer SEND_MSG_TIMEOUT = 3000;

    public static final Integer RETRY_TIMES_WHEN_SEND_FAILED = 2;
}
