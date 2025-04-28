import React, { useEffect, useState } from 'react';
import axiosInstance from '../../axiosConfig';
import moment from 'moment';

import { Modal } from 'bootstrap';

export default function EditEvent({ event, setShowModal, refreshEvent }) {
  const [updatedEvent, setUpdatedEvent] = useState(event);
  const [cases, setCases] = useState([]);

  useEffect(() => {
    setUpdatedEvent(event); 
    loadCases();
  }, [event]);

  const loadCases = async () => {
    try {
      const response = await axiosInstance.get('http://localhost:8080/case/user');
      setCases(response.data);
    } catch (error) {
      console.error("Chyba při načítání případů:", error);
    }
  };

  const onInputChange = (e) => {
    setUpdatedEvent({ ...updatedEvent, [e.target.name]: e.target.value });
  };

  const onSelectChange = (e) => {
    setUpdatedEvent({ ...updatedEvent, caseId: Number(e.target.value) });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
  
    const payload = {
      id: updatedEvent.id,
      name: updatedEvent.name,
      startAt: updatedEvent.startAt || updatedEvent.start,
      endAt: updatedEvent.endAt || updatedEvent.end,
      caseId: updatedEvent.caseId,
    };
  
    try {
      await axiosInstance.put(`http://localhost:8080/event/${updatedEvent.id}`, payload);
      refreshEvent();
      setShowModal(false);
    } catch (error) {
      console.error("Chyba při ukládání:", error);
    }
  };

  const onDelete = async () => {
    if (window.confirm("Opravdu chcete smazat tuto událost?")) {
      try {
        await axiosInstance.delete(`http://localhost:8080/event/${updatedEvent.id}`);
        refreshEvent(); 
        setShowModal(false); 
      } catch (error) {
        console.error("Chyba při mazání události:", error);
      }
    }
  };

  return (
    <div className="modal fade show" id="editEventModal" tabIndex="-1" style={{ display: 'block' }} aria-labelledby="editEventModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="editEventModalLabel">Upravit Událost</h5>
            <button type="button" className="btn-close" onClick={() => setShowModal(false)}></button>
          </div>
          <form onSubmit={onSubmit}>
            <div className="modal-body">
              <label htmlFor="name" className="form-label">Název</label>
              <input type="text" className="form-control" name="name" value={updatedEvent.name || ''} onChange={onInputChange} />

              <label htmlFor="description" className="form-label mt-3">Popis</label>
              <textarea
                className="form-control"
                name="description"
                value={updatedEvent.description || ''}
                onChange={onInputChange}
                placeholder="Popište událost"
              />

              <label htmlFor="startAt" className="form-label mt-3">Začátek</label>
              <input type="datetime-local" className="form-control" name="startAt" value={moment(updatedEvent.start).format('YYYY-MM-DDTHH:mm')} onChange={onInputChange} />

              <label htmlFor="endAt" className="form-label mt-3">Konec</label>
              <input type="datetime-local" className="form-control" name="endAt" value={moment(updatedEvent.end).format('YYYY-MM-DDTHH:mm')} onChange={onInputChange} />

              <label htmlFor="caseId" className="form-label mt-3">Přiřazený případ</label>
              <select
                className="form-select"
                name="caseId"
                value={updatedEvent.caseId || ''}
                onChange={onSelectChange}
              >
                <option value="">Vyberte případ</option>
                {cases.map((oneCase) => (
                  <option key={oneCase.id} value={oneCase.id}>
                    {oneCase.name}
                  </option>
                ))}
              </select>
            </div>

            <div className="modal-footer">
              <button type="submit" className="btn btn-primary">Uložit</button>
              <button type="button" className="btn btn-danger" onClick={onDelete}>Smazat</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
