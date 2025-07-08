package com.atm.atmapp.controller;

import com.atm.atmapp.dto.TransactionDto;
import com.atm.atmapp.iaccountService.ITransectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private ITransectionService iTransectionService;
    @PostMapping("/{acountId}")
    public List<TransactionDto> getAllTransactionByID(@PathVariable  Long accountId){
        return iTransectionService.getAllTransactionByID(accountId);
    }
}
