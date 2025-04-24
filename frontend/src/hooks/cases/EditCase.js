import React, { useState, useEffect } from 'react'
import axiosInstance from '../../axiosConfig';
import { Modal } from 'bootstrap';

export default function EditCase({businessCase, refreshBusinessCases}) {
    const [updatedBusinessCase, setUpdatedBusinessCase] = useState(businessCase);

    useEffect(()=>{
        setUpdatedBusinessCase(businessCase);
    }, [businessCase]);

    const onInputChange = (e) => {
        setUpdatedBusinessCase({...updatedBusinessCase, [e.target.name]: e.target.value});
    }

    

    const onSubmit = async (e) => {
      e.preventDefault();
      await axiosInstance.put(`http://localhost:8080/case/${updatedBusinessCase.id}`, updatedBusinessCase);
    
      const modalElement = document.getElementById('editCaseModal');
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();
  
      document.body.classList.remove('modal-open'); 
      const modalBackdrop = document.querySelector('.modal-backdrop');
      if (modalBackdrop) modalBackdrop.remove(); 
  
      refreshBusinessCases();
    };

    


  return (
    <div className="modal fade" id="editCaseModal" tabIndex="-1" aria-labelledby="editCaseLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h1 className="modal-title fs-5" id="editCaseModal">Upravit kontakt</h1>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <form onSubmit={onSubmit}>
            <div className="modal-body">
              <label htmlFor="Firstname" className="form-label">Název</label>
              <input type="text" className="form-control" name="name" value={updatedBusinessCase.name} onChange={onInputChange} />

              <label htmlFor="Lastname" className="form-label">Cena</label>
              <input type="text" className="form-control" name="price" value={updatedBusinessCase.price} onChange={onInputChange} />
            </div>

            <div className="modal-footer">
              <button type="submit" className="btn btn-outline-primary" data-bs-dismiss="modal">Uložit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}
