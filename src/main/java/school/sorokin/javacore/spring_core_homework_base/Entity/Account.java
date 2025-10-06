package school.sorokin.javacore.spring_core_homework_base.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private BigDecimal moneyAmount;

    public Account(User user, BigDecimal moneyAmount) {
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public Account(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}