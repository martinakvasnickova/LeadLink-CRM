import React, { useEffect, useState } from 'react';
import axiosInstance from '../../axiosConfig';
import moment from 'moment';

import { Modal } from 'bootstrap';

export default function EditEvent({ event, setShowModal, refreshEvent }) {
  const [updatedEvent, setUpdatedEvent] = useState(event);

  useEffect(() => {
    setUpdatedEvent(event); 
  }, [event]);

  const onInputChange = (e) => {
    setUpdatedEvent({ ...updatedEvent, [e.target.name]: e.target.value });
  };

  

  const onSubmit = async (e) => {
    e.preventDefault();
  
    const payload = {
      id: updatedEvent.id,
      name: updatedEvent.name,
      description: updatedEvent.description,
      startAt: updatedEvent.startAt || updatedEvent.start,
      endAt: updatedEvent.endAt || updatedEvent.end,
    };
  
    try {
      await axiosInstance.put(`http://localhost:8080/event/${updatedEvent.id}`, payload);
      refreshEvent();
      setShowModal(false); // zavře modal React způsobem
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
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={() => setShowModal(false)}></button>
          </div>
          <form onSubmit={onSubmit}>
            <div className="modal-body">
              <label htmlFor="name" className="form-label">Název</label>
              <input type="text" className="form-control" name="name" value={updatedEvent.name} onChange={onInputChange} />

              <label htmlFor="description" className="form-label mt-3">Popis</label>
              <textarea
                className="form-control"
                name="description"
                value={updatedEvent.description || ''}
                onChange={onInputChange}
                placeholder="Popište událost"
              />

              <label htmlFor="startAt" className="form-label">Začátek</label>
              <input type="datetime-local" className="form-control" name="startAt" value={moment(updatedEvent.start).format('YYYY-MM-DDTHH:mm')} onChange={onInputChange} />

              <label htmlFor="endAt" className="form-label">Konec</label>
              <input type="datetime-local" className="form-control" name="endAt" value={moment(updatedEvent.end).format('YYYY-MM-DDTHH:mm')} onChange={onInputChange} />
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
