/**
 * Copyright 2024, Company. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.service.impl;

import com.payu.payment_platform.constans.CardType;
import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.constans.PaymentStatus;
import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.domain.Payment;
import com.payu.payment_platform.dto.PaymentRequestDto;
import com.payu.payment_platform.dto.PaymentResponseDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.mapper.PaymentMapper;
import com.payu.payment_platform.repository.PaymentRepository;
import com.payu.payment_platform.service.BankService;
import com.payu.payment_platform.service.CustomerService;
import com.payu.payment_platform.service.FraudPreventionService;
import com.payu.payment_platform.service.PaymentService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

  private final FraudPreventionService fraudPreventionService;

  private final CustomerService customerService;

  private final PaymentRepository paymentRepository;

  private final BankService bankService;


  public PaymentServiceImpl(
      FraudPreventionService fraudPreventionService, CustomerService customerService,
      PaymentRepository paymentRepository, BankService bankService
  ) {
    this.fraudPreventionService = fraudPreventionService;
    this.customerService = customerService;
    this.paymentRepository = paymentRepository;
    this.bankService = bankService;
  }


  public PaymentResponseDto processingPayment(PaymentRequestDto paymentRequestDto)
      throws PaymentPlatformException {

    validateCardType(paymentRequestDto.getCardDto().getCardType());

    fraudPreventionService.sendInfoToFraudPrevention(paymentRequestDto.getCustomerDto());

    Customer customer = customerService.validateCustomerExistence(
        paymentRequestDto.getCustomerDto().getIdentificationType(),
        paymentRequestDto.getCustomerDto().getIdentification());

    if (customer == null) {
      paymentRequestDto.getCustomerDto().setCardsDto(paymentRequestDto.getCardDto());
      customer = customerService.save(paymentRequestDto.getCustomerDto());
    }

    Payment payment = paymentRepository.save(buildPayment(paymentRequestDto, customer));

    PaymentStatus newStatus =
        Boolean.TRUE.equals(bankService.sendInfoToBank(paymentRequestDto)) ? PaymentStatus.APPROVED
            : PaymentStatus.REJECTED;

    payment.setStatus(newStatus.toString());

    paymentRepository.save(payment);

    return PaymentMapper.INSTANCE.paymentResponsetDtoToPayment(payment);

  }

  private void validateCardType(String cardType) throws PaymentPlatformException {

    log.info("Processing payment");
    if (!CardType.CREDIT.toString().equalsIgnoreCase(cardType.toUpperCase())) {
      log.error("Invalid card type");
      throw new PaymentPlatformException(NotificationCode.INVALID_CARD_TYPE);
    }

  }


  private Payment buildPayment(PaymentRequestDto paymentRequestDto, Customer customer) {

    Payment payment = PaymentMapper.INSTANCE.paymentRequestDtoToPayment(paymentRequestDto);
    payment.setCustomer(customer);
    payment.setCreatedAt(LocalDateTime.now());
    payment.setStatus(PaymentStatus.PENDING.toString());

    return payment;

  }


}