import React, { useState } from 'react';
import '../../App.css'
import './css/Registration.css';
import { useNavigate, Link } from 'react-router-dom';
import axiosInstance from '../../axiosConfig'; 
import { useDispatch } from 'react-redux';
import { loginSuccess } from '../../slices/authSlice';
import { jwtDecode } from 'jwt-decode'; 

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
  
    try {
      const response = await axiosInstance.post('http://localhost:8080/user/login', {
        username,
        password
      });
  
      const token = response.data.token;
  
      if (token) {
        dispatch(loginSuccess(token));
  
        localStorage.setItem('token', token);
  
        const decoded = jwtDecode(token);
        localStorage.setItem('role', decoded.role);
  
        navigate('/dashboard');
      } else {
        setError('Nepodařilo se získat token.');
      }
  
    } catch (error) {
      setError('Přihlášení selhalo.');
    }
  };
  

  return (
    <div className='registration'>
      <form className='registration-form' onSubmit={handleLogin}>

        <h2>Přihlášení</h2>

        <div className="mb-3">
          <label htmlFor="username" className="form-label">Email*</label>
          <input
            type="text"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="form-control custom-placeholder"
            id="username"
            placeholder='john.doe@example.com'
          />
        </div>

        <div className="mb-3">
          <label htmlFor="password" className="form-label">Heslo*</label>
          <input
            type="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="form-control custom-placeholder"
            id="password"
            placeholder='*****'
          />
        </div>

        {error && <div className="text-danger mb-2">{error}</div>}

        <button type="submit" className="btn custom-button-primary-filled-olive-maxi">Přihlásit se</button>

        <p>Nemáte účet? <Link className='text-black' to="/registration">Registrujte se</Link></p>
     
      </form>
    </div>
  );
}
