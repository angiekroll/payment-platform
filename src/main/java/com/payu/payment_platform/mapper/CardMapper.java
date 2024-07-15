/**
 * Copyright 2024. All rights reserved Date: 15/07/24
 */
package com.payu.payment_platform.mapper;

import com.payu.payment_platform.domain.Card;
import com.payu.payment_platform.domain.Customer;
import com.payu.payment_platform.dto.CardDto;
import com.payu.payment_platform.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author angiekroll@gmail.com - Ángela Carolina Castillo Rodríguez.
 * @version - 1.0.0
 * @since - 1.0.0
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

  CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

  Card cardDtoToCard(CardDto cardDto);

  CardDto cardToCardDto(Card card);

}