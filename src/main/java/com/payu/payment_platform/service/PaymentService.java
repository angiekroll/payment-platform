/**
 * Copyright 2024. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.service;

import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.exception.PaymentPlatformException;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public interface PaymentService {

  PaymentResponseDto processingPayment(PaymentRequestDto paymentRequestDto)
      throws PaymentPlatformException;

}