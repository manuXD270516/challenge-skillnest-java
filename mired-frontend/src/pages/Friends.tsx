import { useEffect, useState } from 'react';
import { fetchFriendList, removeFriend } from '../services/friendService';
import { FriendDto } from '../interfaces/FriendTypes';

export default function Friends() {
  const [friends, setFriends] = useState<FriendDto[]>([]);

  const loadFriends = async () => {
    const res = await fetchFriendList();
    setFriends(res);
  };

  const handleRemove = async (id: number) => {
    await removeFriend(id);
    loadFriends();
  };

  useEffect(() => {
    loadFriends();
  }, []);

  return (
    <div className="container mt-4">
      <h3>Amigos confirmados</h3>
      <ul className="list-group">
        {friends.map((f) => (
          <li className="list-group-item d-flex justify-content-between" key={f.id}>
            {f.email}
            <button className="btn btn-sm btn-danger" onClick={() => handleRemove(f.id)}>Eliminar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
