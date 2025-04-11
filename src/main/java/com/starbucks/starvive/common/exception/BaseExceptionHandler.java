package com.starbucks.starvive.common.exception;

import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    /**
     * 발생한 예외 처리
     */

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> BaseError(BaseException e) {
        ErrorResponse response = new ErrorResponse(e.getStatus());
        log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
        return new ResponseEntity(response, response.httpStatus());
    }

    /**
     * security 인증 에러
     * 아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생
     *
     * @return FAILED_TO_LOGIN 에러 response
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        ErrorResponse response = new ErrorResponse(BaseResponseStatus.FAILED_TO_LOGIN);
        log.error("BadCredentialsException: ", e);
        return new ResponseEntity(response, response.httpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> RuntimeError(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        log.error("RuntimeException: ", e);
        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }
        return new ResponseEntity(response, response.httpStatus());
    }

}
