package com.atm.atmapp.mapper;

import com.atm.atmapp.dto.AccountDto;
import com.atm.atmapp.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
