import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/js/bootstrap.bundle.min';

import Home from "./pages/public/Home";
import Contacts from "./pages/private/Contacts";
import Dashboard from "./pages/private/Dashboard";
import Cases from "./pages/private/Cases";
import Registration from "./pages/public/Registration";
import RegistrationSuccessful from "./pages/public/RegistrationSuccessful";
import Login from "./pages/public/Login";
import { AuthProvider } from "./contexts/AuthContext";
import ProtectedRoute from "./routes/ProtectedRoute";
import UserCalendar from "./pages/private/UserCalendar";

function App() {
  return (

    <AuthProvider>
      <Router>
        <Routes>

          <Route path="/" element={<Home />} />
          <Route path="/registration" element={<Registration/>}/>
          <Route path="/registration-successful" element={<RegistrationSuccessful/>}/>
          <Route path="/login" element={<Login/>}/>

          <Route path="/dashboard" element={<ProtectedRoute><Dashboard/></ProtectedRoute>}/>
          <Route path="/contacts" element={<ProtectedRoute><Contacts/></ProtectedRoute>}/>
          <Route path="/cases" element={<ProtectedRoute><Cases/></ProtectedRoute>}/>
          <Route path="/user-calendar" element={<ProtectedRoute><UserCalendar/></ProtectedRoute>}/>
          
        
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
