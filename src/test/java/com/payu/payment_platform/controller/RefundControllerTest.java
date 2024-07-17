/**
 * Copyright 2024, Company. All rights reserved Date: 16/07/24
 */
package com.payu.payment_platform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.payu.payment_platform.domain.Card;
import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.domain.Payment;
import com.payu.payment_platform.repository.PaymentRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@SpringBootTest
@AutoConfigureMockMvc
class RefundControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentRepository paymentRepository;


  @Test
  void givenValidRefundRequest_whenProcessed_thenExpectedSuccess()
      throws Exception {

    Mockito.when(paymentRepository.findById(any())).thenReturn(buildPayment("APPROVED"));

    mockMvc.perform(put("/payments/{paymentId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("COP"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("REFUNDED"));
  }


  @Test
  void givenRejectedPayment_whenFullRefund_thenExpectedFail()
      throws Exception {

    Mockito.when(paymentRepository.findById(any())).thenReturn(buildPayment("REJECTED"));

    mockMvc.perform(put("/payments/{paymentId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().is4xxClientError());

  }

  @Test
  void givenNonExistentPayment_whenFullRefund_thenExpectedFail()
      throws Exception {

    Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/payments/{paymentId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().is4xxClientError());

  }


  Optional<Payment> buildPayment(String satus) {

    return Optional.ofNullable(
        Payment.builder()
            .id(1L)
            .amount(new BigDecimal(100))
            .currency("COP")
            .status(satus)
            .customer(Customer.builder()
                .id(1L)
                .firstName("TEST")
                .lastName("TEST")
                .cards(List.of(Card.builder()
                    .id(1L)
                    .cardNumber("123456789")
                    .expirationDate("12/24")
                    .build()))
                .build())
            .build());
  }


}