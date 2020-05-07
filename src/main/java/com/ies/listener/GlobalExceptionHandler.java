package com.ies.listener;

import com.ies.constast.SysConstast;
import com.ies.utils.ResultObj;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: base
 * @description: 统一异常处理
 * @author: fuchen
 * @create: 2020-04-11 14:56
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public ResultObj handleException(Exception e) {
        return ResultObj.customResult(-1, e.getMessage());
    }

}
