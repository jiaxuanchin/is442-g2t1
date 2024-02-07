import React, { useState, useEffect } from 'react';

const ManageBooking = () => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    // Fetch the bookings from the backend API
    const fetchBookings = async () => {
      // Replace with your actual fetch call
      const response = await fetch('your-api/bookings');
      const data = await response.json();
      setBookings(data); // Assuming the API returns the bookings data in the desired format
    };

    fetchBookings();
  }, []);

  const handleCheckIn = async (bookingId) => {
    // Send the check-in status to the backend
    try {
      const response = await fetch(`your-api/bookings/check-in/${bookingId}`, {
        method: 'POST', // This might be 'PUT' or 'PATCH' depending on your API
        // Additional headers and body may be required depending on the backend implementation
      });

      if (response.ok) {
        // Update the status in the local state to reflect the check-in
        setBookings(currentBookings =>
          currentBookings.map(booking =>
            booking.id === bookingId ? { ...booking, checkedIn: true } : booking
          )
        );
      } else {
        // Handle errors, such as displaying a message to the user
        console.error('Failed to check in');
      }
    } catch (error) {
      // Handle network errors
      console.error('Network error:', error);
    }
  };

  return (
    <div>
      <h2>Manage Bookings</h2>
      <table>
        <thead>
          <tr>
            <th>Booking ID</th>
            <th>Event Name</th>
            <th>Customer Name</th>
            <th>Status</th>
            <th>Check-In</th>
          </tr>
        </thead>
        <tbody>
          {bookings.map(booking => (
            <tr key={booking.id}>
              <td>{booking.id}</td>
              <td>{booking.eventName}</td>
              <td>{booking.customerName}</td>
              <td>{booking.checkedIn ? 'Checked In' : 'Not Checked In'}</td>
              <td>
                {booking.checkedIn ? (
                  'Done'
                ) : (
                  <button onClick={() => handleCheckIn(booking.id)}>Check In</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ManageBooking;
