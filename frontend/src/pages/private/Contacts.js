import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';

import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import AddContact from '../../hooks/contacts/AddContact';

import './css/Layout.css'

export default function Contacts() {

  const [contacts, setContacts]=useState([])

  useEffect(()=>{
    loadContacts();
  },[]);

  const loadContacts=async()=>{
    const result = await axios.get('http://localhost:8080/contacts')
    setContacts(result.data);
  }

  return (
    <div>
      <Aside/>
      <NavbarPrivate/>
      <main>
        <h2>Adresář</h2>
        
        <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
          Přidat Kontakt
        </button>
        <AddContact/>


       

        <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Jméno</th>
            <th scope="col">Příjmení</th>
            <th scope="col">Email</th>
            <th scope="col">Action</th>
          </tr>
          </thead>
          <tbody>
            {
              contacts.map((contact, index)=>(
                <tr>
                  <th scope="row" key={index}>{index+1}</th>
                  <td>{contact.firstname}</td>
                  <td>{contact.lastname}</td>
                  <td>{contact.email}</td>
                  <td>
                    <button className='btn btn-outline-dark mx-2'>Detail</button>
                    <button className='btn btn-outline-dark mx-2'>Upravit</button>
                    <button className='btn btn-outline-dark mx-2'>Smazat</button>
                  </td>
              </tr>
              ))
            }
          </tbody>
        </table>
      </main>
    </div>
  )
}
