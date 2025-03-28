import React from 'react'
import Aside from '../../components/nav/Aside'

export default function Dashboard() {
  return (
    <div class="container-fluid">
    <div class="row">
    
    <Aside/>
    <main class="col-10 offset-2 p-3">
      <h1>Dashboard</h1>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
    </main>

  </div>
</div>
  )
}
