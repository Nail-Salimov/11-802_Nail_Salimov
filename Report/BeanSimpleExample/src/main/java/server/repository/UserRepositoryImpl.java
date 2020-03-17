package server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import server.model.User;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_FIND_USER = "SELECT * FROM test WHERE name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (row, number) ->
            User.builder()
            .name(row.getString("name"))
            .surname(row.getString("surname"))
            .build();

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserByName(String name) {
        try {
            User user =jdbcTemplate.queryForObject(SQL_FIND_USER, new Object[]{name}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
