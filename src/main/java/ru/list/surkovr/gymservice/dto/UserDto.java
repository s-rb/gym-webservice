package ru.list.surkovr.gymservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    @JsonIgnore
    private String password;

    @Builder
    public UserDto(Long id, String username, String firstName, String lastName, String middleName,
                   String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
    }
}
