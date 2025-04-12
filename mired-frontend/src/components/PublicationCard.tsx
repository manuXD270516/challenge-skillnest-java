import { Card, Button } from "react-bootstrap";
import { Publication } from "../interfaces/Publication";
import { useNavigate } from "react-router-dom";
import { useSession } from "../context/SessionContext";

interface Props {
  publication: Publication;
  onDelete?: (id: number) => void;
}

export default function PublicationCard({ publication, onDelete }: Props) {
  const navigate = useNavigate();
  const { email: currentUserEmail } = useSession();

  const handleView = () => {
    navigate(`/publications/${publication.id}`);
  };

  const handleEdit = () => {
    navigate(`/edit-publication/${publication.id}`);
  };

  const handleDelete = () => {
    if (publication.id && confirm("¿Deseas eliminar esta publicación?")) {
      onDelete?.(publication.id);
    }
  };

  const isAuthor = publication.userEmail === currentUserEmail;

  return (
    <Card className="mb-4 shadow-sm">
      <Card.Header>{publication.userEmail || "Usuario anónimo"}</Card.Header>
      <Card.Body>
        <Card.Title>{publication.title}</Card.Title>
        <div className="d-flex gap-2">
          <Button size="sm" variant="outline-primary" onClick={handleView}>
            Ver más
          </Button>
          {isAuthor && (
            <>
              <Button size="sm" variant="outline-secondary" onClick={handleEdit}>
                Editar
              </Button>
              <Button size="sm" variant="outline-danger" onClick={handleDelete}>
                Eliminar
              </Button>
            </>
          )}
        </div>
      </Card.Body>
    </Card>
  );
}
