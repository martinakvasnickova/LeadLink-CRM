import React from 'react'
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

import '../../App.css';
import './css/Layout.css'
import '../../App.css'

export default function Dashboard() {
  return (
    <div className='content'>
      <Aside/>
      <NavbarPrivate/>
      <main>
      <div className='secondary-nav'>
        <h3>Nástěnka</h3>
      </div>

      <p>Vítejtě zpět</p>
      </main>
    </div>
  )
}
