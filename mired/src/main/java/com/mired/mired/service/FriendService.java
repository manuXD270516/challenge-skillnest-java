package com.mired.mired.service;

import com.mired.mired.dto.FriendDto;
import com.mired.mired.dto.FriendRequestDto;
import com.mired.mired.model.FriendRequest;
import com.mired.mired.model.User;
import com.mired.mired.repository.FriendRequestRepository;
import com.mired.mired.repository.UserRepository;
import com.mired.mired.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    public List<FriendDto> searchUsers(String query) {
        Long currentUserId = getAuthenticatedUser().getId();
        return userRepository.findByEmailContainingIgnoreCase(query).stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .map(FriendDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FriendDto> getFriendsOfCurrentUser() {
        Long currentUserId = getAuthenticatedUser().getId();
        return friendRequestRepository.findConfirmedFriends(currentUserId).stream()
                .map(FriendDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void sendFriendRequest(Long receiverId) {
        User sender = getAuthenticatedUser();
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));

        // Evita duplicados
        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new RuntimeException("Ya se ha enviado una solicitud");
        }

        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setAccepted(false);

        friendRequestRepository.save(request);
    }

    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!request.getReceiver().getId().equals(getAuthenticatedUser().getId())) {
            throw new RuntimeException("No autorizado");
        }

        request.setAccepted(true);
        friendRequestRepository.save(request);
    }

    public void rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!request.getReceiver().getId().equals(getAuthenticatedUser().getId())) {
            throw new RuntimeException("..lNo autorizado");
        }

        friendRequestRepository.delete(request);
    }

    public void removeFriend(Long friendId) {
        Long currentUserId = getAuthenticatedUser().getId();
        friendRequestRepository.deleteFriendship(currentUserId, friendId);
    }

    private User getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserDetailsImpl) auth.getPrincipal();
        return principal.getUserEntity();
    }

    public List<FriendRequestDto> getPendingRequestsForCurrentUser() {
        User currentUser = getAuthenticatedUser();
        return friendRequestRepository.findPendingRequestsByReceiver(currentUser.getId()).stream()
                .map(FriendRequestDto::fromEntity)
                .collect(Collectors.toList());
    }

}