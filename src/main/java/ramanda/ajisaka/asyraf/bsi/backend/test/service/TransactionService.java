package ramanda.ajisaka.asyraf.bsi.backend.test.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Transaction;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.TransactionType;
import ramanda.ajisaka.asyraf.bsi.backend.test.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        accountService.addAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        account.setBalance(account.getBalance().subtract(amount));
        accountService.addAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}