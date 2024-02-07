import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div>
      <h1>Welcome to the Ticket Booking System</h1>
      <p>Find and book tickets for your favorite events.</p>
      <Link to="/events">Browse Events</Link>
    </div>
  );
};

export default Home;
