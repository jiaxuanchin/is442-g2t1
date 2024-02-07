import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const EventList = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Here you would fetch the events from the backend
    // For now, we'll use some dummy data
    setEvents([
      { id: 1, name: 'Rock Concert', date: '2024-03-01', venue: 'Stadium A' },
      { id: 2, name: 'Jazz Festival', date: '2024-04-12', venue: 'Open Air Park' },
      // ... other events
    ]);
  }, []);

  return (
    <div>
      <h2>Upcoming Events</h2>
      <ul>
        {events.map(event => (
          <li key={event.id}>
            <Link to={`/events/${event.id}`}>{event.name}</Link> - {event.date} at {event.venue}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EventList;
