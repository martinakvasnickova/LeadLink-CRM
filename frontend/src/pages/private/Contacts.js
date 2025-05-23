import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';

import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import AddContact from '../../hooks/contacts/AddContact';
import EditContact from '../../hooks/contacts/EditContact';

import '../../App.css';
import './css/Layout.css';
import axiosInstance from '../../axiosConfig';

import { ReactComponent as AddIcon } from '../../assets/icons/plus-lg.svg'
import { ReactComponent as EditIcon } from '../../assets/icons/three-dots-vertical.svg'
import { ReactComponent as DeleteIcon } from '../../assets/icons/trash3.svg'

export default function Contacts() {
  const [contacts, setContacts] = useState([]);
  const [selectedContact, setSelectedContact] = useState(null);

  const {id}=useParams()

  useEffect(() => {
    loadContacts();
  }, []);

  const loadContacts = async () => {
    const result = await axiosInstance.get('http://localhost:8080/contact/user');
    setContacts(result.data);
  };

  const deleteContact=async(id)=>{
    await axiosInstance.delete(`http://localhost:8080/contact/${id}`)
    loadContacts()
  }

  return (
    <div className='content'>
      <Aside />
      <NavbarPrivate />
      <main>

      <div className='secondary-nav'>
        <h3>Adresář</h3>

        <button type="button" className="btn custom-button-primary-filled-mint" data-bs-toggle="modal" data-bs-target="#addContactModal">
          <AddIcon/>
        </button>
        <AddContact onSuccess={loadContacts} />
      </div>

        <table className="table table-striped table-hover custom-table ">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Jméno</th>
              <th scope="col">Příjmení</th>
              <th scope="col">Email</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {contacts.map((contact, index) => (
              <tr key={contact.id}>
                <th scope="row">{index + 1}</th>
                <td>{contact.firstname}</td>
                <td>{contact.lastname}</td>
                <td>{contact.email}</td>
                <td>

                  <button className="btn btn-outline-dark mx-2 custom-button-for-icon" onClick={()=>deleteContact(contact.id)}><DeleteIcon/></button>
                  <button className="btn btn-outline-dark mx-2 custom-button-for-icon" data-bs-toggle="modal" data-bs-target="#editContactModal" onClick={() => setSelectedContact(contact)}>
                    <EditIcon/>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>

      {selectedContact && <EditContact contact={selectedContact} refreshContacts={loadContacts} />}
    </div>
  );
}
