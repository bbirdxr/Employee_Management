package com.example.config;

import com.example.exception.BusinessException;
import com.example.result.ErrorCode;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Configuration
@Slf4j
public class RestTemplateConfig {

    private static final int RETRY_TIMES = 3;

    private static final int RETRY_INTERVAL = 500;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(getClientHttpRequestFactory()));
        restTemplate.getInterceptors().add(new RestTemplateLoggingRequestInterceptor());
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "password"));

//        restTemplate.setErrorHandler(new ResponseErrorHandler() {
//            @Override
//            public boolean hasError(ClientHttpResponse response) throws IOException {
//                // 只有返回 500 才认为有错误
//                return response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//
//            @Override
//            public void handleError(ClientHttpResponse response) throws IOException {
//                // 如果返回 400，就抛出自定义异常
//                if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
//                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
//                }
//                // 其他情况不做处理
//            }
//        });

//        // 用 UTF-8 StringHttpMessageConverter 替换默认 StringHttpMessageConverter
//        List<HttpMessageConverter<?>> newMessageConverters = new ArrayList<>();
//        for(HttpMessageConverter<?> converter : restTemplate.getMessageConverters()){
//            if(converter instanceof StringHttpMessageConverter){
//                // 默认的是 ios 8859-1
//                StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//                newMessageConverters.add(messageConverter);
//            }else {
//                newMessageConverters.add(converter);
//            }
//        }
//        restTemplate.setMessageConverters(newMessageConverters);

        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpRequestRetryHandler handler = (exception, curRetryCount, context) -> {
            if (curRetryCount > RETRY_TIMES) {
                return false;
            }
            try {
                Thread.sleep((long) curRetryCount * RETRY_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (exception instanceof NoHttpResponseException || exception instanceof ConnectException) {
                return true;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            org.apache.http.HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等 idempotent 的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };

        // HTTP 客户端能够保持的最大连接数
        httpClientBuilder.setRetryHandler(handler).setMaxConnTotal(400);

        // httpClient 连接配置，底层是配置 RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());

        // 连接超时设置 10s
        clientHttpRequestFactory.setConnectTimeout(10000);

        // 读取超时设置 10s
        clientHttpRequestFactory.setReadTimeout(10000);

        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(10000);

        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(10000);

        return clientHttpRequestFactory;
    }

//    // 实现一个拦截器：使用拦截器为每一个 HTTP 请求添加 Basic Auth 认证用户名密码信息
//    private ClientHttpRequestInterceptor getCustomInterceptor() {
//        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
//            httpRequest.getHeaders().set("Authorization",
//                    "Basic " +
//                            Base64.getEncoder()
//                                    .encodeToString("admin:adminpwd".getBytes()));
//            return execution.execute(httpRequest, bytes);
//        };
//        return interceptor;
//    }
}
