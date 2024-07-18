package com.payu.payment_platform.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
  import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.domain.Payment;
import com.payu.payment_platform.dto.CardDto;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.repository.PaymentRepository;
import com.payu.payment_platform.service.BankService;
import com.payu.payment_platform.service.CustomerService;
import com.payu.payment_platform.service.FraudPreventionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceImplTest {

  @InjectMocks
  PaymentServiceImpl paymentServiceIml;
  @Mock
  FraudPreventionService fraudPreventionService;
  @Mock
  CustomerService customerService;
  @Mock
  PaymentRepository paymentRepository;

  @Mock
  BankService bankService;

  @Test
  void processingPayment_success() throws PaymentPlatformException {

    doNothing().when(fraudPreventionService).sendInfoToFraudPrevention(any(CustomerDto.class));

    when(customerService.validateCustomerExistence(anyString(), anyString()))
        .thenReturn(Customer.builder().build());

    when(customerService.save(any(CustomerDto.class)))
        .thenReturn(Customer.builder()
            .id(1L)
            .firstName("Angie")
            .lastName("Rodriguez Suarez")
            .identificationType("CC")
            .identification("1234")
            .email("angie.rodriguez@example.com")
            .build());

    when(bankService.sendInfoToBank(any(PaymentRequestDto.class)))
        .thenReturn(Boolean.TRUE);

    when(paymentRepository.save(any(Payment.class)))
        .thenReturn(Payment.builder()
            .id(1L)
            .amount(new BigDecimal("100.50"))
            .currency("COP")
            .build());

    PaymentResponseDto result = paymentServiceIml.processingPayment(buildPaymentRequestDto());

    assertEquals("APPROVED", result.getStatus());
  }

  @Test
  void processingPayment_throwsExceptionOnDatabaseError() throws PaymentPlatformException {

    doNothing().when(fraudPreventionService).sendInfoToFraudPrevention(any(CustomerDto.class));

    when(customerService.validateCustomerExistence(anyString(), anyString()))
        .thenReturn(null);

    Mockito.when(customerService.save(any()))
        .thenThrow(new PaymentPlatformException(NotificationCode.ERROR_PROCESSING_DATA));

    assertThrows(PaymentPlatformException.class,
        () -> paymentServiceIml.processingPayment(buildPaymentRequestDto()));

  }

  PaymentRequestDto buildPaymentRequestDto() {

    return PaymentRequestDto.builder()
        .amount(new BigDecimal("100.50"))
        .currency("COP")
        .installmentsCount("1").description("Payment for services")
        .customerDto(CustomerDto.builder()
            .firstName("Angie")
            .lastName("Rodriguez Suarez")
            .identificationType("CC")
            .identification("1234")
            .email("angie.rodriguez@example.com")
            .build())
        .cardDto(CardDto.builder()
            .cardNumber("1234567890123456")
            .cardType("CREDIT")
            .expirationDate("12/25")
            .cvv("123")
            .cardHolder("Angie Rodriguez S.")
            .build())
        .build();
  }


}