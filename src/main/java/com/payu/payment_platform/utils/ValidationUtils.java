/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.utils;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.exception.PaymentPlatformException;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public class ValidationUtils {

  public static void validateInputFields(BindingResult resultErrors) throws PaymentPlatformException {
    if (resultErrors.hasErrors()) {
      List<FieldError> fieldErrors = resultErrors.getFieldErrors();
      List<String> errorMessages = fieldErrors.stream()
          .map(error -> error.getField() + ": " + error.getDefaultMessage())
          .toList();
      throw new PaymentPlatformException(NotificationCode.INVALID_INPUT_FIELD,
          new Throwable(errorMessages.toString()));
    }
  }

}