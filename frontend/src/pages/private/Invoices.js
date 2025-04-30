import React, { useEffect, useState } from 'react';
import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import axiosInstance from '../../axiosConfig';
import AddInvoice from '../../hooks/invoices/AddInvoice'; // Vytvoříme za chvíli
import EditInvoice from '../../hooks/invoices/EditInvoice'; // Placeholder
import '../../App.css';
import './css/Layout.css';

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
            Generovat fakturu
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
              <th>Akce</th>
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
                    className="btn btn-outline-dark"
                    data-bs-toggle="modal"
                    data-bs-target="#editInvoiceModal"
                    onClick={() => setSelectedInvoice(invoice)}
                  >
                    Upravit
                  </button>
                  <button
                        className="btn btn-outline-dark mx-1"
                        onClick={() => downloadPdf(invoice.id)}
                        >
                        Stáhnout PDF
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
