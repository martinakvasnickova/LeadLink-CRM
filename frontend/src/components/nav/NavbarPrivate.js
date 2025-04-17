import React from 'react';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate, Link } from 'react-router-dom';


import './css/NavbarPrivate.css';
import'../../App.css';

import { ReactComponent as SettingsIcon } from '../../assets/icons/settings-outline.svg';
import { ReactComponent as BellIcon } from '../../assets/icons/bell-outline.svg';
import { ReactComponent as PersonIcon } from '../../assets/icons/person-outline.svg';

export default function NavbarPrivate() {

  const {logout} = useAuth();

  const navigate = useNavigate();

  const logoutUser = () =>{
    logout()
    navigate("/login")
  }

  return (
    <nav className="navbar navbar-expand-lg custom-navbar">
      <div className="container-fluid">
        <form className="d-flex" role="search">
          <input className="form-control me-2 custom-search" type="search" placeholder="Kontakt" aria-label="Search"/>
          <button className="btn btn-outline-dark custom-button-primary-outline-blue" type="submit">Hledat</button>
        </form>
    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse" id="navbarSupportedContent">
      <ul className="navbar-nav me-auto mb-2 mb-lg-0">
      <a href="#" className="nav-link text-black d-lg-none">Small Screen Item</a>
      <li class="nav-item"><Link className="nav-link text-black d-lg-none" to="/dashboard">Nástěnka</Link></li>

        
      </ul>
      
      <SettingsIcon className = 'icon'/>
      <BellIcon className = 'icon'/>
      
      <button className='btn custom-button-primary-outline-blue' onClick={logoutUser}>Logout</button>
    </div>
  </div>
</nav>
  )
}
