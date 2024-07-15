/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentRequestDto {

  private CustomerDto customerDto;

  private CardDto cardDto;

  @NotNull(message = "The amount is required.")
  private BigDecimal amount;

  @NotBlank(message = "The currency is required.")
  @Size(max = 3, message = "The currency field cannot be longer than 3 characters.")
  private String currency;

  private String installmentsCount;

  private String description;

}