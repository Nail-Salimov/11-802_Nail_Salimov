package server.repository;

import server.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByName(String name);
}
