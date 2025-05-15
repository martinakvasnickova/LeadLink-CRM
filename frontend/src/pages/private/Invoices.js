import React, { useEffect, useState } from 'react';
import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import axiosInstance from '../../axiosConfig';
import AddInvoice from '../../hooks/invoices/AddInvoice'; 
import EditInvoice from '../../hooks/invoices/EditInvoice'; 
import '../../App.css';
import './css/Layout.css';

import { ReactComponent as AddIcon } from '../../assets/icons/plus-lg.svg'
import { ReactComponent as EditIcon } from '../../assets/icons/three-dots-vertical.svg'
import { ReactComponent as DownloadInvoiceIcon } from '../../assets/icons/arrow-down-circle.svg'

export default function Invoices() {
  const [invoices, setInvoices] = useState([]);
  const [selectedInvoice, setSelectedInvoice] = useState(null);

  useEffect(() => {
    loadInvoices();
  }, []);

  const loadInvoices = async () => {
    const result = await axiosInstance.get('http://localhost:8080/invoice/user');
    setInvoices(result.data);
  };

  const downloadPdf = async (invoiceId) => {
    try {
      const response = await axiosInstance.get(`http://localhost:8080/invoice/${invoiceId}/pdf`, {
        responseType: 'blob',
      });
  
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `faktura_${invoiceId}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error('Chyba při stahování PDF:', error);
    }
  };
  

  return (
    <div className='content'>
      <Aside />
      <NavbarPrivate />

      <main>
        <div className='secondary-nav'>
          <h3>Faktury</h3>

          <button
            type="button"
            className="btn custom-button-primary-filled-mint"
            data-bs-toggle="modal"
            data-bs-target="#generateInvoiceModal"
          >
            <AddIcon/>
          </button>
        </div>

        <AddInvoice onSuccess={loadInvoices} />

        <table className="table table-striped table-hover custom-table mt-3">
          <thead>
            <tr>
              <th>#</th>
              <th>Číslo</th>
              <th>Popis</th>
              <th>Částka</th>
              <th>Klient</th>
              <th>Stav</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {invoices.map((invoice) => (
              <tr key={invoice.id}>
                <td>{invoice.id}</td>
                <td>{invoice.invoiceNumber}</td>
                <td>{invoice.description}</td>
                <td>{invoice.amount} Kč</td>
                <td>{invoice.clientName}</td>
                <td>{invoice.status}</td>
                <td>
                  <button
                        className="btn btn-outline-dark mx-1 custom-button-for-icon"
                        onClick={() => downloadPdf(invoice.id)}
                        >
                        <DownloadInvoiceIcon/>
                  </button>
                  <button
                    className="btn btn-outline-dark custom-button-for-icon"
                    data-bs-toggle="modal"
                    data-bs-target="#editInvoiceModal"
                    onClick={() => setSelectedInvoice(invoice)}
                  >
                    <EditIcon/>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>

      {selectedInvoice && <EditInvoice invoice={selectedInvoice} onSuccess={loadInvoices} />}
    </div>
  );
}
