import { FriendRequestDto } from '../interfaces/FriendTypes';

interface Props {
  request: FriendRequestDto;
  onAccept: (id: number) => void;
  onReject: (id: number) => void;
}

export default function FriendRequestCard({ request, onAccept, onReject }: Props) {
  return (
    <li className="list-group-item d-flex justify-content-between align-items-center">
      {request.senderEmail}
      <div>
        <button className="btn btn-sm btn-success me-2" onClick={() => onAccept(request.requestId)}>Aceptar</button>
        <button className="btn btn-sm btn-danger" onClick={() => onReject(request.requestId)}>Rechazar</button>
      </div>
    </li>
  );
}
