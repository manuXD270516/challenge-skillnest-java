import { useEffect, useState } from 'react';
import { searchUsers, sendFriendRequest } from '../services/friendService';
import { FriendDto } from '../interfaces/FriendTypes';

export default function UserSearchBar() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<FriendDto[]>([]);

  useEffect(() => {
    const fetch = async () => {
      if (query.length >= 3) {
        const res = await searchUsers(query);
        setResults(res);
      } else {
        setResults([]);
      }
    };
    fetch();
  }, [query]);

  const handleRequest = async (id: number) => {
    await sendFriendRequest(id);
    setResults(results.filter(r => r.id !== id));
  };

  return (
    <div className="mb-3">
      <input
        type="text"
        className="form-control"
        placeholder="Buscar usuarios por email..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      {results.length > 0 && (
        <ul className="list-group mt-2">
          {results.map((user) => (
            <li key={user.id} className="list-group-item d-flex justify-content-between">
              {user.email}
              <button className="btn btn-sm btn-primary" onClick={() => handleRequest(user.id)}>Solicitar amistad</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
