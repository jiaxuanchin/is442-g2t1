import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header'; // Navigation bar
import Home from './pages/Home'; // Homepage
import EventList from './pages/EventList'; // List of events
import EventDetails from './pages/EventDetails'; // Details for a specific event
import BookingHistory from './pages/BookingHistory'; // User's booking history
import Login from './pages/Login'; // Login page
import Register from './pages/Register'; // Registration page
import ManageBooking from './pages/ManageBooking'; // For ticketing officers to manage bookings
import SalesStatistics from './pages/SalesStatistics'; // For event managers to view sales statistics
import './App.css'; // Optional if you have global styles

function App() {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/events" element={<EventList />} />
          <Route path="/events/:id" element={<EventDetails />} />
          <Route path="/booking-history" element={<BookingHistory />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/manage-booking" element={<ManageBooking />} />
          <Route path="/sales-statistics" element={<SalesStatistics />} />
          {/* You can add more routes here as needed */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
