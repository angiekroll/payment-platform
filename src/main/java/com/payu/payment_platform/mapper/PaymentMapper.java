/**
 * Copyright 2024. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.mapper;

import com.payu.payment_platform.domain.Payment;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

  PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

  Payment paymentRequestDtoToPayment(PaymentRequestDto paymentRequestDto);

  PaymentResponseDto paymentResponsetDtoToPayment(Payment payment);

  PaymentRequestDto paymentToPaymentRequestDto(Payment payment);

}