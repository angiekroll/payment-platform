/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.service.impl;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.external.apis.fraud.dto.FraudClientResponseDto;
import com.payu.payment_platform.external.apis.fraud.FraudPreventionClient;
import com.payu.payment_platform.service.FraudPreventionService;
import feign.FeignException;
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
public class FraudPreventionServiceImpl implements FraudPreventionService {

  private final FraudPreventionClient fraudPreventionClient;

  public FraudPreventionServiceImpl(FraudPreventionClient fraudPreventionClient) {
    this.fraudPreventionClient = fraudPreventionClient;
  }

  public void sendInfoToFraudPrevention(CustomerDto customerDto) throws PaymentPlatformException {
    try {
      log.info("Calling fraud external API");
      ResponseEntity<FraudClientResponseDto> fraudClientResponseDTO = fraudPreventionClient.sendInfoToFraudPrevention(
          customerDto);

      if (fraudClientResponseDTO.getStatusCode() != HttpStatus.OK) {
        log.error("Error getting response from fraud external API");
        throw new PaymentPlatformException(NotificationCode.ERROR_GETTING_RESPONSE_FRAUD_SERVICE);
      }

      if (!fraudClientResponseDTO.getBody().getStatus()) {
        throw new PaymentPlatformException(NotificationCode.REJECTED_BY_FRAUD);
      }

    } catch (FeignException e) {
      log.error("Error al llamar al servicio de fraude: " + e.getMessage());
      throw new PaymentPlatformException(
          NotificationCode.ERROR_FRAUD_SERVICE_UNAVAILABLE);
    }

  }

}