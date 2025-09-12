package school.sorokin.javacore.spring_core_homework_base.Services;

import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.FindUserByIdException;
import school.sorokin.javacore.spring_core_homework_base.Exception.UserIncorrectEnter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class UserService {
    private final Set<User> allUsers = new HashSet<>();
    private final Set<String> logins = new TreeSet<>();
    private Long idIncrement = 1L;

    public User createUser(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new UserIncorrectEnter("Недопустимо пустое значение или поле состоящее из пробелов");
        }
        String checkLogin = login.trim();
        if (!logins.contains(checkLogin)) {
            logins.add(checkLogin);
        } else {
            throw new UserIncorrectEnter("Такой логин уже существует");
        }
        User newUser = new User(idIncrement++, checkLogin);
        allUsers.add(newUser);
        return newUser;
    }

    public User findUserById(Long id) {
        Optional<User> user = allUsers.stream().filter(n -> n.getId().equals(id)).findAny();
        if (user.isPresent()) {
            return user.get();
        }
        throw new FindUserByIdException("Такого пользователя нет");
    }

    public void getAllUsers() {
        System.out.println(allUsers.toString());
    }

}