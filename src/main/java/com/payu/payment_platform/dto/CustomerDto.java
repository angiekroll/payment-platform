/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CustomerDto {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @NotBlank(message = "The number identification is required.")
  @Size(max = 5, message = "The number identification field cannot be longer than 50 characters.")
  private String identificationType;

  @NotBlank(message = "The number identification is required.")
  @Size(max = 20, message = "The number identification field cannot be longer than 50 characters.")
  private String identification;

  @Column(nullable = false)
  @Email(message = "The email must be a valid email address.")
  private String email;

  private String address;

  private CardDto cardsDto;

}