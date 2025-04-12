import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPublicationById, updatePublication } from "../services/publicationService";
import { Publication } from "../interfaces/Publication";
import { Button, Container, Form, Alert } from "react-bootstrap";

const EditPublication = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [form, setForm] = useState<Publication>({
    title: "",
    description: "",
    category: "",
    imageUrl: "",
  });
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;

    getPublicationById(Number(id))
      .then(data => setForm(data))
      .catch(() => setError("No se pudo cargar la publicación para editar."));
  }, [id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;

    try {
      await updatePublication(Number(id), form);
      navigate(`/publications/${id}`);
    } catch (err) {
      setError("No se pudo actualizar la publicación.");
    }
  };

  return (
    <Container className="mt-5" style={{ maxWidth: "600px" }}>
      <h2 className="mb-4 text-center">Editar Publicación</h2>
      {error && <Alert variant="danger">{error}</Alert>}
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="title">
          <Form.Label>Título</Form.Label>
          <Form.Control
            type="text"
            name="title"
            value={form.title}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="description">
          <Form.Label>Descripción</Form.Label>
          <Form.Control
            as="textarea"
            rows={4}
            name="description"
            value={form.description}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="category">
          <Form.Label>Categoría</Form.Label>
          <Form.Control
            type="text"
            name="category"
            value={form.category}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="imageUrl">
          <Form.Label>URL de Imagen (opcional)</Form.Label>
          <Form.Control
            type="text"
            name="imageUrl"
            value={form.imageUrl}
            onChange={handleChange}
          />
        </Form.Group>

        <div className="d-grid">
          <Button type="submit" variant="dark">
            Guardar Cambios
          </Button>
        </div>
      </Form>
    </Container>
  );
};

export default EditPublication;
