/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.service.impl;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.constans.PaymentStatus;
import com.payu.payment_platform.domain.Payment;
import com.payu.payment_platform.dto.CardDto;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.dto.RefundRequestDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.mapper.CardMapper;
import com.payu.payment_platform.mapper.CustomerMapper;
import com.payu.payment_platform.mapper.PaymentMapper;
import com.payu.payment_platform.repository.PaymentRepository;
import com.payu.payment_platform.service.BankService;
import com.payu.payment_platform.service.RefundService;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Slf4j
@Service
public class RefundServiceImpl implements RefundService {

  private PaymentRepository paymentRepository;

  private BankService bankService;


  public RefundServiceImpl(PaymentRepository paymentRepository, BankService bankService) {
    this.paymentRepository = paymentRepository;
    this.bankService = bankService;
  }

  @Override
  public PaymentResponseDto fullPaymentRefund(RefundRequestDto refundRequestDto)
      throws PaymentPlatformException {

    Optional<Payment> paymentOptional = paymentRepository.findById(
        refundRequestDto.getTransactionId());

    if (paymentOptional.isEmpty()) {
      throw new PaymentPlatformException(NotificationCode.PAYMENT_NOT_FOUND);
    }

    Payment payment = paymentOptional.get();

    PaymentRequestDto paymentRequestDto = PaymentMapper.INSTANCE.paymentToPaymentRequestDto(payment);

    CardDto cardDto = CardMapper.INSTANCE.cardToCardDto(payment.getCustomer().getCards().get(0));
    CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto(payment.getCustomer());

    paymentRequestDto.setCardDto(cardDto);
    paymentRequestDto.setCustomerDto(customerDto);

    bankService.sendInfoToBank(paymentRequestDto);

    payment.setStatus(PaymentStatus.REFUNDED.toString());

    paymentRepository.save(payment);

    return PaymentMapper.INSTANCE.paymentResponsetDtoToPayment(payment);
  }

}