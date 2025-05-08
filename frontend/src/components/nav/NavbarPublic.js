import React from 'react'
import { Link } from "react-router-dom"
import './css/NavbarPublic.css'

export default function NavbarPublic() {
  return (
    <div className='home'>
      <nav className="navbar navbar-expand-lg custom-nav-public">
        <div className="container-fluid d-flex justify-content-between align-items-center">
          <a className="navbar-brand" href="#">LeadLink</a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
            <ul className="navbar-nav me-3">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="#">O nás</a>
              </li>
              <li className="nav-item">
                <a className="nav-link active" href="#">Kontakty</a>
              </li>
              <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Více
                </a>
                <ul className="dropdown-menu">
                  <li><a className="dropdown-item" href="#">Action</a></li>
                  <li><a className="dropdown-item" href="#">Another action</a></li>
                  <li><a className="dropdown-item" href="#">Something else here</a></li>
                </ul>
              </li>
            </ul>
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link text-white" to="/login">Přihlásit se</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  )
}
