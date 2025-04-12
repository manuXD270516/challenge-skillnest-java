import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  getPublicationById,
  deletePublication,
} from "../services/publicationService";
import { Button, Container, Spinner, Alert } from "react-bootstrap";
import { useSession } from "../hooks/useSession";
import { Publication } from "../interfaces/Publication";

const PublicationDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { email: currentUserEmail, role } = useSession();

  const [publication, setPublication] = useState<Publication | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;

    getPublicationById(Number(id))
      .then((data) => setPublication(data))
      .catch(() => setError("No se pudo cargar la publicación."))
      .finally(() => setLoading(false));
  }, [id]);

  const handleDelete = async () => {
    if (!id) return;

    if (confirm("¿Estás seguro de que deseas eliminar esta publicación?")) {
      try {
        await deletePublication(Number(id));
        navigate("/"); // Redirigir a home
      } catch (err) {
        alert("Error al eliminar la publicación.");
      }
    }
  };

  if (loading) {
    return (
      <Container className="text-center mt-5">
        <Spinner animation="border" />
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="mt-5">
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  if (!publication) {
    return (
      <Container className="mt-5">
        <Alert variant="warning">Publicación no encontrada</Alert>
      </Container>
    );
  }

  const canChangeInformation = (role === "ADMIN") || currentUserEmail === publication.userEmail;

  return (
    <Container className="mt-5">
      <h2 className="mb-3">{publication.title}</h2>
      <img
        src={
          publication.imageUrl ||
          `https://picsum.photos/seed/${publication.id}/600/300`
        }
        alt="Imagen de la publicación"
        className="img-fluid mb-3"
        style={{ maxHeight: "400px", objectFit: "cover" }}
        onError={(e) => {
          (e.target as HTMLImageElement).src =
            "https://picsum.photos/600/300?grayscale";
        }}
      />
      <p>
        <strong>Categoría:</strong> {publication.category}
      </p>
      <p>
        <strong>Descripción:</strong>
      </p>
      <p>{publication.description}</p>

      {canChangeInformation && (
        <div className="mt-4 d-flex gap-3">
          <Button
            variant="primary"
            onClick={() => navigate(`/edit-publication/${publication.id}`)}
          >
            Editar
          </Button>
          <Button variant="danger" onClick={handleDelete}>
            Eliminar
          </Button>
        </div>
      )}
    </Container>
  );
};

export default PublicationDetail;
