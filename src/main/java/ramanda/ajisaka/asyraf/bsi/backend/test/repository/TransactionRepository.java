package ramanda.ajisaka.asyraf.bsi.backend.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ramanda.ajisaka.asyraf.bsi.backend.test.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}