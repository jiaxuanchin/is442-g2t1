import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const EventDetails = () => {
  const [eventDetails, setEventDetails] = useState(null);
  const { eventId } = useParams();

  useEffect(() => {
    // Here you would fetch the event details from the backend using the eventId
    // For now, we'll use some dummy data
    setEventDetails({
      id: eventId,
      name: 'Rock Concert',
      date: '2024-03-01',
      venue: 'Stadium A',
      description: 'The biggest rock concert of the year.',
      availableTickets: 250,
    });
  }, [eventId]);

  return (
    <div>
      {eventDetails ? (
        <>
          <h2>{eventDetails.name}</h2>
          <p>Date: {eventDetails.date}</p>
          <p>Venue: {eventDetails.venue}</p>
          <p>Description: {eventDetails.description}</p>
          <p>Tickets Available: {eventDetails.availableTickets}</p>
          {/* Add a button or link to proceed to booking */}
        </>
      ) : (
        <p>Loading event details...</p>
      )}
    </div>
  );
};

export default EventDetails;
