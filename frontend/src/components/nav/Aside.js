import React from 'react'
import { Link } from "react-router-dom";

import './css/Aside.css'
import '../../App.css'

import { ReactComponent as BoardIcon } from '../../assets/icons/clipboard.svg';
import { ReactComponent as ContactsIcon } from '../../assets/icons/person-rolodex.svg';
import { ReactComponent as CalendarIcon } from '../../assets/icons/calendar3-week.svg';
import { ReactComponent as CasesIcon } from '../../assets/icons/suitcase-lg.svg';
import { ReactComponent as InvoiceIcon } from '../../assets/icons/file-text.svg';


export default function Aside() {
  return (  
    <>
        <nav class="aside">
          <div>
            <h4>LeadLink</h4>
            <ul class="nav flex-column">
              <label>Přehled</label>
                <li class="nav-item">
                  <Link className="nav-link text-black" to="/dashboard"> <BoardIcon/> Nástěnka</Link></li>
                <li class="nav-item"><Link className="nav-link text-black" to="/user-calendar"> <CalendarIcon/> Kalendář</Link></li>

              <label>Kontakty</label>
                <li class="nav-item"><Link className="nav-link text-black" to="/contacts"> <ContactsIcon/> Adresář</Link></li>
                
              <label>Obchod</label>
                <li class="nav-item"><Link className="nav-link text-black" to="/cases"> <CasesIcon/> Případy</Link></li>
                <li class="nav-item"><Link className="nav-link text-black" to="/invoices"> <InvoiceIcon/> Fakturace</Link></li>
            </ul>
        </div>

        <div>
          <button className="btn btn-outline-dark custom-button-primary-filled-purple">
            <Link className="nav-link text-white" to="/manual">Nápověda</Link>
          </button>
        </div>
        </nav>
    </>
  )
}
