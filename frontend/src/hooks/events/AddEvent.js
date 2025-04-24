import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../axiosConfig';
import 'bootstrap/dist/css/bootstrap.min.css';
import * as bootstrap from 'bootstrap';

import { Modal } from 'bootstrap';

export default function AddEvent({ onSuccess }) {

  let navigate = useNavigate();

  const [event, setEvent] = useState({
    name: "",
    createdAt: "",
    startAt: "",
    endAt: ""
  });

  const { name, startAt, endAt } = event;

  const onInputChange = (e) => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  

  const onSubmit = async (e) => {
    e.preventDefault();
    
    await axiosInstance.post('http://localhost:8080/event', event);

    const modalElement = document.getElementById('addEventModal');
    const modal = Modal.getInstance(modalElement) || new Modal(modalElement);
    modal.hide();

    document.body.classList.remove('modal-open'); 
    const modalBackdrop = document.querySelector('.modal-backdrop');
    if (modalBackdrop) modalBackdrop.remove(); 

    setEvent({
      name: "",
      startAt: "",
      endAt: ""
    });

    if (onSuccess) onSuccess();
  };

  return (
    <form
      className="modal fade"
      id="addEventModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabIndex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
      onSubmit={onSubmit}
    >
      <div className="modal-dialog">
        <div className="modal-content">

          <div className="modal-header">
            <h1 className="modal-title fs-5" id="staticBackdropLabel">Přidat událost</h1>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div className="modal-body">
            <label htmlFor="name" className="form-label">Název</label>
            <input
              type="text"
              className="form-control"
              placeholder="jméno"
              name="name"
              value={name}
              onChange={onInputChange}
            />

            <label htmlFor="startAt" className="form-label mt-3">Začátek</label>
            <input
              type="datetime-local"
              className="form-control"
              name="startAt"
              value={startAt ? startAt : ""}
              onChange={onInputChange}
            />

            <label htmlFor="endAt" className="form-label mt-3">Konec</label>
            <input
              type="datetime-local"
              className="form-control"
              name="endAt"
              value={endAt ? endAt : ""}
              onChange={onInputChange}
            />
          </div>

          <div className="modal-footer">
            <button type="submit" className="btn btn-outline-primary">Uložit</button>
          </div>

        </div>
      </div>
    </form>
  );
}
