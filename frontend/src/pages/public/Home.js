import React from 'react'
import NavbarPublic from '../../components/nav/NavbarPublic'

import './css/Home.css';

export default function Home() {
  return (
    <div className='home'>

      <div className='home-inner'>

      <div className='viewport one'>
        <NavbarPublic/>
      </div>

      <div className='viewport two'>
        <div className="container text-center">
        <div className="row">
          <div className="col viewport1 left">
            <h1>Organizujte svou práci efektivně a jednoduše</h1>
          </div>
          <div className="col viewport1">
            <p>Náš software je navržen tak, aby freelancerům usnadnil organizaci jejich pracovních úkolů. S intuitivním rozhraním a bento stylem si můžete snadno uspořádat svůj den.</p>
            <div>
              <button type="button" className="btn btn-outline-dark ll-primary-purple">Zjistit více</button>
              <button type="button" className="btn btn-outline-dark ll-primary-purple-outline">Registrace</button>
            </div>
          </div>
        </div>
      </div>
      </div>

      <div className='viewport'>
        <p>2</p>
      </div>

      </div>

    </div>
  )
}
