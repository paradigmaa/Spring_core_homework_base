package school.sorokin.javacore.spring_core_homework_base.Entity;

import java.util.ArrayList;
import java.util.List;


public class User {
    private final Long id;

    private final String login;

    private List<Account> accountList;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public User(Long id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>(accountList);

    }

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public void addAccountList(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}