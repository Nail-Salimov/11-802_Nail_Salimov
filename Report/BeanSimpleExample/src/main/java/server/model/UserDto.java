package server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String surname;

    public static UserDto getDto(User user){
        return new UserDto(user.getName(), user.getSurname());
    }
}
