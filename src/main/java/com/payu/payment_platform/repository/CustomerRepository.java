/**
 * Copyright 2024. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.repository;


import com.payu.payment_platform.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByIdentificationTypeAndIdentification (String type, String documentNumber);



}