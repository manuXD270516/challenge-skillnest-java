package com.mired.mired.dto;

import com.mired.mired.model.FriendRequest;
import lombok.Data;

@Data
public class FriendRequestDto {

    private Long requestId;
    private Long senderId;
    private String senderEmail;

    public static FriendRequestDto fromEntity(FriendRequest request) {
        FriendRequestDto dto = new FriendRequestDto();
        dto.setRequestId(request.getId());
        dto.setSenderId(request.getSender().getId());
        dto.setSenderEmail(request.getSender().getEmail());
        return dto;
    }
}
