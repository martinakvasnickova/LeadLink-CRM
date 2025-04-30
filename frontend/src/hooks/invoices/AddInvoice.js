import React, { useEffect, useState } from 'react';
import axiosInstance from '../../axiosConfig';
import { Modal } from 'bootstrap';

export default function AddInvoice({ onSuccess }) {
  const [cases, setCases] = useState([]);
  const [selectedCaseId, setSelectedCaseId] = useState('');
  const [formData, setFormData] = useState({
    issueDate: '',
    dueDate: '',
    amount: '',
    description: '',
    invoiceNumber: ''
  });

  useEffect(() => {
    loadCases();
  }, []);

  useEffect(() => {
    if (selectedCaseId) {
      axiosInstance.get(`http://localhost:8080/invoice/pre-fill?caseId=${selectedCaseId}`)
        .then(res => {
          const data = res.data;
          setFormData({
            issueDate: new Date().toISOString().split('T')[0],
            dueDate: new Date(Date.now() + 14 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
            amount: data.amount,
            description: `Faktura za ${data.caseName}`,
            invoiceNumber: ''
          });
        });
    }
  }, [selectedCaseId]);

  const loadCases = async () => {
    const result = await axiosInstance.get('http://localhost:8080/case/user');
    setCases(result.data);
  };

  const onChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post('http://localhost:8080/invoice', {
        caseId: selectedCaseId,
        ...formData
      });

      const modalEl = document.getElementById('generateInvoiceModal');
      const modal = Modal.getInstance(modalEl) || new Modal(modalEl);
      modal.hide();

      document.body.classList.remove('modal-open');
      const backdrop = document.querySelector('.modal-backdrop');
      if (backdrop) backdrop.remove();

      setSelectedCaseId('');
      setFormData({
        issueDate: '',
        dueDate: '',
        amount: '',
        description: '',
        invoiceNumber: ''
      });

      if (onSuccess) onSuccess();
    } catch (err) {
      console.error('Chyba při odesílání faktury:', err);
    }
  };

  return (
    <form
      className="modal fade"
      id="generateInvoiceModal"
      tabIndex="-1"
      aria-labelledby="generateInvoiceModalLabel"
      aria-hidden="true"
      onSubmit={onSubmit}
    >
      <div className="modal-dialog">
        <div className="modal-content">

          <div className="modal-header">
            <h5 className="modal-title" id="generateInvoiceModalLabel">Generovat fakturu</h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Zavřít"></button>
          </div>

          <div className="modal-body">
            <label className="form-label">Obchodní případ</label>
            <select
              className="form-select mb-2"
              value={selectedCaseId}
              onChange={(e) => setSelectedCaseId(e.target.value)}
              required
            >
              <option value="" disabled>Vyberte případ</option>
              {cases.map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </select>

            <label className="form-label">Popis</label>
            <input type="text" className="form-control" name="description" value={formData.description} onChange={onChange} required />

            <label className="form-label mt-2">Částka</label>
            <input type="number" className="form-control" name="amount" value={formData.amount} onChange={onChange} required />

            <label className="form-label mt-2">Číslo faktury</label>
            <input type="text" className="form-control" name="invoiceNumber" value={formData.invoiceNumber} onChange={onChange} />

            <label className="form-label mt-2">Datum vystavení</label>
            <input type="date" className="form-control" name="issueDate" value={formData.issueDate} onChange={onChange} required />

            <label className="form-label mt-2">Datum splatnosti</label>
            <input type="date" className="form-control" name="dueDate" value={formData.dueDate} onChange={onChange} required />
          </div>

          <div className="modal-footer">
            <button type="submit" className="btn btn-outline-primary">Uložit</button>
          </div>

        </div>
      </div>
    </form>
  );
}
