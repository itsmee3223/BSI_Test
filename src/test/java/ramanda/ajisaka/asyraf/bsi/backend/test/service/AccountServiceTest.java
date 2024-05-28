package ramanda.ajisaka.asyraf.bsi.backend.test.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;
import ramanda.ajisaka.asyraf.bsi.backend.test.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    public AccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAccount() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.save(account)).thenReturn(account);

        Account savedAccount = accountService.addAccount(account);

        assertEquals("12345", savedAccount.getAccountNumber());
        assertEquals("John Doe", savedAccount.getAccountTitle());
        assertEquals(BigDecimal.valueOf(1000), savedAccount.getBalance());
    }

    @Test
    public void testGetAccountByAccountNumber() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber("12345")).thenReturn(account);

        Account foundAccount = accountService.getAccountByAccountNumber("12345");

        assertEquals("12345", foundAccount.getAccountNumber());
        assertEquals("John Doe", foundAccount.getAccountTitle());
        assertEquals(BigDecimal.valueOf(1000), foundAccount.getBalance());
    }
}