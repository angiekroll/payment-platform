/**
 * Copyright 2024. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.external.apis.bank;

import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.external.apis.bank.dto.BankClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@FeignClient(name = "bank-api", url = "${external-apis.bank-api.url}")
public interface BankClient {

  @PostMapping("/bank")
  ResponseEntity<BankClientResponseDto> sendInfoToBank(PaymentRequestDto paymentRequestDto);

}