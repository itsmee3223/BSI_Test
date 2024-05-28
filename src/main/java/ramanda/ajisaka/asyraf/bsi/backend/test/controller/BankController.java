package ramanda.ajisaka.asyraf.bsi.backend.test.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Transaction;
import ramanda.ajisaka.asyraf.bsi.backend.test.service.AccountService;
import ramanda.ajisaka.asyraf.bsi.backend.test.service.TransactionService;

import java.math.BigDecimal;

@RestController
public class BankController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(path = "/test")
    public String test() {
        return "Controller is working!";
    }

    @PostMapping(path = "/accounts")
    public Account addAccount(@Valid @RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @GetMapping(path = "/accounts/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    @PostMapping(path = "/accounts/{accountNumber}/deposit")
    public Transaction deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return transactionService.deposit(accountNumber, amount);
    }

    @PostMapping(path = "/accounts/{accountNumber}/withdraw")
    public Transaction withdraw(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return transactionService.withdraw(accountNumber, amount);
    }
}