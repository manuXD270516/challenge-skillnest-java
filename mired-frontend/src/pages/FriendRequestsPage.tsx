// src/pages/FriendRequestsPage.tsx

import { useEffect, useState } from 'react';
import { fetchReceivedRequests, acceptFriendRequest, rejectFriendRequest } from '../services/friendService';
import { FriendRequestDto } from '../interfaces/FriendTypes';
import { Button, Container, Alert, Spinner } from 'react-bootstrap';

const FriendRequestsPage = () => {
  const [requests, setRequests] = useState<FriendRequestDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const loadRequests = async () => {
    try {
      const data = await fetchReceivedRequests();
      setRequests(data);
    } catch {
      setError("Error al cargar las solicitudes.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadRequests();
  }, []);

  const handleRespond = async (requestId: number, action: 'accept' | 'reject') => {
    try {
      if (action === 'accept') {
        await acceptFriendRequest(requestId);
      } else {
        await rejectFriendRequest(requestId);
      }
      setRequests(prev => prev.filter(r => r.requestId !== requestId));
    } catch {
      alert("Error al responder la solicitud.");
    }
  };
  

  return (
    <Container className="mt-4">
      <h3>Solicitudes recibidas</h3>
      {loading && <Spinner animation="border" />}
      {error && <Alert variant="danger">{error}</Alert>}
      {requests.length === 0 && !loading && <Alert variant="info">No tienes solicitudes pendientes.</Alert>}
      {requests.map(req => (
        <div key={req.requestId} className="d-flex justify-content-between align-items-center border p-3 my-2 rounded bg-white">
          <span>{req.senderEmail}</span>
          <div className="d-flex gap-2">
            <Button variant="success" onClick={() => handleRespond(req.requestId, 'accept')}>Aceptar</Button>
            <Button variant="danger" onClick={() => handleRespond(req.requestId, 'reject')}>Rechazar</Button>
          </div>
        </div>
      ))}
    </Container>
  );
};

export default FriendRequestsPage;
