// src/pages/CreateEvent.js
import React, { useState } from 'react';

function CreateEvent() {
  const [eventName, setEventName] = useState('');
  const [venue, setVenue] = useState('');
  const [date, setDate] = useState('');
  const [ticketPrice, setTicketPrice] = useState('');
  const [ticketsAvailable, setTicketsAvailable] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    // Implement event creation logic here
    console.log('Creating Event', { eventName, venue, date, ticketPrice, ticketsAvailable });
  };

  return (
    <div>
      <h2>Create New Event</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Event Name:</label>
          <input type="text" value={eventName} onChange={(e) => setEventName(e.target.value)} />
        </div>
        <div>
          <label>Venue:</label>
          <input type="text" value={venue} onChange={(e) => setVenue(e.target.value)} />
        </div>
        <div>
          <label>Date and Time:</label>
          <input type="datetime-local" value={date} onChange={(e) => setDate(e.target.value)} />
        </div>
        <div>
          <label>Ticket Price:</label>
          <input type="number" value={ticketPrice} onChange={(e) => setTicketPrice(e.target.value)} />
        </div>
        <div>
          <label>Number of Tickets Available:</label>
          <input type="number" value={ticketsAvailable} onChange={(e) => setTicketsAvailable(e.target.value)} />
        </div>
        <button type="submit">Create Event</button>
      </form>
    </div>
  );
}

export default CreateEvent;
