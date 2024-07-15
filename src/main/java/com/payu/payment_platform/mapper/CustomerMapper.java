/**
 * Copyright 2024. All rights reserved Date: 14/07/24
 */
package com.payu.payment_platform.mapper;

import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  Customer customerDtoToCustomer(CustomerDto customerDto);

  CustomerDto customerToCustomerDto(Customer customer);

}