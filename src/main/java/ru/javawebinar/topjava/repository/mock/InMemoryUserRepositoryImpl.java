package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.UsersUtil.getValues;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);

        if (!repository.containsKey(id)) {
            return false;
        }

        repository.remove(id);
        return true;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);

        if (!repository.containsKey(id)) {
            return null;
        }

        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        Optional<User> elem = getValues(repository).stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        return elem.orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = getValues(repository);
        userList.sort(Comparator.comparing(User::getName));
        return userList;
    }
}
