/**
 * Copyright 2024, Company. All rights reserved Date: 16/07/24
 */
package com.payu.payment_platform.config;

import feign.Retryer;
import feign.Retryer.Default;
import org.springframework.context.annotation.Bean;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public class FeignConfig {

  @Bean
  public Retryer feignRetryer() {
    return new Default(1000L, 2, 3);
  }

}