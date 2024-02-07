import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const UpdateEvent = () => {
  const { eventId } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    date: '',
    venue: '',
    ticketPrice: '',
    ticketsAvailable: '',
  });

  useEffect(() => {
    // Fetch the event details from the API
    const fetchEventDetails = async () => {
      // Replace with your actual fetch call
      const response = await fetch(`your-api/events/${eventId}`);
      const data = await response.json();
      setFormData(data); // This assumes the API returns event data in the same shape as formData
    };

    fetchEventDetails();
  }, [eventId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Send the form data to the API for updating
    try {
      const response = await fetch(`your-api/events/${eventId}`, {
        method: 'PUT', // or 'PATCH' depending on your API
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        // Handle success
        navigate(`/events/${eventId}`); // Redirect to the event details page
      } else {
        // Handle errors
        console.error('Failed to update event');
      }
    } catch (error) {
      // Handle network errors
      console.error('Network error:', error);
    }
  };

  return (
    <div>
      <h2>Update Event</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Event Name:
          <input type="text" name="name" value={formData.name} onChange={handleChange} />
        </label>
        <label>
          Date:
          <input type="date" name="date" value={formData.date} onChange={handleChange} />
        </label>
        <label>
          Venue:
          <input type="text" name="venue" value={formData.venue} onChange={handleChange} />
        </label>
        <label>
          Ticket Price:
          <input type="number" name="ticketPrice" value={formData.ticketPrice} onChange={handleChange} />
        </label>
        <label>
          Tickets Available:
          <input type="number" name="ticketsAvailable" value={formData.ticketsAvailable} onChange={handleChange} />
        </label>
        <button type="submit">Update Event</button>
      </form>
    </div>
  );
};

export default UpdateEvent;
