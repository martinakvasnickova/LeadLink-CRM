import React from 'react'

export default function Aside() {
  return (  
    <>
        <nav class="col-md-3 col-lg-2 d-none d-md-block bg-dark text-white p-3 vh-100 position-fixed">
        <h4>Menu</h4>
        <ul class="nav flex-column">
            <li class="nav-item"><a class="nav-link text-white" href="#">Home</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="#">About</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="#">Services</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="#">Contact</a></li>
        </ul>
        </nav>
    </>
  )
}
