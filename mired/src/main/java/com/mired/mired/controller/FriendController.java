package com.mired.mired.controller;
import com.mired.mired.dto.FriendDto;
import com.mired.mired.dto.FriendRequestDto;
import com.mired.mired.dto.UserDto;
import com.mired.mired.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // ✅ Obtener lista de amigos confirmados
    @GetMapping("/list")
    public ResponseEntity<List<FriendDto>> getFriendsList() {
        return ResponseEntity.ok(friendService.getFriendsOfCurrentUser());
    }

    // ✅ Buscar usuarios para enviar solicitud
    @GetMapping("/search")
    public ResponseEntity<List<FriendDto>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(friendService.searchUsers(query));
    }

    // ✅ Enviar solicitud de amistad
    @PostMapping("/request/{receiverId}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable Long receiverId) {
        friendService.sendFriendRequest(receiverId);
        return ResponseEntity.ok().build();
    }

    // ✅ Aceptar solicitud
    @PostMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long requestId) {
        friendService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // ✅ Rechazar solicitud
    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable Long requestId) {
        friendService.rejectFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // ✅ Eliminar amigo
    @DeleteMapping("/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable Long friendId) {
        friendService.removeFriend(friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/requests/received")
    public ResponseEntity<List<FriendRequestDto>> getReceivedFriendRequests() {
        return ResponseEntity.ok(friendService.getPendingRequestsForCurrentUser());
    }

}
