/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CardDto {

  @NotNull(message = "The number card is required.")
  private String cardNumber;

  @NotBlank(message = "The card type is required.")
  @Enumerated(EnumType.STRING)
  private String cardType;

  @NotBlank(message = "The card type is required.")
  private String cvv;

  private String expirationDate;

  @NotBlank(message = "The card type is required.")
  private String cardHolder;

}