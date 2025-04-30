import React, { useEffect, useState } from 'react';
import axiosInstance from '../../axiosConfig';
import { Modal } from 'bootstrap';

export default function EditInvoice({ invoice, onSuccess }) {
  const [updatedInvoice, setUpdatedInvoice] = useState(invoice);

  const statusOptions = ['ISSUED', 'PAID', 'CANCELLED'];

  useEffect(() => {
    setUpdatedInvoice(invoice);
  }, [invoice]);

  const onChange = (e) => {
    setUpdatedInvoice({ ...updatedInvoice, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.put(`http://localhost:8080/invoice/${updatedInvoice.id}`, updatedInvoice);

      const modalEl = document.getElementById('editInvoiceModal');
      const modal = Modal.getInstance(modalEl) || new Modal(modalEl);
      modal.hide();

      document.body.classList.remove('modal-open');
      const backdrop = document.querySelector('.modal-backdrop');
      if (backdrop) backdrop.remove();

      if (onSuccess) onSuccess();
    } catch (err) {
      console.error('Chyba při ukládání faktury:', err);
    }
  };

  return (
    <div className="modal fade" id="editInvoiceModal" tabIndex="-1" aria-labelledby="editInvoiceModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={onSubmit}>

          <div className="modal-header">
            <h5 className="modal-title" id="editInvoiceModalLabel">Upravit fakturu</h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Zavřít"></button>
          </div>

          <div className="modal-body">
            <label className="form-label">Popis</label>
            <input type="text" className="form-control" name="description" value={updatedInvoice.description} onChange={onChange} />

            <label className="form-label mt-2">Částka</label>
            <input type="number" className="form-control" name="amount" value={updatedInvoice.amount} onChange={onChange} />

            <label className="form-label mt-2">Číslo faktury</label>
            <input type="text" className="form-control" name="invoiceNumber" value={updatedInvoice.invoiceNumber} onChange={onChange} />

            <label className="form-label mt-2">Datum vystavení</label>
            <input type="date" className="form-control" name="issueDate" value={updatedInvoice.issueDate} onChange={onChange} />

            <label className="form-label mt-2">Datum splatnosti</label>
            <input type="date" className="form-control" name="dueDate" value={updatedInvoice.dueDate} onChange={onChange} />

            <label className="form-label mt-2">Stav</label>
            <select
              className="form-select"
              name="status"
              value={updatedInvoice.status}
              onChange={onChange}
            >
              {statusOptions.map((status) => (
                <option key={status} value={status}>
                  {status === 'ISSUED' && 'Vystavena'}
                  {status === 'PAID' && 'Zaplacena'}
                  {status === 'CANCELLED' && 'Stornována'}
                </option>
              ))}
            </select>
          </div>

          <div className="modal-footer">
            <button type="submit" className="btn btn-outline-primary">Uložit</button>
          </div>

        </form>
      </div>
    </div>
  );
}
