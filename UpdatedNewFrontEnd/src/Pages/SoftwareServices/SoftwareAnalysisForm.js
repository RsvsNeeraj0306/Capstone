import React, { useState } from 'react';

const SoftwareAnalysisForm = ({ onAnalysis }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [activeUsers, setActiveUsers] = useState('');
  const [averageTimeUsage, setAverageTimeUsage] = useState('');
  const [companyRating, setCompanyRating] = useState('');
  const [message, setMessage] = useState('');

  const resetForm = () => {
    setSoftwareId('');
    setActiveUsers('');
    setAverageTimeUsage('');
    setCompanyRating('');
    setMessage('');
  };

  const handleAnalysisSubmit = async (e) => {
    e.preventDefault();

    const analysisData = {
      software: {
        id: softwareId,
      },
      softwareAnalysis: {
        activeUsers,
        averageTimeUsage,
        companyRating,
      },
    };

    try {
      const response = await fetch('http://localhost:8080/api/analysis', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(analysisData),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(data.responseBody);
        onAnalysis(); // Notify the parent component about the successful analysis
      } else {
        setMessage('Failed to set software analysis. Please check the software ID.');

      }
    } catch (error) {
      setMessage('An error occurred while setting software analysis.');
    }
    console.log(analysisData);
    resetForm();
  };

  return (
    <div className="form-container">
      <h2>Set Software Analysis</h2>
      <form onSubmit={handleAnalysisSubmit}>
        <label>
          Software ID:
          <input
            type="text"
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          />
        </label>
        <label>
          Active Users:
          <input
            type="text"
            value={activeUsers}
            onChange={(e) => setActiveUsers(e.target.value)}
          />
        </label>
        <label>
          Average Time Usage:
          <input
            type="text"
            value={averageTimeUsage}
            onChange={(e) => setAverageTimeUsage(e.target.value)}
          />
        </label>
        <label>
          Company Rating:
          <input
            type="text"
            value={companyRating}
            onChange={(e) => setCompanyRating(e.target.value)}
          />
        </label>
        <button type="submit">Set Software Analysis</button>
      </form>
      <div className="message">{message}</div>
    </div>
  );
};

export default SoftwareAnalysisForm;
