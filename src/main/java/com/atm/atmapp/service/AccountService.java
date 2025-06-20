package com.atm.atmapp.service;

import com.atm.atmapp.dto.AccountDto;
import com.atm.atmapp.dto.LoginDto;
import com.atm.atmapp.entity.Account;
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
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Yetersiz bakiye");
        }

        account.setBalance(account.getBalance() - amount);
        Account updated = accountRepository.save(account);
        return accountMapper.toDto(updated);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));

        account.setBalance(account.getBalance() + amount);
        Account updated = accountRepository.save(account);
        return accountMapper.toDto(updated);
    }

    @Override
    public Double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));
        return account.getBalance();
    }

    @Override
    public AccountDto login(LoginDto loginDto) {
        Account account = accountRepository.findByCardNumber(loginDto.getCardNumber())
                .orElseThrow(() -> new RuntimeException("Card number not found"));
        return accountMapper.toDto(account);
    }
}
