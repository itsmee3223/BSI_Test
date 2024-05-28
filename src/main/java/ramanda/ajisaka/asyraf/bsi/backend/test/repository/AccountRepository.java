package ramanda.ajisaka.asyraf.bsi.backend.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
}