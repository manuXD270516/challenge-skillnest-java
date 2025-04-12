package com.mired.mired.dto;
import com.mired.mired.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDto extends UserDto {
    private Role role;
}