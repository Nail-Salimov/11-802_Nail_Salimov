package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.model.User;
import server.model.UserDto;
import server.repository.UserRepository;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDto> findUserByName(String name) {
        Optional<User> optionalUser = userRepository.findUserByName(name);
        return optionalUser.map(UserDto::getDto);
    }
}
