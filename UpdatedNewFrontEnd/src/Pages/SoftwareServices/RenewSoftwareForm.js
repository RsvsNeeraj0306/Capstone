import React, { useState } from 'react';

const RenewSoftwareForm = ({ onRenew }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [licenseKey, setLicenseKey] = useState('');
  const [message, setMessage] = useState('');

  const resetForm = () => {
    setSoftwareId('');
    setPurchaseDate('');
    setExpiryDate('');
    setLicenseKey('');
    setMessage('');
  };

  const handleRenewSubmit = async (e) => {
    e.preventDefault(); // Prevent page refresh on form submission

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
        },
        body: JSON.stringify(renewalData),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(data.responseBody);
        onRenew(); // Notify the parent component about the successful renewal
      } else {
        setMessage('Failed to renew software. Please check the software ID.');
      }
    } catch (error) {
      setMessage('An error occurred while renewing the software.');
    }

    resetForm();
  };

  return (
    <div className="form-container">
      <h2>Renew Software</h2>
      <form onSubmit={handleRenewSubmit}>
        <label>
          Software ID:
          <input
            type="text"
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          />
        </label>
        <label>
          Purchase Date:
          <input
            type="date"
            value={purchaseDate}
            onChange={(e) => setPurchaseDate(e.target.value)}
          />
        </label>
        <label>
          Expiry Date:
          <input
            type="date"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
          />
        </label>
        <label>
          License Key:
          <input
            type="text"
            value={licenseKey}
            onChange={(e) => setLicenseKey(e.target.value)}
          />
        </label>
        <button type="submit">Renew Software</button>
      </form>
      <div className="message">{message}</div>
    </div>
  );
};

export default RenewSoftwareForm;
