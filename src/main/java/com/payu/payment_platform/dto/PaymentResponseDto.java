/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentResponseDto {

  private String id;
  private BigDecimal amount;
  private String currency;
  private String status;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime createdAt;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime updatedAt;
  private String description;

}