package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;

import java.util.*;

@Service
public class UserService {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User createUser(User user) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.beginTransaction();
            session.persist(user);
            transaction.commit();
            session.close();
            return user;
        }
    };

    public User findUserById(Long id) {
        Session session = sessionFactory.openSession();
        User finedUser = session.find(User.class, id);
        session.close();
        return finedUser;
    }

    public void getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User>users = session.createQuery("select s from User s",User.class).list();
        session.close();
    }

}