package com.mired.mired.dto;

import com.mired.mired.model.User;
import lombok.Data;

@Data
public class FriendDto {
    private Long id;
    private String email;

    public static FriendDto fromEntity(User user) {
        FriendDto dto = new FriendDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }
}

