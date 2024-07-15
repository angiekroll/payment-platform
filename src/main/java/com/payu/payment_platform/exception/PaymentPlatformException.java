/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.exception;

import com.payu.payment_platform.constans.NotificationCode;
import lombok.Getter;

/**
 *
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public class PaymentPlatformException extends Exception {

  @Getter
  private final NotificationCode errorCode;

  public PaymentPlatformException(NotificationCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }

  public PaymentPlatformException(NotificationCode errorCode, Throwable cause) {
    super(errorCode.getDescription(), cause);
    this.errorCode = errorCode;
  }

}