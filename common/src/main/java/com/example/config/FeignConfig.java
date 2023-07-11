package com.example.config;

import feign.*;
import feign.codec.EncodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {

    @Value("${feign.client.config.default.connectTimeout}")
    private int connectTimeout;

    @Value("${feign.client.config.default.readTimeout}")
    private int readTimeout;

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; //设置日志级别为 FULL
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, 1000, 3);
    }
}