package com.absoft.springsample.repositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.absoft.springsample.entities.User;

import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    private static List<User> users = new ArrayList<>();
    private static int userCount = 0;

    /*
     * Intialize the users list with dummy values
     */
    static {
        users.add(new User(1, "Steve Rogers", "captain.america@gmail.com"));
        users.add(new User(2, "Thor Odinson", "mighty.thor@gmail.com"));
        users.add(new User(1, "Tony Stark", "iamironman@gmail.com"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
