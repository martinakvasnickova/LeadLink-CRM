import React, { useState, useEffect } from "react";
import { Calendar as BigCalendar, momentLocalizer, Views } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";

import './css/Layout.css';
import Aside from '../../components/nav/Aside';
import NavbarPrivate from '../../components/nav/NavbarPrivate';

export default function UserCalendar() {
  const localizer = momentLocalizer(moment);
  const [events, setEvents] = useState([]);
  const [view, setView] = useState(Views.MONTH); 

  useEffect(() => {
    setEvents([
      {
        title: "Pracovní schůzka",
        start: new Date(2025, 3, 10, 10, 0),
        end: new Date(2025, 3, 10, 12, 0),
        id: 1,
      },
      {
        title: "Deadline projektu",
        start: new Date(2025, 3, 15, 0, 0),
        end: new Date(2025, 3, 15, 23, 59),
        id: 2,
      },
    ]);
  }, []);

  const handleSelectEvent = (event) => {
    alert(`Detail události: ${event.title}`);
  };

  return (
    <div>
      <Aside />
      <NavbarPrivate />
      <main>
        <h2>Kalendář</h2>
        <BigCalendar
            localizer={localizer}
            events={events}
            startAccessor="start"
            endAccessor="end"
            style={{ height: 500 }}
            onSelectEvent={handleSelectEvent}
            views={Object.values(Views)}
            view={view} 
            onView={(newView) => setView(newView)}
            toolbar={true}
        />
      </main>
    </div>
  );
}
