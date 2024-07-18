/**
 * Copyright 2024, Company. All rights reserved Date: 18/07/24
 */
package com.payu.payment_platform.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@SpringBootTest()
class CustomerServiceImplTest {

  @InjectMocks
  CustomerServiceImpl customerServiceImpl;

  @Mock
  CustomerRepository customerRepository;

  @BeforeEach
  public void setUp() {
    customerRepository.save(Customer.builder()
        .firstName("Angie")
        .lastName("Rodriguez Suarez")
        .identificationType("CC")
        .identification("1234567890")
        .email("angie.rodriguez@example.com")
        .build());
  }


  @Test
  void validateCustomerExistence_success() throws PaymentPlatformException {

    Mockito.when(customerRepository.findByIdentificationTypeAndIdentification(anyString(), anyString()))
        .thenReturn(Customer.builder()
            .firstName("Angie")
            .lastName("Rodriguez Suarez")
            .identificationType("CC")
            .identification("1234567890")
            .email("angie.rodriguez@example.com")
            .build());

    Customer result = customerServiceImpl.validateCustomerExistence("CC", "1234567890");

    assertEquals("CC", result.getIdentificationType());
    assertEquals("1234567890", result.getIdentification());

  }

  @Test
  void validateCustomerExistence_fail() throws PaymentPlatformException {
    Customer result = customerServiceImpl.validateCustomerExistence("CC", "123");

    assertNull(result);

  }

  @Test
  void validateCustomerExistence_throwsExceptionOnDatabaseError() {

    Mockito.when(customerRepository.findByIdentificationTypeAndIdentification(anyString(), anyString()))
        .thenThrow(new RuntimeException("Simulated database error"));

    assertThrows(PaymentPlatformException.class,
        () -> customerServiceImpl.validateCustomerExistence("CC", "1234567890"));
  }

  @Test
  void saveCustomer_throwsExceptionOnDatabaseError() {

    Mockito.when(customerRepository.save(any()))
        .thenThrow(new RuntimeException("Simulated database error"));

    assertThrows(PaymentPlatformException.class,
        () -> customerServiceImpl.save(any()));
  }

}