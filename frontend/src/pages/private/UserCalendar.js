import React, { useState, useEffect } from "react";
import { Calendar as BigCalendar, momentLocalizer, Views } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";

import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';

import '../../App.css';
import './css/Layout.css';
import axiosInstance from '../../axiosConfig';
import AddEvent from "../../hooks/events/AddEvent";
import EditEvent from "../../hooks/events/EditEvent";

export default function UserCalendar() {
  const localizer = momentLocalizer(moment);
  const [events, setEvents] = useState([]);
  const [view, setView] = useState(Views.MONTH);
  const [date, setDate] = useState(new Date());
  const [selectedEvent, setSelectedEvent] = useState(null); 
  const [showModal, setShowModal] = useState(false); 

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      const response = await axiosInstance.get('http://localhost:8080/event/user');
      const data = response.data;
  
      const formattedEvents = data.map(event => ({
        id: event.id,
        name: event.name,
        startAt: event.startAt,
        endAt: event.endAt,
        start: new Date(event.startAt),
        end: new Date(event.endAt),
        title: event.name,
        caseId: event.caseId, // >>> přidat sem!
      }));
  
      setEvents(formattedEvents);
    } catch (error) {
      console.error("Chyba při načítání eventů:", error);
    }
  };
  

  const handleSelectEvent = (event) => {
    setSelectedEvent(event); 
    setShowModal(true); 
  };

  return (
    <div className="content calendar-layout">
      <Aside />
      <NavbarPrivate />
      <main className="calendar-main">
      <div className='secondary-nav'>
        <h3>Kalendář</h3>
        <button type="button" className="btn custom-button-primary-filled-mint" data-bs-toggle="modal" data-bs-target="#addEventModal">
          Přidat Událost
        </button>
      </div>
        <AddEvent onSuccess={loadEvents}/>
        
        <BigCalendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          style={{ height: 600, margin: '2rem' }}
          onSelectEvent={handleSelectEvent}
          views={Object.values(Views)}
          view={view}
          date={date}
          onNavigate={setDate}
          onView={(newView) => setView(newView)}
          toolbar
        />
      </main>

      {/* Modal pro editaci události */}
      {showModal && selectedEvent && (
        <EditEvent event={selectedEvent} setShowModal={setShowModal} refreshEvent={loadEvents} />
      )}
    </div>
  );
}
