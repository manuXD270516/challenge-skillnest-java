import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createPublication } from "../services/publicationService";

export default function PublicationForm() {
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("");
  const [description, setDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    await createPublication({ title, category, description, imageUrl });
    navigate("/");
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        className="form-control mb-2"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Título"
      />
      <input
        className="form-control mb-2"
        value={category}
        onChange={(e) => setCategory(e.target.value)}
        placeholder="Categoría"
      />
      <textarea
        className="form-control mb-2"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder="Descripción"
      />
      <input
        className="form-control mb-2"
        value={imageUrl}
        onChange={(e) => setImageUrl(e.target.value)}
        placeholder="URL de la imagen"
      />
      <button className="btn btn-success">Publicar</button>
    </form>
  );
}
