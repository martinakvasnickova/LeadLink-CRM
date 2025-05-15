import React, { useState, useEffect } from 'react';
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
    endAt: "",
    caseId: "" 
  });

  const [cases, setCases] = useState([]); 

  const { name, startAt, endAt, caseId } = event;

  useEffect(() => {
    loadCases(); 
  }, []);

  const loadCases = async () => {
    try {
      const result = await axiosInstance.get('http://localhost:8080/case/user');
      setCases(result.data);
    } catch (error) {
      console.error("Chyba při načítání případů:", error);
    }
  };

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
      endAt: "",
      caseId: ""
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
              placeholder="Název události"
              name="name"
              value={name}
              onChange={onInputChange}
              required
            />

            <label htmlFor="startAt" className="form-label mt-3">Začátek</label>
            <input
              type="datetime-local"
              className="form-control"
              name="startAt"
              value={startAt}
              onChange={onInputChange}
              required
            />

            <label htmlFor="endAt" className="form-label mt-3">Konec</label>
            <input
              type="datetime-local"
              className="form-control"
              name="endAt"
              value={endAt}
              onChange={onInputChange}
              required
            />

            <label htmlFor="caseId" className="form-label mt-3">Připojit k případu</label>
            <select
              className="form-select"
              name="caseId"
              value={caseId}
              onChange={onInputChange}
              required
            >
              <option value="" disabled>Vyberte případ</option>
              {cases.map((businessCase) => (
                <option key={businessCase.id} value={businessCase.id}>
                  {businessCase.name}
                </option>
              ))}
            </select>

          </div>

          <div className="modal-footer">
            <button type="submit" className="btn btn-outline-primary">Uložit</button>
          </div>

        </div>
      </div>
    </form>
  );
}
