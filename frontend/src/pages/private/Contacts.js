import React from 'react'
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

export default function Contacts() {
  return (
    <div class="container-fluid">
    <div class="row">
    
    <Aside/>
    <main class="col-10 offset-2 p-3">
    <NavbarPrivate/>
      <h1>Main Content</h1>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
    </main>

  </div>
</div>
  )
}
