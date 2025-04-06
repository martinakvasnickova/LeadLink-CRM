import React, { useState } from 'react';
import './css/Registration.css';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../axiosConfig'; 
import { useAuth } from '../../contexts/AuthContext';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = (e) => {
    if (e.target.name === 'username') {
      setUsername(e.target.value);
    } else if (e.target.name === 'password') {
      setPassword(e.target.value);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');

    const loginData = { username, password };

    try {
      const response = await axiosInstance.post(
        'http://localhost:8080/user/login',
        loginData
      );

      if (response.status === 200) {
        localStorage.setItem('username', username);
        localStorage.setItem('password', password);

        login();
        navigate('/dashboard');
      } else {
        const errorData = response.data;
        setError(errorData.message || 'Login failed. Please retry.');
      }
    } catch (error) {
      setError('An error occurred. Please retry.');
    }
  };

  return (
    <div className='registration'>
      <form className='registration-form' onSubmit={handleLogin}>
        <h2>Přihlášení</h2>

        <div className="mb-3">
          <label htmlFor="exampleInputEmail1" className="form-label">Email</label>
          <input
            type="text"
            name="username"
            value={username}
            onChange={handleChange}
            className="form-control"
            id="email"
            aria-describedby="emailHelp"
          />
          <div id="emailHelp" className="form-text">
            We'll never share your email with anyone else.
          </div>
        </div>

        <div className="mb-3">
          <label htmlFor="exampleInputPassword1" className="form-label">Heslo</label>
          <input
            type="password"
            name="password"
            value={password}
            onChange={handleChange}
            className="form-control"
            id="password"
          />
        </div>

        <button type="submit" className="btn btn-primary">Přihlásit se</button>
      </form>
    </div>
  );
}
