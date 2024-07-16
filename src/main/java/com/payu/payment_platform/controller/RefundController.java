/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.controller;

import com.payu.payment_platform.constans.ResourceMapping;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.service.RefundService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@RestController
@RequestMapping(ResourceMapping.PAYMENTS)
@Validated
public class RefundController {

  private final RefundService refundService;

  public RefundController(RefundService refundService) {
    this.refundService = refundService;
  }


  @Operation(summary = "Full refund method")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponseDto.class))
      }),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
  })

  @PostMapping("/{paymentId}")
  public ResponseEntity<PaymentResponseDto> fullRefund(
      @PathVariable @NotNull @Positive Long paymentId)
      throws PaymentPlatformException {

    return ResponseEntity.ok(refundService.fullPaymentRefund(paymentId));

  }


}