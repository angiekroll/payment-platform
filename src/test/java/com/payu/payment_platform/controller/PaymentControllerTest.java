/**
 * Copyright 2024, Company. All rights reserved Date: 16/07/24
 */
package com.payu.payment_platform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.payu.payment_platform.dto.CardDto;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.external.apis.bank.BankClient;
import com.payu.payment_platform.external.apis.bank.dto.BankClientResponseDto;
import com.payu.payment_platform.external.apis.fraud.FraudPreventionClient;
import com.payu.payment_platform.external.apis.fraud.dto.FraudClientResponseDto;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

  //
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FraudPreventionClient fraudPreventionClient;

  @MockBean
  private BankClient bankClient;

  @Test
  void givenValidPaymentRequest_whenProcessed_thenSuccessExpected()
      throws Exception {

    String requetsJsonString = new ObjectMapper().writeValueAsString(buildPaymentRequestDto());

    Mockito.when(fraudPreventionClient.sendInfoToFraudPrevention(any(CustomerDto.class)))
        .thenReturn(buildResponseFraudClientSuccessful());

    Mockito.when(bankClient.sendInfoToBank(any(PaymentRequestDto.class)))
        .thenReturn(buildResponseBankClientSuccessful());

    mockMvc.perform(post("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requetsJsonString))

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.50))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("COP"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("APPROVED"));
  }

  @Test
  void givenValidPaymentRequest_whenFraudValidationIsFailed_thenPaymentProcessIsFailed()
      throws Exception {

    String requetsJsonString = new ObjectMapper().writeValueAsString(buildPaymentRequestDto());

    Mockito.when(fraudPreventionClient.sendInfoToFraudPrevention(any(CustomerDto.class)))
        .thenReturn(buildResponseFraudClientFailed());

    mockMvc.perform(post("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requetsJsonString))

        .andExpect(status().is4xxClientError());


  }

  @Test
  void givenValidPaymentRequest_whenBankValidationIsFailed_thenPaymentProcessIsSuccess_AndRejectedStatus()
      throws Exception {

    String requetsJsonString = new ObjectMapper().writeValueAsString(buildPaymentRequestDto());

    Mockito.when(fraudPreventionClient.sendInfoToFraudPrevention(any(CustomerDto.class)))
        .thenReturn(buildResponseFraudClientSuccessful());

    Mockito.when(bankClient.sendInfoToBank(any(PaymentRequestDto.class)))
        .thenReturn(buildResponseBankClientFailed());

    mockMvc.perform(post("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requetsJsonString))

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.50))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("COP"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("REJECTED"));


  }

  @Test
  void givenValidPaymentRequest_whenCardTypeIsInvalid_thenPaymentProcessIsFailed()
      throws Exception {

    PaymentRequestDto paymentRequestDto = buildPaymentRequestDto();
    paymentRequestDto.getCardDto().setCardType("INVALID");

    String requetsJsonString = new ObjectMapper().writeValueAsString(paymentRequestDto);

    mockMvc.perform(post("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requetsJsonString))

        .andExpect(status().is4xxClientError());

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

  ResponseEntity<FraudClientResponseDto> buildResponseFraudClientSuccessful() {

    return ResponseEntity.ok(
        FraudClientResponseDto.builder().status(true).message("Validación exitosa").build());
  }

  ResponseEntity<BankClientResponseDto> buildResponseBankClientSuccessful() {

    return ResponseEntity.ok(
        BankClientResponseDto.builder().status(true).message("Validación exitosa").build());
  }

  ResponseEntity<FraudClientResponseDto> buildResponseFraudClientFailed() {

    return ResponseEntity.ok(
        FraudClientResponseDto.builder().status(false).message("Validación failed").build());
  }

  ResponseEntity<BankClientResponseDto> buildResponseBankClientFailed() {

    return ResponseEntity.ok(
        BankClientResponseDto.builder().status(false).message("Validación failed").build());
  }


}