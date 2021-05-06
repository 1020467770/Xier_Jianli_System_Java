package cn.sqh.web.exception.handler;

import cn.sqh.domain.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//感觉没有Spring自带的写的好，就放这看看了
    public Result exceptionHanlder(Exception ex) {
        log.debug(ex.getMessage());
        return Result.build(Result.RESULTTYPE_EXCEPTION, ex.getMessage());
    }
}
