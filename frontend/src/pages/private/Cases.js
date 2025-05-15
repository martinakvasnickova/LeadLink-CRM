import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import axiosInstance from '../../axiosConfig';
import AddCase from '../../hooks/cases/AddCase';
import EditCase from '../../hooks/cases/EditCase';


import '../../App.css';
import './css/Layout.css';

import { ReactComponent as AddIcon } from '../../assets/icons/plus-lg.svg'
import { ReactComponent as AddContactIcon } from '../../assets/icons/person-add.svg'
import { ReactComponent as EditIcon } from '../../assets/icons/three-dots-vertical.svg'
import { ReactComponent as DeleteIcon } from '../../assets/icons/trash3.svg'

export default function Cases() {
  const [businessCases, setBusinessCases] = useState([]);
  const [contacts, setContacts] = useState([]);
  const [selectedBusinessCase, setSelectedBusinessCase] = useState(null);
  const [selectedContact, setSelectedContact] = useState(null);
  const [contactCases, setContactCases] = useState([]);


  const { id } = useParams();

  useEffect(() => {
    loadBusinessCases();
    loadContacts(); 
    loadContactCases();
  }, []);

  const loadBusinessCases = async () => {
    const result = await axiosInstance.get('http://localhost:8080/case/user');
    setBusinessCases(result.data);
  };

  const loadContacts = async () => {
    const result = await axiosInstance.get('http://localhost:8080/contact/user'); 
    setContacts(result.data);
  };

  const deleteBusinessCase = async (id) => {
    await axiosInstance.delete(`http://localhost:8080/case/${id}`);
    loadBusinessCases();
  };

  const attachContactToCase = async (caseId, contactId) => {
    console.log("Odesílám správný payload na server:", {
      contactId: contactId,
      caseId: caseId,
      role: "Main Contact" 
    });
  
    await axiosInstance.post('http://localhost:8080/contact-case', {
      contactId: contactId,
      caseId: caseId,
      role: "Main Contact"
    });
  
    loadContactCases(); 
  };
  
  
  

  const loadContactCases = async () => {
    const result = await axiosInstance.get('http://localhost:8080/contact-case');
    setContactCases(result.data);
  };



  const getContactForCase = (caseId) => {
    const relation = contactCases.find(cc => cc.caseId === caseId);
    return relation ? { firstname: relation.contactFirstname, lastname: relation.contactLastname } : null;
  };
  

  return (
    <div className='content'>
      <Aside />
      <NavbarPrivate />

      <main>

        <div className='secondary-nav'>
          <h3>Případy</h3>

          <button type="button" className="btn custom-button-primary-filled-mint" data-bs-toggle="modal" data-bs-target="#addCaseModal">
            <AddIcon/>
          </button>
        </div>

        <AddCase onSuccess={loadBusinessCases} />

        <table className="table table-striped table-hover custom-table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Název</th>
              <th scope="col">Cena</th>
              <th scope="col">Kontakt</th>
              <th scope="col"></th>
            </tr>
          </thead>

          <tbody>
            {businessCases.map((businessCase, index) => (
              <tr key={businessCase.id}>
                <th scope="row">{index + 1}</th>
                <td>{businessCase.name}</td>
                <td>{businessCase.price}</td>
                <td>
                    {(() => {
                      const contact = getContactForCase(businessCase.id);
                      return contact ? `${contact.firstname} ${contact.lastname}` : "Není připojený kontakt";
                    })()}
                  </td>
                <td>
                  <button
                    className="btn btn-outline-dark mx-2 custom-button-for-icon"
                    data-bs-toggle="modal"
                    data-bs-target="#editCaseModal"
                    onClick={() => setSelectedBusinessCase(businessCase)}
                  >
                    <EditIcon/>
                  </button>

                  <button className="btn btn-outline-dark mx-2 custom-button-for-icon" onClick={() => deleteBusinessCase(businessCase.id)}>
                    <DeleteIcon/>
                  </button>

                  <button
                    className="btn btn-outline-dark mx-2 custom-button-for-icon"
                    data-bs-toggle="modal"
                    data-bs-target="#attachContactModal"
                    onClick={() => setSelectedBusinessCase(businessCase)}
                  >
                    <AddContactIcon/>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>

      <div className="modal fade" id="attachContactModal" tabIndex="-1" aria-labelledby="attachContactModalLabel" aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="attachContactModalLabel">Připojit kontakt k případu</h5>
              <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div className="modal-body">
              <h6>Vyberte kontakt:</h6>
              <select
  className="form-select"
  onChange={(e) => setSelectedContact(Number(e.target.value))}   
  defaultValue=""
>

                <option value="" disabled>
                  Vyberte kontakt
                </option>
                {contacts.map((contact) => (
                  <option key={contact.id} value={contact.id}>
                    {contact.firstname} {contact.lastname} ({contact.email})
                  </option>
                ))}
              </select>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Zavřít
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={() => {
                  if (selectedBusinessCase && selectedContact) {
                    attachContactToCase(selectedBusinessCase.id, selectedContact);
                    setSelectedContact(null);
                  } else {
                    console.error("Kontakt nebo případ není vybrán", selectedContact, selectedBusinessCase);
                    alert("Musíte vybrat kontakt i případ.");
                  }
                }}
                
              >
                Připojit
              </button>
            </div>
          </div>
        </div>
      </div>

      {selectedBusinessCase && <EditCase businessCase={selectedBusinessCase} refreshBusinessCases={loadBusinessCases} />}
    </div>
  );
}
