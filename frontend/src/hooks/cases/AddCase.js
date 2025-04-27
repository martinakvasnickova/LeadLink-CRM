import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axiosInstance from '../../axiosConfig'
import { Modal } from 'bootstrap';

import '../../../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js';

export default function AddCase({ onSuccess }) {

  let navigate = useNavigate();

  const [businessCase, setBusinessCase] = useState({
    name: "",
    price: ""
  });

  const [errors, setErrors] = useState({}); // <-- Přidáno pro zachytávání chyb

  const { name, price } = businessCase;

  const onInputChange = (e) => {
    setBusinessCase({ ...businessCase, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      await axiosInstance.post('http://localhost:8080/case', businessCase);

      const modalElement = document.getElementById('addCaseModal');
      const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
      modal.hide();

      document.body.classList.remove('modal-open');
      const modalBackdrop = document.querySelector('.modal-backdrop');
      if (modalBackdrop) modalBackdrop.remove();

      setBusinessCase({
        name: "",
        price: ""
      });

      setErrors({}); // Vymazat chyby po úspěšném odeslání

      if (onSuccess) onSuccess();

    } catch (error) {
      if (error.response && error.response.status === 400) {
        setErrors(error.response.data.errors || {});
      } else {
        console.error('Something went wrong:', error);
      }
    }
  };

  return (
    <form className="modal fade" id="addCaseModal" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" onSubmit={(e) => onSubmit(e)}>
      <div className="modal-dialog">
        <div className="modal-content">

          <div className="modal-header">
            <h1 className="modal-title fs-5" id="staticBackdropLabel">Přidat Případ</h1>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div className="modal-body">
            <label htmlFor="name" className='form-label'>Název</label>
            <input
              type="text"
              className={`form-control ${errors.name ? 'is-invalid' : ''}`}
              placeholder="Název případu"
              name="name"
              value={name}
              onChange={onInputChange}
              required
            />
            {errors.name && <div className="invalid-feedback">{errors.name}</div>}

            <label htmlFor="price" className='form-label mt-3'>Cena</label>
            <input
              type="number"
              className={`form-control ${errors.price ? 'is-invalid' : ''}`}
              placeholder="Cena"
              name="price"
              value={price}
              onChange={onInputChange}
            />
            {errors.price && <div className="invalid-feedback">{errors.price}</div>}
          </div>

          <div className="modal-footer">
            <button type="submit" className="btn btn-outline-primary">Uložit</button>
          </div>

        </div>
      </div>
    </form>
  );
}
