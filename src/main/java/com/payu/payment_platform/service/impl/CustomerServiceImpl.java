/**
 * Copyright 2024, Company. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.service.impl;

import com.payu.payment_platform.constans.NotificationCode;
import com.payu.payment_platform.domain.Card;
import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.dto.CustomerDto;
import com.payu.payment_platform.exception.PaymentPlatformException;
import com.payu.payment_platform.mapper.CardMapper;
import com.payu.payment_platform.mapper.CustomerMapper;
import com.payu.payment_platform.repository.CustomerRepository;
import com.payu.payment_platform.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer validateCustomerExistence(String type, String documentNumber)
      throws PaymentPlatformException {

    log.info("Validating customer existence");
    log.debug("Validating customer existence with type: {} and document number: {}", type,
        documentNumber);
    try {

      return customerRepository.findByIdentificationTypeAndIdentification(type,
          documentNumber);

    } catch (Exception e) {
      log.error("Error validating customer existence.");
      throw new PaymentPlatformException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  public Customer save(CustomerDto customerDto) throws PaymentPlatformException {
    try {
      Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);
      Card card = CardMapper.INSTANCE.cardDtoToCard(customerDto.getCardsDto());

      card.setCustomer(customer);
      customer.setCards(List.of(card));

      log.info("Saving customer");

      return customerRepository.save(customer);

    } catch (Exception e) {
      log.error("Error Saving customer.");
      throw new PaymentPlatformException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }


}