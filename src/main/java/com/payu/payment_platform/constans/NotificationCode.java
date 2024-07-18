/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.constans;

import org.springframework.http.HttpStatus;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public enum NotificationCode {
  INVALID_INPUT_FIELD("Wrong input fields", HttpStatus.BAD_REQUEST),
  INVALID_CARD_TYPE("Invalid card type, only credit card payments accepted", HttpStatus.BAD_REQUEST),
  ERROR_GETTING_RESPONSE_FRAUD_SERVICE("An error occurred while establishing a connection to the fraud external api", HttpStatus.SERVICE_UNAVAILABLE),
  REJECTED_BY_FRAUD("Rejected for fraud", HttpStatus.BAD_REQUEST),
  ERROR_GETTING_RESPONSE_BANK_SERVICE("An error occurred while establishing a connection to the bank external api", HttpStatus.SERVICE_UNAVAILABLE),
  REJECTED_BY_BANK("Rejected for bank", HttpStatus.BAD_REQUEST),
  PAYMENT_NOT_FOUND("Payment not found", HttpStatus.BAD_REQUEST),
  ERROR_PROCESSING_DATA("Error processing data", HttpStatus.INTERNAL_SERVER_ERROR),
  ERROR_FRAUD_SERVICE_UNAVAILABLE("Error calling fraud service", HttpStatus.SERVICE_UNAVAILABLE),
  REFUND_NOT_ALLOWED("Payment cannot be refunded as it is not in approved status.", HttpStatus.BAD_REQUEST),

  ERROR_BANK_SERVICE_UNAVAILABLE("Error calling bank service", HttpStatus.SERVICE_UNAVAILABLE);



  private final HttpStatus httpStatus;
  private final String message;


  NotificationCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getDescription() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}