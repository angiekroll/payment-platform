/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.service.impl;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.external.apis.bank.BankClient;
import com.payu.payment_platform.external.apis.bank.dto.BankClientResponseDto;
import com.payu.payment_platform.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Slf4j
@Service
public class BankServiceImpl implements BankService {

  private final BankClient bankClient;

  public BankServiceImpl(BankClient bankClient) {
    this.bankClient = bankClient;
  }

  @Override
  public Boolean sendInfoToBank(PaymentRequestDto paymentRequestDto) {
    try {
      log.info("Calling bank external API");
      ResponseEntity<BankClientResponseDto> bankClientResponseDto = bankClient.sendInfoToBank(
          paymentRequestDto);

      if (bankClientResponseDto.getStatusCode() != HttpStatus.OK) {
        log.error("Error getting response from bank external API");
        throw new PaymentPlatformException(NotificationCode.ERROR_GETTING_RESPONSE_BANK_SERVICE);
      }

      if (!bankClientResponseDto.getBody().getStatus()) {
        return false;
      }

    } catch (Exception e) {
      log.error("Error in feign client: " + e.getMessage());
    }
    return Boolean.TRUE;
  }
}