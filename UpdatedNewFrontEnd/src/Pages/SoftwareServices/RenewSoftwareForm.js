import React, { useEffect, useState } from 'react';
import { TextField, Button } from '@mui/material';
import './RenewSoftwareForm.css';
import { toast } from 'react-toastify';


const RenewSoftwareForm = ({ onRenew }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [licenseKey, setLicenseKey] = useState('');
  const [message, setMessage] = useState('');

  const [software, setSoftware] = useState([]);

  const resetForm = () => {
    setSoftwareId('');
    setPurchaseDate('');
    setExpiryDate('');
    setLicenseKey('');
    setMessage('');
  };

  const handleRenewSubmit = async (e) => {
    e.preventDefault();
  
    const renewalData = {
      software: {
        id: softwareId,
        purchaseDate,
        expiryDate,
        licenseKey,
      },
    };
  
    try {
      const response = await fetch('http://localhost:8080/api/renewSoftware', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(renewalData),
      });
  
      if (response.ok) {
        const data = await response.json();
        toast.success('Software renewed successfully!');
      } 
      else {
        toast.error('Failed to renew software. Please check the software ID.');
      }
    } catch (error) {
      if(error.message.includes("401")){
        toast.error('Unauthorized Access');
      }else{
        toast.error('Failed to renew software. Please check the software ID.');
      }
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
      <h2>Renew Software</h2>
      <form onSubmit={handleRenewSubmit}>
        <label>
          Software ID:
          <select
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          >
            <option value="">Select a software</option>
            {softwareOptions}
          </select>
          <br />
          <br />
        </label>
        <label>
          Purchase Date:
          <input
            type="date"
            className="custom-textfield" 
            value={purchaseDate}
            onChange={(e) => setPurchaseDate(e.target.value)}
          />
        </label>
        <label>
          Expiry Date:
          <input
            type="date"
            className="custom-textfield" 
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
          />
        </label>
        <label>
          License Key:
          <input
            type="text"
            className="custom-textfield" 
            value={licenseKey}
            onChange={(e) => setLicenseKey(e.target.value)}
          />
        </label>
        <button type="submit" className="custom-button">Renew Software</button> {/* Apply the CSS class */}
      </form>
      <div className="message">
        {message}
      </div>
    </div>
  );
};

export default RenewSoftwareForm;
