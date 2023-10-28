import React, { useEffect, useState } from 'react';
import './RenewSoftwareForm.css'; // Import the RenewSoftwareForm.css file
import { ToastContainer, toast } from 'react-toastify';

const SoftwareAnalysisForm = ({ onAnalysis }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [activeUsers, setActiveUsers] = useState('');
  const [averageTimeUsage, setAverageTimeUsage] = useState('');
  const [companyRating, setCompanyRating] = useState('');
  const [message, setMessage] = useState('');

  const [software, setSoftware] = useState([]);

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
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(analysisData),
      });
  
      if (response.ok) {
        const data = await response.json();
        toast.success('Software analysis done successfully!');
      
      } else {
        toast.error('Failed to set software analysis. Please check the software ID.');

      }
    } catch (error) {
      toast.error('An error occurred while setting software analysis.');
    }
  
    resetForm();
  };

  useEffect(() => {
    const fetchSoftwareDevices = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/allSoftware',{headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
              }});
            const data = await response.json();
            setSoftware(data);
        }
        catch (error) {
            console.error('Error:', error);
        }
    };

    fetchSoftwareDevices();
}
, []);


const softwareOptions = software.map((software) => (
  <option key={software.id} value={software.id}>
      {software.id}
  </option>
));

  

  return (
    <div className="form-container">
      <h2>Software Analysis</h2>
      <form onSubmit={handleAnalysisSubmit}>
        <label>
          Software ID:
          <select
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          >
            <option value="">Select a software</option>
            {softwareOptions}
          </select>
        </label>
        <br />
        <br />
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
