import axios from 'axios';
import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axiosConfig';

export default function EditContact({ contact, refreshContacts }) {
  const [updatedContact, setUpdatedContact] = useState(contact);

  useEffect(() => {
    setUpdatedContact(contact); 
  }, [contact]);

  const onInputChange = (e) => {
    setUpdatedContact({ ...updatedContact, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axiosInstance.put(`http://localhost:8080/contact/${updatedContact.id}`, updatedContact);
    refreshContacts();
  };

  return (
    <div className="modal fade" id="editContactModal" tabIndex="-1" aria-labelledby="editContactLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h1 className="modal-title fs-5" id="editContactLabel">Upravit kontakt</h1>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <form onSubmit={onSubmit}>
            <div className="modal-body">
              <label htmlFor="Firstname" className="form-label">Jméno</label>
              <input type="text" className="form-control" name="firstname" value={updatedContact.firstname} onChange={onInputChange} />

              <label htmlFor="Lastname" className="form-label">Příjmení</label>
              <input type="text" className="form-control" name="lastname" value={updatedContact.lastname} onChange={onInputChange} />

              <label htmlFor="Email" className="form-label">Email</label>
              <input type="text" className="form-control" name="email" value={updatedContact.email} onChange={onInputChange} />
            </div>

            <div className="modal-footer">
              <button type="submit" className="btn btn-outline-primary" data-bs-dismiss="modal">Uložit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
