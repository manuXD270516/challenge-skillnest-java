import { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSession } from "../hooks/useSession";
import { registerUser } from "../services/authService";
import { Container, Row, Col, Card, Form, Button, Alert } from "react-bootstrap";

export default function Register() {
  const navigate = useNavigate();
  const { login: loginSession } = useSession();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await registerUser({ email, password });
      if (response.token) {
        loginSession(response.token, response.role, response.email);
        navigate("/");
      } else {
        setError("Registro exitoso, pero faltan datos en la respuesta.");
      }
    } catch (err: any) {
      setError(err.response?.data?.message || "Ocurrió un error al registrar.");
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Row className="w-100 justify-content-center">
        <Col xs={12} md={6} lg={4}>
          <Card className="shadow-sm">
            <Card.Body>
              <h3 className="text-center mb-4">Registro</h3>
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="registerEmail" className="mb-3">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Correo electrónico"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group controlId="registerPassword" className="mb-3">
                  <Form.Label>Contraseña</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </Form.Group>

                {error && <Alert variant="danger">{error}</Alert>}

                <Button type="submit" variant="primary" className="w-100">
                  Registrarse
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
