/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.external.apis.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankClientResponseDto {

  private Boolean status;
  private String message;

}