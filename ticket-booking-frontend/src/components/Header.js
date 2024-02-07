import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/events">Events</Link></li>
          <li><Link to="/booking-history">Booking History</Link></li>
          <li><Link to="/login">Login</Link></li>
          <li><Link to="/register">Register</Link></li>
          {/* Assuming Manage Booking and Sales Statistics are for authenticated users/admins */}
          <li><Link to="/manage-booking">Manage Booking</Link></li>
          <li><Link to="/sales-statistics">Sales Statistics</Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
