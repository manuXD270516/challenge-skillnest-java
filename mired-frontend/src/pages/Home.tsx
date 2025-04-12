// src/pages/Home.tsx
import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import PublicationCard from "../components/PublicationCard";
import { Publication } from "../interfaces/Publication";
import { fetchPublications, deletePublication } from "../services/publicationService";

export default function Home() {
  const [publications, setPublications] = useState<Publication[]>([]);

  const loadPublications = async () => {
    const data = await fetchPublications();
    setPublications(data);
  };

  useEffect(() => {
    loadPublications();
  }, []);

  const handleDelete = async (id: number) => {
    await deletePublication(id);
    await loadPublications();
  };

  return (
    <Container className="mt-4">
      <h2 className="mb-4">Publicaciones recientes</h2>
      {publications.map((pub) => (
        <PublicationCard key={pub.id} publication={pub} onDelete={handleDelete} />
      ))}
    </Container>
  );
}
