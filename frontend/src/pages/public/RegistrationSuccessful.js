import React from 'react'
import { Link } from "react-router-dom";

export default function RegistrationSuccessful() {
  return (
    <div className='registration'>
        
        <div>
            <h2>Registrace proběhla úspěšně.</h2>
            <Link to='/login'>Přihlásit se</Link>
        </div>
    </div>
  )
}
