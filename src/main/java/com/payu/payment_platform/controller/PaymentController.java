/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.controller;

import com.payu.payment_platform.constans.ResourceMapping;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.service.PaymentService;
import com.payu.payment_platform.utils.ValidationUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }


  @Operation(summary = "Process a credit card payment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponseDto.class))
      }),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
  })

  @PostMapping
  public ResponseEntity<PaymentResponseDto> processingPayment(
      @Valid @RequestBody PaymentRequestDto paymentRequestDto, BindingResult resultErrors)
      throws PaymentPlatformException {

    ValidationUtils.validateInputFields(resultErrors);

    return ResponseEntity.ok(paymentService.processingPayment(paymentRequestDto));

  }

}