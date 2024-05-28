package ramanda.ajisaka.asyraf.bsi.backend.test.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Transaction;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.TransactionType;
import ramanda.ajisaka.asyraf.bsi.backend.test.repository.TransactionRepository;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    public TransactionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeposit() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountService.getAccountByAccountNumber("12345")).thenReturn(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(500));
        transaction.setTransactionType(TransactionType.DEPOSIT);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setId(1L);
            return savedTransaction;
        });

        Transaction savedTransaction = transactionService.deposit("12345", BigDecimal.valueOf(500));

        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
        assertEquals(BigDecimal.valueOf(500), savedTransaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, savedTransaction.getTransactionType());
    }

    @Test
    public void testWithdraw() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountService.getAccountByAccountNumber("12345")).thenReturn(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(500));
        transaction.setTransactionType(TransactionType.WITHDRAWAL);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setId(1L);
            return savedTransaction;
        });

        Transaction savedTransaction = transactionService.withdraw("12345", BigDecimal.valueOf(500));

        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        assertEquals(BigDecimal.valueOf(500), savedTransaction.getAmount());
        assertEquals(TransactionType.WITHDRAWAL, savedTransaction.getTransactionType());
    }
}