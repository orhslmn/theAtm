package com.atm.atmapp.mapper;

import com.atm.atmapp.dto.TransactionDto;
import com.atm.atmapp.entity.Account;
import com.atm.atmapp.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "account.id", target = "accountId")
    TransactionDto toDto(Transaction transaction);

    @Mapping(source = "accountId", target = "account.id")
    Transaction toEntity(TransactionDto transactionDto);

    default Account mapAccountId(Long accountId) {
        Account a = new Account();
        a.setId(accountId);
        return a;
    }

    List<TransactionDto> toListDto(List<Transaction> transactionList);

}
