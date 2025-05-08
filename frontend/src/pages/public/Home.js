import React from 'react'
import NavbarPublic from '../../components/nav/NavbarPublic'

import './css/Home.css';
import AnimatedTextSlider from '../../components/animations/AnimatedTextSlider';

export default function Home() {
  return (
    <div className='home'>

      <div className='home-inner'>

      <div className='viewport one'>
        <NavbarPublic/>

        <div class="container-fluid text-center mt-4">
          <div class="row custom-gap">
            <div class="col col-one-one">
              <AnimatedTextSlider/>
            </div>
            <div class="col col-one-two">
            <div className="inner-blocks">
              <div className="inner-block one">

                <div>
                  <h1 className='special-big'>Tvůj den.</h1>
                  <h1 className='special-big'>Promyšleně.</h1>
                  <p>Ušetři čas a měj přehled o tom, co, kdy a pro koho děláš.</p>
                  <button className=' btn custom-button-primary-filled-olive'>
                    Vyzkoušet zdarma
                  </button>
                </div>
              </div>
              <div className="inner-block two"></div>
            </div>
            </div>
          </div>
        </div>
      </div>

      

      

      </div>

    </div>
  )
}
