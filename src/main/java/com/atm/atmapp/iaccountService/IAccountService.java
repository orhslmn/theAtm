package com.atm.atmapp.iaccountService;

import com.atm.atmapp.dto.AccountDto;
import com.atm.atmapp.dto.LoginDto;

public interface IAccountService {
    AccountDto createAccount(AccountDto dto);
    AccountDto getAccountById(Long id);
    AccountDto withdraw(Long id, Double amount);
    AccountDto deposit(Long id, Double amount);
    Double getBalance(Long id);
    AccountDto login(LoginDto loginDto);

}
