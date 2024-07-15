/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.service;

import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.dto.CardDto;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.exception.PaymentPlatformException;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */


public interface CustomerService {

  Customer validateCustomerExistence(String type, String documentNumber)
      throws PaymentPlatformException;

  Customer save(CustomerDto customerDto) throws PaymentPlatformException;

}