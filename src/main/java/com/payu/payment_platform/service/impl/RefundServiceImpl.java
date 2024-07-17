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
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.mapper.CardMapper;
import com.payu.payment_platform.mapper.CustomerMapper;
import com.payu.payment_platform.mapper.PaymentMapper;
import com.payu.payment_platform.repository.PaymentRepository;
import com.payu.payment_platform.service.BankService;
import com.payu.payment_platform.service.RefundService;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public PaymentResponseDto fullPaymentRefund(Long paymentId)
      throws PaymentPlatformException {

    log.info("Validate payment to refund");
    Payment payment = validatePayment(paymentRepository.findById(paymentId));

    PaymentRequestDto paymentRequestDto = PaymentMapper.INSTANCE.paymentToPaymentRequestDto(payment);

    CardDto cardDto = CardMapper.INSTANCE.cardToCardDto(payment.getCustomer().getCards().get(0));
    CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto(payment.getCustomer());

    paymentRequestDto.setCardDto(cardDto);
    paymentRequestDto.setCustomerDto(customerDto);

    log.info("Sending refund request to bank");
    bankService.sendInfoToBank(paymentRequestDto);

    payment.setStatus(PaymentStatus.REFUNDED.toString());
    payment.setUpdatedAt(LocalDateTime.now());

    log.info("Saving payment refund");
    paymentRepository.save(payment);

    return PaymentMapper.INSTANCE.paymentResponsetDtoToPayment(payment);
  }

  private static Payment validatePayment(Optional<Payment> paymentOptional)
      throws PaymentPlatformException {
    log.info("Consulting payment");
    if (paymentOptional.isEmpty()) {
      throw new PaymentPlatformException(NotificationCode.PAYMENT_NOT_FOUND);
    }

    Payment payment = paymentOptional.get();

    log.info("Validating payment status");
    if (!PaymentStatus.APPROVED.toString().equals(payment.getStatus())) {
      throw new PaymentPlatformException(NotificationCode.REFUND_NOT_ALLOWED);
    }
    return payment;
  }

}