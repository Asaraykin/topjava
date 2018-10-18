package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static AtomicInteger id = new AtomicInteger(0);

    private static Map<Integer, User> userMap = new ConcurrentHashMap<>();

    {
        save(new User(null, "guest", "sss", "0", Role.ROLE_USER));
        save(new User(null, "admin", "sss", "0", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userMap.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (!user.isNew()) {
            return userMap.computeIfPresent(user.getId(), (userId, oldValue) -> user);
        } else {
            userMap.put(id.incrementAndGet(), user);
            return user;
        }
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values().stream()
                .sorted((Comparator.comparing(AbstractNamedEntity::getName)))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Optional<User> user = userMap.values().stream()
                .filter(user1 -> user1.getEmail().equals(email))
                .findFirst();
        return user.get();
    }
}
