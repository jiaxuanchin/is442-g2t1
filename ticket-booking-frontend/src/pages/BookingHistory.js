import React, { useState, useEffect } from 'react';

const BookingHistory = () => {
  const [bookingHistory, setBookingHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Replace with the API call to fetch the user's booking history
    const fetchBookingHistory = async () => {
      try {
        const response = await fetch('your-api/user/bookings');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setBookingHistory(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBookingHistory();
  }, []);

  if (loading) return <div>Loading your booking history...</div>;
  if (error) return <div>Error fetching booking history: {error}</div>;

  return (
    <div>
      <h2>Your Booking History</h2>
      <table>
        <thead>
          <tr>
            <th>Event</th>
            <th>Date</th>
            <th>Venue</th>
            <th>Number of Tickets</th>
            <th>Total Price</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {bookingHistory.length === 0 ? (
            <tr>
              <td colSpan="6">No bookings found.</td>
            </tr>
          ) : (
            bookingHistory.map((booking, index) => (
              <tr key={index}>
                <td>{booking.eventName}</td>
                <td>{booking.date}</td>
                <td>{booking.venue}</td>
                <td>{booking.numberOfTickets}</td>
                <td>${booking.totalPrice.toFixed(2)}</td>
                <td>{booking.status}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default BookingHistory;
