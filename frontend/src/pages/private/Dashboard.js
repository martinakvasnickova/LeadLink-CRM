import React from 'react'
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

import './css/Layout.css'

export default function Dashboard() {
  return (
    <div>
      <Aside/>
      <NavbarPrivate/>
      <main>
        <h1>Dashboard</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
      </main>
    </div>
  )
}
