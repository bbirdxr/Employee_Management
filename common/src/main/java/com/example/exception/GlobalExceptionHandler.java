package com.example.exception;

import com.alibaba.fastjson.JSON;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletResponse response; // 注入响应对象

//    @ExceptionHandler(BusinessException.class)
//    public BaseResponse businessExceptionHandler(BusinessException e) {
//        log.error("businessException: " + e.getMessage(), e);
//        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
//        // log.error("runtimeException", e);
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
//    }

    @ExceptionHandler(BusinessException.class) // 指定处理自定义异常的方法
    public void handleBusinessException(BusinessException e) {
        response.setStatus(e.getCode() / 100); // 设置响应的 HTTP 状态码
        response.setContentType("application/json"); // 设置响应的内容类型
        response.setCharacterEncoding("UTF-8"); // 设置响应的字符编码
        // 设置其他需要的 HTTP 头信息，如 Cache-Control, Expires 等
        try {
            // 将统一异常返回类转换为 JSON 格式，并写入响应体中
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription())));
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 处理 RetryableException 异常
    @ExceptionHandler(RetryableException.class)
    public void handleRetryableException(RetryableException e) {
        response.setStatus(e.status());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "")));
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
