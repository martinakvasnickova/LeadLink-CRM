import React, { useEffect, useState } from 'react';
import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';
import axiosInstance from '../../axiosConfig';

import '../../App.css';
import './css/Layout.css';

export default function Dashboard() {
  const [userName, setUserName] = useState('');
  const [eventsToday, setEventsToday] = useState([]);
  const [activeCases, setActiveCases] = useState([]);
  const [unpaidInvoices, setUnpaidInvoices] = useState([]);
  const [metrics, setMetrics] = useState({
    totalInvoices: 0,
    totalClients: 0,
    monthlyIncome: 0
  });

  useEffect(() => {
    loadEvents();
    loadCases();
    loadInvoices();
  }, []);


  const loadEvents = async () => {
    const result = await axiosInstance.get('http://localhost:8080/event/user');
    const today = new Date().toISOString().split('T')[0];
    const filtered = result.data.filter(ev => typeof ev.startAt === 'string' && ev.startAt.startsWith(today));
    setEventsToday(filtered);
  };

  const loadCases = async () => {
    const result = await axiosInstance.get('http://localhost:8080/case/user');
    setActiveCases(result.data.slice(0, 3));
  };

  const loadInvoices = async () => {
    const result = await axiosInstance.get('http://localhost:8080/invoice/user');
    const unpaid = result.data.filter(inv => inv.status === 'ISSUED');
    const paid = result.data.filter(inv => inv.status === 'PAID');

    setUnpaidInvoices(unpaid);
    setMetrics({
      totalInvoices: result.data.length,
      totalClients: new Set(result.data.map(i => i.clientName)).size,
      monthlyIncome: paid
        .filter(inv => inv.issueDate?.startsWith(new Date().toISOString().slice(0, 7)))
        .reduce((sum, inv) => sum + inv.amount, 0)
    });
  };

  return (
    <div className='content'>
      <Aside />
      <NavbarPrivate />
      <main>
        <div className='secondary-nav'>
          <h3>Nástěnka</h3>
        </div>

        <h5 className="mt-3">Vítej zpět{userName ? `, ${userName}` : ''}</h5>

        <div className="row mt-4">

          {/* METRIKY */}
          <div className="col-md-4">
            <div className="card p-3 shadow-sm rounded-3">
              <h6>Příjmy tento měsíc</h6>
              <p><strong>{metrics.monthlyIncome.toLocaleString()} Kč</strong></p>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card p-3 shadow-sm rounded-3">
              <h6>Faktury celkem</h6>
              <p><strong>{metrics.totalInvoices}</strong></p>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card p-3 shadow-sm rounded-3">
              <h6>Počet klientů</h6>
              <p><strong>{metrics.totalClients}</strong></p>
            </div>
          </div>

        </div>

        {/* DNESNÍ UDÁLOSTI */}
        <div className="mt-5">
          <h5>Dnešní události</h5>
          {eventsToday.length === 0 ? (
            <p>Nemáte naplánované žádné události na dnes.</p>
          ) : (
            <ul className="list-group">
              {eventsToday.map(event => (
                <li className="list-group-item d-flex justify-content-between" key={event.id}>
                  {event.name}
                  <span>{event.startAt?.slice(11, 16)} – {event.caseName}</span>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* ROZPRACOVANÉ PŘÍPADY */}
        <div className="mt-5">
          <h5>Vaše případy</h5>
          {activeCases.length === 0 ? (
            <p>Nemáte žádné aktivní případy.</p>
          ) : (
            <ul className="list-group">
              {activeCases.map(c => (
                <li className="list-group-item d-flex justify-content-between" key={c.id}>
                  {c.name}
                  <span>{c.price} Kč</span>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* NEZAPLACENÉ FAKTURY */}
        <div className="mt-5">
          <h5>Nezaplacené faktury</h5>
          {unpaidInvoices.length === 0 ? (
            <p>Žádné nezaplacené faktury 🎉</p>
          ) : (
            <ul className="list-group">
              {unpaidInvoices.map(inv => (
                <li className="list-group-item d-flex justify-content-between" key={inv.id}>
                  {inv.invoiceNumber} – {inv.clientName}
                  <span>{inv.amount} Kč</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      </main>
    </div>
  );
}
