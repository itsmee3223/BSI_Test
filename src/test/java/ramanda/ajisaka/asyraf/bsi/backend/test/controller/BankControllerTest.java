package ramanda.ajisaka.asyraf.bsi.backend.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;
import ramanda.ajisaka.asyraf.bsi.backend.test.repository.AccountRepository;
import ramanda.ajisaka.asyraf.bsi.backend.test.repository.TransactionRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void testAddAccount() throws Exception {
        String accountJson = "{\"accountNumber\":\"12345\",\"accountTitle\":\"John Doe\",\"balance\":1000}";

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("12345"))
                .andExpect(jsonPath("$.accountTitle").value("John Doe"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void testDeposit() throws Exception {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));
        accountRepository.save(account);

        mockMvc.perform(post("/accounts/12345/deposit")
                        .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(500))
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"));

        Account updatedAccount = accountRepository.findByAccountNumber("12345");
        assertEquals(0, BigDecimal.valueOf(1500).compareTo(updatedAccount.getBalance()));
    }

    @Test
    void testWithdraw() throws Exception {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setAccountTitle("John Doe");
        account.setBalance(BigDecimal.valueOf(1000));
        accountRepository.save(account);

        mockMvc.perform(post("/accounts/12345/withdraw")
                        .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(500))
                .andExpect(jsonPath("$.transactionType").value("WITHDRAWAL"));

        Account updatedAccount = accountRepository.findByAccountNumber("12345");
        assertEquals(0, BigDecimal.valueOf(500).compareTo(updatedAccount.getBalance()));
    }
}