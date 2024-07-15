/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.exception;

import com.payu.payment_platform.dto.NotificationDto;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Slf4j
@RestControllerAdvice
public class HandlerExceptions {

  @ExceptionHandler(PaymentPlatformException.class)
  public ResponseEntity<Object> handlerPaymentPlatformException(PaymentPlatformException ex) {
    log.error("Exception: [{}]", ex.getErrorCode().getDescription(), ex);
    return new ResponseEntity<>(
        NotificationDto.builder()
            .message(ex.getErrorCode().getDescription())
            .status(ex.getErrorCode().getHttpStatus().value())
            .error(ex.getErrorCode().getHttpStatus().getReasonPhrase())
            .timestamp(Instant.now())
            .build(),
        ex.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<NotificationDto> handlerException(Exception ex) {
    log.error("Unexpected exception: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(NotificationDto.builder()
            .message("Unexpected error occurred. " + ex.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .timestamp(Instant.now())
            .build());
  }

}