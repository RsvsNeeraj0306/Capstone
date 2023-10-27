import React, { useState } from 'react';
import './RenewSoftwareForm.css'; // Import the RenewSoftwareForm.css file

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
            className="custom-textfield" // Use the class from RenewSoftwareForm.css
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          />
        </label>
        <label>
          Active Users:
          <input
            type="text"
            className="custom-textfield" // Use the class from RenewSoftwareForm.css
            value={activeUsers}
            onChange={(e) => setActiveUsers(e.target.value)}
          />
        </label>
        <label>
          Average Time Usage:
          <input
            type="text"
            className="custom-textfield" // Use the class from RenewSoftwareForm.css
            value={averageTimeUsage}
            onChange={(e) => setAverageTimeUsage(e.target.value)}
          />
        </label>
        <label>
          Company Rating:
          <input
            type="text"
            className="custom-textfield" // Use the class from RenewSoftwareForm.css
            value={companyRating}
            onChange={(e) => setCompanyRating(e.target.value)}
          />
        </label>
        <button type="submit" className="custom-button">Set Software Analysis</button>
      </form>
      <div className="message">{message}</div>
    </div>
  );
};

export default SoftwareAnalysisForm;
