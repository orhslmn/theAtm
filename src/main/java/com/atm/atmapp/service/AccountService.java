package com.atm.atmapp.service;

import com.atm.atmapp.dto.AccountDto;
import com.atm.atmapp.dto.LoginDto;
import com.atm.atmapp.dto.TransactionDto;
import com.atm.atmapp.entity.Account;
import com.atm.atmapp.entity.Transaction;
import com.atm.atmapp.exeptions.CardNotFoundException;
import com.atm.atmapp.exeptions.InvalidPinException;
import com.atm.atmapp.iaccountService.IAccountService;
import com.atm.atmapp.mapper.AccountMapper;
import com.atm.atmapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionService transactionService;

    @Override
    public AccountDto createAccount(AccountDto dto) {
        Account account = accountMapper.toEntity(dto);
        account.setBalance(dto.getBalance() != null ? dto.getBalance() : 0.0);
        Account saved = accountRepository.save(account);
        return accountMapper.toDto(saved);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        TransactionDto dto=new TransactionDto();
        dto.setAccountId(account.getId());
        dto.setAmount(amount);
        dto.setType("withdraw");
        transactionService.saveTransaction(dto);
        Account updated = accountRepository.save(account);
        return accountMapper.toDto(updated);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        TransactionDto dto=new TransactionDto();
        dto.setAccountId(account.getId());
        dto.setAmount(amount);
        dto.setType("deposit");
        transactionService.saveTransaction(dto);
        Account updated = accountRepository.save(account);
        return accountMapper.toDto(updated);
    }

    @Override
    public Double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    @Override
    public AccountDto login(LoginDto loginDto) {
        Account account = accountRepository.findByCardNumber(loginDto.getCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Card number not found"));
        if (!account.getPin().equals(loginDto.getPin())) {
            throw new InvalidPinException("Invalid Pin");
        }

        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto transferAmount(Long senderId, Long receiverId, Double amount) {
        Account sender = accountRepository.findById(senderId).orElseThrow(() -> new RuntimeException("id not found"));
        Account receiver = accountRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("transfer id is not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance.");
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.save(sender);
        accountRepository.save(receiver);
        return accountMapper.toDto(sender);
    }

}
