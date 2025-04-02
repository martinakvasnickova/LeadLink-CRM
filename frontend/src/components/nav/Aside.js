import React from 'react'
import { Link } from "react-router-dom";

import './css/Aside.css'

export default function Aside() {
  return (  
    <>
        <nav class="aside">
        <h4>LeadLink</h4>
        <ul class="nav flex-column">
            <li class="nav-item"><Link className="nav-link text-white" to="/dashboard">Nástěnka</Link></li>
            <li class="nav-item"><Link className="nav-link text-white" to="/contacts">Adresář</Link></li>
            <li class="nav-item"><Link className="nav-link text-white" to="/cases">Případy</Link></li>
            <li class="nav-item"><Link className="nav-link text-white" to="/user-calendar">Kalendář</Link></li>
        </ul>
        </nav>
    </>
  )
}
