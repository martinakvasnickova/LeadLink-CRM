import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axiosInstance from '../../axiosConfig'

export default function AddCase() {

    let navigate = useNavigate()

    const [businessCase, setBusinessCase] = useState({
        name:"",
        price:""
    })

    const {name, price} = businessCase

    const onInputChange = (e) =>{
        setBusinessCase({...businessCase, [e.target.name]:e.target.value})
    }

    const onSubmit = async(e)=>{
        e.preventDefault();
        await axiosInstance.post('http://localhost:8080/case', businessCase);
        navigate('/cases');
    }
  return (
    <form class="modal fade" id="addCaseModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" onSubmit={(e)=>onSubmit(e)}>
    <div class="modal-dialog">
      <div class="modal-content">
        
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Přidat kontakt</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <label htmlFor="Firstname" className='form-label'>Název</label>
          <input type={'text'} className='form-control' placeholder='jméno' name='name' value={name} onChange={(e)=>onInputChange(e)}></input>

          <label htmlFor="Lastname" className='form-label'>Cena</label>
          <input type={'text'} className='form-control' placeholder='příjmení' name='price' value={price} onChange={(e)=>onInputChange(e)}></input>
        </div>

        <div class="modal-footer">
          <button type="submit" class="btn btn-outline-primary">Uložit</button>
        </div>

      </div>
    </div>
  </form>
  )
}
