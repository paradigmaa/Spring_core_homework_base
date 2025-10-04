package school.sorokin.javacore.spring_core_homework_base.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    @ManyToOne
    @JoinColumn(name = "id")
    private User userId;

    @Column(name = "amount")
    private BigDecimal moneyAmount;

    public Account(Long id, User userId, BigDecimal moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public User getUserId() {
        return userId;
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
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
