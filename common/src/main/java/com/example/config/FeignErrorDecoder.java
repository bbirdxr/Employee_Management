package com.example.config;

import com.example.exception.BusinessException;
import com.example.result.ErrorCode;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Configuration
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // 如果 response 的 status 是 4xx
        if (response.status() >= HttpServletResponse.SC_BAD_REQUEST && response.status() < HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.error("请求xxx服务-{},返回:{}", response.status(), response.body());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求xxx服务失败");
        }
        FeignException exception = FeignException.errorStatus(methodKey, response);
        if (response.request().httpMethod() == Request.HttpMethod.GET) { //只对 GET 重试
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    new Date(),
                    response.request());
        }
        return exception;
    }
}
