import axios from 'axios'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axiosInstance from '../../axiosConfig'
import { Modal } from 'bootstrap';

import '../../../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js';

export default function AddContact({ onSuccess }) {

    let navigate = useNavigate()

    const [contact, setContact]=useState({
        firstname:"",
        lastname:"",
        email:""
    })

    const{firstname, lastname, email}=contact

    const onInputChange=(e)=>{
        setContact({...contact,[e.target.name]:e.target.value})
    }

    const onSubmit = async (e) => {
      e.preventDefault();
    
      await axiosInstance.post('http://localhost:8080/contact', contact);
    
      const modalElement = document.getElementById('addContactModal');
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
    
      document.body.classList.remove('modal-open'); 
      const modalBackdrop = document.querySelector('.modal-backdrop');
      if (modalBackdrop) modalBackdrop.remove(); 
    
      setContact({
        firstname: "",
        lastname: "",
        email: ""
      });
    
      if (onSuccess) onSuccess();
    };
    

  return (
    <form class="modal fade" id="addContactModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" onSubmit={(e)=>onSubmit(e)}>
    <div class="modal-dialog">
      <div class="modal-content">
        
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Přidat kontakt</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <label htmlFor="Firstname" className='form-label'>Jméno</label>
          <input type={'text'} className='form-control' placeholder='jméno' name='firstname' value={firstname} onChange={(e)=>onInputChange(e)}></input>

          <label htmlFor="Lastname" className='form-label'>Příjmení</label>
          <input type={'text'} className='form-control' placeholder='příjmení' name='lastname' value={lastname} onChange={(e)=>onInputChange(e)}></input>

          <label htmlFor="Email" className='form-label'>Email</label>
          <input type={'text'} className='form-control' placeholder='email' name='email' value={email} onChange={(e)=>onInputChange(e)}></input>
        </div>

        <div class="modal-footer">
          <button type="submit" class="btn btn-outline-primary">Uložit</button>
        </div>

      </div>
    </div>
  </form>
  )
}
