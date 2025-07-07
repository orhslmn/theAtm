package com.atm.atmapp.iaccountService;

import com.atm.atmapp.dto.TransactionDto;
import com.atm.atmapp.entity.Transaction;

import java.util.List;

public interface ITransectionService {
void saveTransaction(TransactionDto transactionDto);
    List<TransactionDto> getAllTransactionByID(Long accountId);

}

