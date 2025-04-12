package com.mired.mired.repository;

import com.mired.mired.model.FriendRequest;
import com.mired.mired.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

    // Amigos confirmados donde el usuario es emisor o receptor
    @Query("SELECT fr.sender FROM FriendRequest fr WHERE fr.receiver.id = :userId AND fr.accepted = true " +
            "UNION " +
            "SELECT fr.receiver FROM FriendRequest fr WHERE fr.sender.id = :userId AND fr.accepted = true")
    List<User> findConfirmedFriends(Long userId);

    // Eliminar amistad en ambos sentidos
    @Query("DELETE FROM FriendRequest fr WHERE " +
            "(fr.sender.id = :userId AND fr.receiver.id = :friendId) OR " +
            "(fr.sender.id = :friendId AND fr.receiver.id = :userId)")
    void deleteFriendship(Long userId, Long friendId);

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver.id = :receiverId AND fr.accepted = false")
    List<FriendRequest> findPendingRequestsByReceiver(Long receiverId);

}
