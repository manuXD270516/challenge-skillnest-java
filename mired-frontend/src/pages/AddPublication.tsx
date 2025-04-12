import { useState, FormEvent } from "react";
import { Container, Form, Button, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { createPublication } from "../services/publicationService";

const AddPublication = () => {
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("");
  const [description, setDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const getDefaultImageUrl = () => {
    const randomSeed = Math.floor(Math.random() * 1000);
    return `https://picsum.photos/seed/${randomSeed}/800/400`;
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    const postPayload = {
      title,
      category,
      description,
      imageUrl: imageUrl.trim() !== "" ? imageUrl : getDefaultImageUrl(),
    };

    try {
      await createPublication(postPayload);
      navigate("/");
    } catch (err: any) {
      setError(err.response?.data?.message || "Error al crear la publicación");
    }
  };

  return (
    <Container className="mt-5" style={{ maxWidth: "700px" }}>
      <h2 className="mb-4 text-center">Nueva publicación</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Título</Form.Label>
          <Form.Control
            type="text"
            placeholder="Escribe el título"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Categoría</Form.Label>
          <Form.Control
            type="text"
            placeholder="Ej: Tecnología, Estilo de vida..."
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Descripción</Form.Label>
          <Form.Control
            as="textarea"
            rows={4}
            placeholder="Escribe el contenido"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>URL de la imagen (opcional)</Form.Label>
          <Form.Control
            type="text"
            placeholder="https://..."
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
          />
        </Form.Group>

        {error && <Alert variant="danger">{error}</Alert>}

        <div className="text-center">
          <Button variant="dark" type="submit">
            Publicar
          </Button>
        </div>
      </Form>
    </Container>
  );
};

export default AddPublication;
