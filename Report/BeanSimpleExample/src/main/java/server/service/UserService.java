package server.service;

import server.model.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> findUserByName (String name);
}
