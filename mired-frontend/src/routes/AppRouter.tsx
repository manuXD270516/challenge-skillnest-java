import { Routes, Route, Navigate } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import AddPublication from "../pages/AddPublication";
import EditPublication from "../pages/EditPublication";
import PublicationDetail from "../pages/PublicationDetail";
import Friends from "../pages/Friends";
import FriendRequestsPage from "../pages/FriendRequestsPage";
import PrivateRoute from "./PrivateRoute";

const AppRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<PrivateRoute><Home /></PrivateRoute>} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/add-publication" element={<PrivateRoute><AddPublication /></PrivateRoute>} />
      <Route path="/edit-publication/:id" element={<PrivateRoute><EditPublication /></PrivateRoute>} />
      <Route path="/publications/:id" element={<PrivateRoute><PublicationDetail /></PrivateRoute>} />
      <Route path="/friends" element={<PrivateRoute><Friends /></PrivateRoute>} />
      <Route path="/requests" element={<PrivateRoute><FriendRequestsPage /></PrivateRoute>} />
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};

export default AppRouter;
