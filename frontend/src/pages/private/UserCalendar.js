import React, { useState, useEffect } from "react";
import { Calendar as BigCalendar, momentLocalizer, Views } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";

import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';

import './css/Layout.css';
import axiosInstance from '../../axiosConfig';

export default function UserCalendar() {
  const localizer = momentLocalizer(moment);
  const [events, setEvents] = useState([]);
  const [view, setView] = useState(Views.MONTH);
  const [date, setDate] = useState(new Date());

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      const response = await axiosInstance.get('http://localhost:8080/event/user');
      const data = response.data;

      const formattedEvents = data.map(event => ({
        id: event.id,
        title: event.name,
        start: new Date(event.startAt),
        end: new Date(event.endAt),
      }));

      setEvents(formattedEvents);
    } catch (error) {
      console.error("Chyba při načítání eventů:", error);
    }
  };

  const handleSelectEvent = (event) => {
    alert(`🗓️ Detail události: ${event.title}\n\nZačátek: ${event.start}\nKonec: ${event.end}`);
  };

  return (
    <div className="calendar-layout">
      <Aside />
      <NavbarPrivate />
      <main className="calendar-main">
        <h2>Kalendář</h2>
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
    </div>
  );
}
