package com.atm.atmapp.service;

import com.atm.atmapp.dto.TransactionDto;
import com.atm.atmapp.entity.Transaction;
import com.atm.atmapp.iaccountService.ITransectionService;
import com.atm.atmapp.mapper.TransactionMapper;
import com.atm.atmapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransectionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactionByID(Long accountId) {
        List<Transaction> transactionList = transactionRepository.findByAccountId(accountId);

        return transactionMapper.toListDto(transactionList);
    }
}
