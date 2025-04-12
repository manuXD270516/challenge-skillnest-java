package com.mired.mired.service;

import com.mired.mired.dto.FriendDto;
import com.mired.mired.dto.UserDto;
import com.mired.mired.model.User;
import com.mired.mired.repository.UserRepository;
import com.mired.mired.security.UserDetailsImpl;
import com.mired.mired.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<FriendDto> searchUsers(String query) {
        Long currentUserId = getAuthenticatedUserId();
        return userRepository.findByEmailContainingIgnoreCase(query)
                .stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .map(FriendDto::fromEntity)
                .collect(Collectors.toList());
    }

    private Long getAuthenticatedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserDetailsImpl) auth.getPrincipal();
        return principal.getUserEntity().getId();
    }
}
