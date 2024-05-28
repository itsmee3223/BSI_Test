package ramanda.ajisaka.asyraf.bsi.backend.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "accountNumber")
    private String accountNumber;

    @NotNull
    @Column(name = "accountTitle")
    private String accountTitle;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;
}