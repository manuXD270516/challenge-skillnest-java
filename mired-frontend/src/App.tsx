import AppRouter from "./routes/AppRouter";
import "./App.css";
import Navbar from "./components/Navbar";

const App = () => {
  return (
    <div className="App">
       <Navbar />
      <AppRouter />
    </div>
  );
};

export default App;
