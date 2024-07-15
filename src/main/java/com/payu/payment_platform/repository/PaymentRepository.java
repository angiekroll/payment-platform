/**
 * Copyright 2024. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.repository;

import com.payu.payment_platform.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}