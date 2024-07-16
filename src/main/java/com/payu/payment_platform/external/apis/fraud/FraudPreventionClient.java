/**
 * Copyright 2024. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.external.apis.fraud;

import com.payu.payment_platform.config.FeignConfig;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.external.apis.fraud.dto.FraudClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@FeignClient(name = "fraud-api", url = "${external-apis.fraud-api.url}", configuration = FeignConfig.class)
public interface FraudPreventionClient {

  @PostMapping()
  ResponseEntity<FraudClientResponseDto> sendInfoToFraudPrevention(CustomerDto customerDto);

}