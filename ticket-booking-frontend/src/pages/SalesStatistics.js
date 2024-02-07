import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const SalesStatistics = () => {
  const [salesData, setSalesData] = useState([]);

  useEffect(() => {
    // Fetch the sales statistics data from the API
    const fetchSalesData = async () => {
      // Replace with your actual fetch call
      const response = await fetch('your-api/sales-statistics');
      const data = await response.json();
      setSalesData(data); // Assuming the API returns the sales data in a suitable format
    };

    fetchSalesData();
  }, []);

  const chartData = {
    labels: salesData.map(item => item.eventName),
    datasets: [
      {
        label: 'Revenue',
        data: salesData.map(item => item.revenue),
        backgroundColor: 'rgba(0, 123, 255, 0.5)',
      },
      {
        label: 'Tickets Sold',
        data: salesData.map(item => item.ticketsSold),
        backgroundColor: 'rgba(75, 192, 192, 0.5)',
      },
    ],
  };

  return (
    <div>
      <h2>Sales Statistics</h2>
      <Bar data={chartData} options={{
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }} />
    </div>
  );
};

export default SalesStatistics;
