import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/js/bootstrap.bundle.min';

import Home from "./pages/public/Home";
import Contacts from "./pages/private/Contacts";
import Dashboard from "./pages/private/Dashboard";
import Registration from "./pages/public/Registration";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Dashboard />}/>
        <Route path="/contacts" element={<Contacts/>}/>
        <Route path="/registration" element={<Registration/>}/>
       
      </Routes>
    </Router>
  );
}

export default App;
