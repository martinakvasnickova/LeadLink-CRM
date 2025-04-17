import React from 'react'
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

import './css/Layout.css'
import '../../App.css'

export default function Dashboard() {
  return (
    <div>
      <Aside/>
      <NavbarPrivate/>
      <main>
        <h3>Nástěnka</h3>
        <p>Vítejtě zpět</p>
      </main>
    </div>
  )
}
