import { Link, useLocation, useNavigate } from "react-router-dom";
import { Button, Navbar as BsNavbar, Container, Nav } from "react-bootstrap";
import { useSession } from "../context/SessionContext";
import { logoutRequest } from "../services/authService";

const Navbar = () => {
  const { token, logout } = useSession();
  const location = useLocation();
  const navigate = useNavigate();

  const isLogin = location.pathname === "/login";
  const isRegister = location.pathname === "/register";

  const handleLogout = async () => {
    try {
      await logoutRequest();
      logout();
      navigate("/login");
    } catch (error) {
      console.error("Error al cerrar sesión:", error);
    }
  };

  return (
    <BsNavbar bg="dark" variant="dark" expand="lg">
      <Container>
        <BsNavbar.Brand as={Link} to="/">MiRed</BsNavbar.Brand>
        <BsNavbar.Toggle aria-controls="basic-navbar-nav" />
        <BsNavbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto align-items-center">
            {!token && (
              <>
                {isLogin && (
                  <Nav.Link as={Link} to="/register">Registro</Nav.Link>
                )}
                {isRegister && (
                  <Nav.Link as={Link} to="/login">Login</Nav.Link>
                )}
                {!isLogin && !isRegister && (
                  <>
                    <Nav.Link as={Link} to="/login">Login</Nav.Link>
                    <Nav.Link as={Link} to="/register">Registro</Nav.Link>
                  </>
                )}
              </>
            )}

            {token && (
              <>
                <Nav.Link as={Link} to="/">Inicio</Nav.Link>
                <Nav.Link as={Link} to="/add-publication">Publicar</Nav.Link>
                <Nav.Link as={Link} to="/friends">Amigos</Nav.Link>
                <Nav.Link as={Link} to="/requests">Solicitudes</Nav.Link>
                <Button onClick={handleLogout} variant="outline-light" size="sm">
                  Cerrar sesión
                </Button>
              </>
            )}
          </Nav>
        </BsNavbar.Collapse>
      </Container>
    </BsNavbar>
  );
};

export default Navbar;
